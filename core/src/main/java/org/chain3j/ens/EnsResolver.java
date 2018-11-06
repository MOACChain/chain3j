package org.chain3j.ens;

import org.chain3j.crypto.WalletUtils;
import org.chain3j.ens.contracts.generated.ENS;
import org.chain3j.ens.contracts.generated.PublicResolver;
import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.core.DefaultBlockParameterName;
import org.chain3j.protocol.core.methods.response.McBlock;
import org.chain3j.protocol.core.methods.response.McSyncing;
import org.chain3j.protocol.core.methods.response.NetVersion;
import org.chain3j.tx.ClientTransactionManager;
import org.chain3j.tx.ManagedTransaction;
import org.chain3j.tx.TransactionManager;
import org.chain3j.tx.gas.DefaultGasProvider;
import org.chain3j.utils.Numeric;

/**
 * Resolution logic for contract addresses.
 */
public class EnsResolver {

    static final long DEFAULT_SYNC_THRESHOLD = 1000 * 60 * 3;
    static final String REVERSE_NAME_SUFFIX = ".addr.reverse";

    private final Chain3j chain3j;
    private final TransactionManager transactionManager;
    private long syncThreshold;  // non-final in case this value needs to be tweaked

    public EnsResolver(Chain3j chain3j, long syncThreshold) {
        this.chain3j = chain3j;
        transactionManager = new ClientTransactionManager(chain3j, null);  // don't use empty string
        this.syncThreshold = syncThreshold;
    }

    public EnsResolver(Chain3j chain3j) {
        this(chain3j, DEFAULT_SYNC_THRESHOLD);
    }

    public void setSyncThreshold(long syncThreshold) {
        this.syncThreshold = syncThreshold;
    }

    public long getSyncThreshold() {
        return syncThreshold;
    }

    /**
     * Provides an access to a valid public resolver in order to access other API methods.
     * @param ensName our user input ENS name
     * @return PublicResolver
     */
    public PublicResolver obtainPublicResolver(String ensName) {
        if (isValidEnsName(ensName)) {
            try {
                if (!isSynced()) {
                    throw new EnsResolutionException("Node is not currently synced");
                } else {
                    return lookupResolver(ensName);
                }
            } catch (Exception e) {
                throw new EnsResolutionException("Unable to determine sync status of node", e);
            }

        } else {
            throw new EnsResolutionException("EnsName is invalid: " + ensName);
        }
    }

    public String resolve(String contractId) {
        if (isValidEnsName(contractId)) {
            PublicResolver resolver = obtainPublicResolver(contractId);

            byte[] nameHash = NameHash.nameHashAsBytes(contractId);
            String contractAddress = null;
            try {
                contractAddress = resolver.addr(nameHash).send();
            } catch (Exception e) {
                throw new RuntimeException("Unable to execute Moac request", e);
            }

            if (!WalletUtils.isValidAddress(contractAddress)) {
                throw new RuntimeException("Unable to resolve address for name: " + contractId);
            } else {
                return contractAddress;
            }
        } else {
            return contractId;
        }
    }

    /**
     * Reverse name resolution as documented in the
     * <a href="https://docs.ens.domains/en/latest/userguide.html#reverse-name-resolution">specification</a>.
     * @param address an ethereum address, example: "0x314159265dd8dbb310642f98f50c066173c1259b"
     * @return a EnsName registered for provided address
     */
    public String reverseResolve(String address) {
        if (WalletUtils.isValidAddress(address)) {
            String reverseName = Numeric.cleanHexPrefix(address) + REVERSE_NAME_SUFFIX;
            PublicResolver resolver = obtainPublicResolver(reverseName);

            byte[] nameHash = NameHash.nameHashAsBytes(reverseName);
            String name = null;
            try {
                name = resolver.name(nameHash).send();
            } catch (Exception e) {
                throw new RuntimeException("Unable to execute Moac request", e);
            }

            if (!isValidEnsName(name)) {
                throw new RuntimeException("Unable to resolve name for address: " + address);
            } else {
                return name;
            }
        } else {
            throw new EnsResolutionException("Address is invalid: " + address);
        }
    }

    PublicResolver lookupResolver(String ensName) throws Exception {
        NetVersion netVersion = chain3j.netVersion().send();
        String registryContract = Contracts.resolveRegistryContract(netVersion.getNetVersion());

        ENS ensRegistry = ENS.load(
                registryContract, chain3j, transactionManager,
                DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT);

        byte[] nameHash = NameHash.nameHashAsBytes(ensName);

        String resolverAddress = ensRegistry.resolver(nameHash).send();
        PublicResolver resolver = PublicResolver.load(
                resolverAddress, chain3j, transactionManager,
                DefaultGasProvider.GAS_PRICE, DefaultGasProvider.GAS_LIMIT);

        return resolver;
    }

    boolean isSynced() throws Exception {
        McSyncing ethSyncing = chain3j.mcSyncing().send();
        if (ethSyncing.isSyncing()) {
            return false;
        } else {
            McBlock ethBlock =
                    chain3j.mcGetBlockByNumber(DefaultBlockParameterName.LATEST, false).send();
            long timestamp = ethBlock.getBlock().getTimestamp().longValueExact() * 1000;

            return System.currentTimeMillis() - syncThreshold < timestamp;
        }
    }

    public static boolean isValidEnsName(String input) {
        return input != null  // will be set to null on new Contract creation
                && (input.contains(".") || !WalletUtils.isValidAddress(input));
    }
}
