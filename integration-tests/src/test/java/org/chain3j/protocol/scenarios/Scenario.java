package org.chain3j.protocol.scenarios;

import java.io.IOException;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Optional;

import org.junit.Before;

import org.chain3j.abi.TypeReference;
import org.chain3j.abi.datatypes.Function;
import org.chain3j.abi.datatypes.Uint;
import org.chain3j.crypto.Credentials;
import org.chain3j.protocol.admin.Admin;
import org.chain3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.chain3j.protocol.core.DefaultBlockParameterName;
import org.chain3j.protocol.core.methods.response.McGetTransactionCount;
import org.chain3j.protocol.core.methods.response.McGetTransactionReceipt;
import org.chain3j.protocol.core.methods.response.TransactionReceipt;
import org.chain3j.protocol.http.HttpService;
import org.chain3j.tx.gas.ContractGasProvider;
import org.chain3j.tx.gas.StaticGasProvider;

import static junit.framework.TestCase.fail;

/**
 * Common methods & settings used accross scenarios.
 */
public class Scenario {

    static final BigInteger GAS_PRICE = BigInteger.valueOf(22_000_000_000L);
    static final BigInteger GAS_LIMIT = BigInteger.valueOf(4_300_000);
    static final StaticGasProvider STATIC_GAS_PROVIDER =
            new StaticGasProvider(GAS_PRICE, GAS_LIMIT);

    // testnet
    private static final String WALLET_PASSWORD = "test";

    /*
    If you want to use regular Moac wallet addresses, provide a WALLET address variable
    "0x..." // 20 bytes (40 hex characters) & replace instances of ALICE.getAddress() with this
    WALLET address variable you've defined.
    */
    static final Credentials ALICE = Credentials.create(
            "0x7312F4B8A4457a36827f185325Fd6B66a3f8BB8B",  // 32 byte hex value
            "0xc75a5f85ef779dcf95c651612efb3c3b9a6dfafb1bb5375905454d9fc8be8a6b"  // 64 byte hex value
    );

    static final Credentials BOB = Credentials.create(
            "",  // 32 byte hex value
            "0xB017F0530A78ACB73BC10A90720AA77F4CBEE7889CBAD5059B3BCF256A310635"  // 64 byte hex value
    );

    private static final BigInteger ACCOUNT_UNLOCK_DURATION = BigInteger.valueOf(30);

    private static final int SLEEP_DURATION = 15000;
    private static final int ATTEMPTS = 40;

    Admin chain3j;

    public Scenario() { }

    @Before
    public void setUp() {
        this.chain3j = Admin.build(new HttpService());
    }

    boolean unlockAccount() throws Exception {
        PersonalUnlockAccount personalUnlockAccount =
                chain3j.personalUnlockAccount(
                        ALICE.getAddress(), WALLET_PASSWORD, ACCOUNT_UNLOCK_DURATION)
                        .sendAsync().get();
        return personalUnlockAccount.accountUnlocked();
    }

    TransactionReceipt waitForTransactionReceipt(
            String transactionHash) throws Exception {

        Optional<TransactionReceipt> transactionReceiptOptional =
                getTransactionReceipt(transactionHash, SLEEP_DURATION, ATTEMPTS);

        if (!transactionReceiptOptional.isPresent()) {
            fail("WalletDemo receipt not generated after " + ATTEMPTS + " attempts");
        }

        return transactionReceiptOptional.get();
    }

    private Optional<TransactionReceipt> getTransactionReceipt(
            String transactionHash, int sleepDuration, int attempts) throws Exception {

        Optional<TransactionReceipt> receiptOptional =
                sendTransactionReceiptRequest(transactionHash);
        for (int i = 0; i < attempts; i++) {
            if (!receiptOptional.isPresent()) {
                Thread.sleep(sleepDuration);
                receiptOptional = sendTransactionReceiptRequest(transactionHash);
            } else {
                break;
            }
        }

        return receiptOptional;
    }

    private Optional<TransactionReceipt> sendTransactionReceiptRequest(
            String transactionHash) throws Exception {
        McGetTransactionReceipt transactionReceipt =
                chain3j.mcGetTransactionReceipt(transactionHash).sendAsync().get();

        return transactionReceipt.getTransactionReceipt();
    }

    BigInteger getNonce(String address) throws Exception {
        McGetTransactionCount mcGetTransactionCount = chain3j.mcGetTransactionCount(
                address, DefaultBlockParameterName.LATEST).sendAsync().get();

        return mcGetTransactionCount.getTransactionCount();
    }

    Function createFibonacciFunction() {
        return new Function(
                "fibonacciNotify",
                Collections.singletonList(new Uint(BigInteger.valueOf(7))),
                Collections.singletonList(new TypeReference<Uint>() {}));
    }

    static String load(String filePath) throws URISyntaxException, IOException {
        URL url = Scenario.class.getClass().getResource(filePath);
        byte[] bytes = Files.readAllBytes(Paths.get(url.toURI()));
        return new String(bytes);
    }

    static String getFibonacciSolidityBinary() throws Exception {
        return load("/solidity/fibonacci/build/Fibonacci.bin");
    }
}
