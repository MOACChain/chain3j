package Demo.ContractDemo;

import Demo.BaseDemo.BaseDemoApplication;
import org.chain3j.crypto.Credentials;
import org.chain3j.crypto.WalletUtils;
import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.admin.Admin;
import org.chain3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.chain3j.protocol.core.RemoteCall;
import org.chain3j.protocol.http.HttpService;
import org.chain3j.tx.Contract;
import org.chain3j.tx.gas.DefaultGasProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigInteger;

import static org.chain3j.tx.Contract.GAS_LIMIT;
import static org.chain3j.tx.ManagedTransaction.GAS_PRICE;

public class BaseContractDemo{
    public static void main( String[] args ) throws Exception{
        new BaseContractDemo().run();
    }

    private void run() throws Exception{
        Chain3j chain3j = Chain3j.build(new HttpService("http://127.0.0.1:8545"));
        Admin admin = Admin.build(new HttpService("http://127.0.0.1:8545"));

        try{
            String account=chain3j.mcAccounts().send().getAccounts().get(0);
            //Credentials credentials = Credentials.create(account);
            Credentials credentials = LoadCredentialsFromKeystoreFile("test123");
            String src = credentials.getAddress();
            BigInteger unlockDuration = BigInteger.valueOf(60L);
            PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(account,"test123",unlockDuration).send();
            System.out.println("Load address: " + src);
            String address="0xCb373bc1138a528898c69b9d64B0ce7e95744464";
            System.out.println("test");
            MyContract myContract = MyContract.load(address, chain3j,
                    credentials, BigInteger.valueOf(350000L),BigInteger.valueOf(350000L));
            System.out.println("test2");
            RemoteCall call= myContract.sayHello();
            System.out.println("test3");
            System.out.println("b "+myContract.getContractBinary());
            System.out.println("返回结果:"+call.send());
        }catch (Exception e){
            e.printStackTrace();
            //e.printStackTrace();
        }
    }


    public Credentials LoadCredentialsFromKeystoreFile(String password) throws Exception {
        return WalletUtils.loadCredentials(
                password, "E:/work/MOAC/Moac core/win/vnode/dev/keystore" +
                        "/UTC--2018-12-01T03-07-20.204507100Z--1bc165d9015229c99b9f984a9104b57da5bf39b0");

    }
}
