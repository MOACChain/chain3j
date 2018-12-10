package Demo.ContractDemo;

import org.chain3j.abi.datatypes.generated.Uint256;
import org.chain3j.crypto.Credentials;
import org.chain3j.crypto.WalletUtils;
import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.admin.Admin;
import org.chain3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.chain3j.protocol.core.RemoteCall;
import org.chain3j.protocol.core.methods.response.TransactionReceipt;
import org.chain3j.protocol.http.HttpService;
import org.chain3j.tx.RawTransactionManager;

import java.math.BigInteger;
import java.util.concurrent.ExecutionException;

public class SimpleDemo {
    public static BigInteger gasPrice = new BigInteger("10");
    public static BigInteger gasLimited = new BigInteger("1000000");
    protected SimpleStorage simpleStorage;

    public static void main( String[] args ) throws Exception{
        new SimpleDemo().run();
    }

    private void run() throws Exception{
        Chain3j chain3j = Chain3j.build(new HttpService("http://127.0.0.1:8545"));
        Admin admin = Admin.build(new HttpService("http://127.0.0.1:8545"));

        Credentials credentials = LoadCredentialsFromKeystoreFile("test123");
        System.out.println("address: " + credentials.getAddress());

        String address="0x6059921Ae5494b7d1058DBDDfCe19bfbd2594375";
        System.out.println("contract address: "+address);
        simpleStorage = SimpleStorage.load(address.toString(), chain3j, new RawTransactionManager(chain3j, credentials, 100, 100), gasPrice, gasLimited);
        System.out.println("test");
        TransactionReceipt receipt = null;

        try {
            receipt = simpleStorage.set(new Uint256(new BigInteger("1000"))).get();
        } catch (InterruptedException | ExecutionException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public Credentials LoadCredentialsFromKeystoreFile(String password) throws Exception {
        return WalletUtils.loadCredentials(
                password, "E:/work/MOAC/Moac core/win/vnode/dev/keystore" +
                        "/UTC--2018-12-01T03-07-20.204507100Z--1bc165d9015229c99b9f984a9104b57da5bf39b0");

    }


}
