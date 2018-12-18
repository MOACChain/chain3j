package Demo.ContractDemo.GreetContract;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okio.Buffer;
import org.chain3j.abi.FunctionEncoder;
import org.chain3j.abi.TypeReference;
import org.chain3j.protocol.admin.Admin;
import org.chain3j.protocol.core.methods.response.TransactionReceipt;
import org.chain3j.tx.exceptions.ContractCallException;
import org.chain3j.tx.gas.DefaultGasProvider;
import org.chain3j.tx.gas.StaticGasProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Arrays;
import java.util.concurrent.Future;


import org.chain3j.crypto.Credentials;
import org.chain3j.crypto.RawTransaction;
import org.chain3j.crypto.TransactionEncoder;
import org.chain3j.crypto.WalletUtils;
import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.core.DefaultBlockParameter;
import org.chain3j.protocol.core.methods.response.McGetBalance;
import org.chain3j.protocol.core.methods.response.McGetTransactionCount;
import org.chain3j.protocol.http.HttpService;
import org.chain3j.utils.Numeric;
import org.chain3j.tx.gas.DefaultGasProvider;
import org.chain3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import org.chain3j.tx.Contract;
import org.chain3j.abi.datatypes.Function;
import org.chain3j.protocol.core.RemoteCall;

import org.chain3j.abi.datatypes.Type;
import org.chain3j.abi.datatypes.Utf8String;

public class MyContract {
    private static final Logger log = LoggerFactory.getLogger(MyContract.class);

    public static void main(String[] args) throws Exception {

        new MyContract().run();
    }

    private void run() throws Exception {


        Chain3j chain3j = Chain3j.build(new HttpService(
                "http://127.0.0.1:8545"));  // Use local MOAC server;
//        Admin chain3j = Admin.build(new HttpService("http://127.0.0.1:8545"));

        Credentials credentials = LoadCredentialsFromKeystoreFile("test123");   //test123");

        BigInteger gasLimit = BigInteger.valueOf(80_000);
        String contractBinary = "123";
        String contractAddress =
                "0x316C14F71334fB3D873fF0F797825C65277B64D5";
                //"0x316C14F71334fB3D873fF0F797825C65277B64D5"; //"greet" smart cotract on private chain

        StaticGasProvider gasProvider = new StaticGasProvider(BigInteger.valueOf(80_000_000),gasLimit);
        GreeterContract greeterContract = new GreeterContract(contractBinary,contractAddress,chain3j,credentials,gasProvider);


        if( greeterContract==null ){//isValid())){
            System.out.println(("greeter is null"));
        }

        greeterContract.newGreeting(new Utf8String("hi,testing 2019")).send();

        try{
            System.out.println("test123321 "+greeterContract.greet().send());
        }catch (Exception e){
            e.printStackTrace();
        }

    }


    //private chain
//    public Credentials LoadCredentialsFromKeystoreFile(String password) throws Exception {
//        return WalletUtils.loadCredentials(
//                //password, "C:/Users/david/AppData/Roaming/MoacNode/devnet/keystore"
//                //       + "/UTC--2018-11-14T16-59-28."
//                //       + "398731300Z--533ef68e791d49154d0979c8851fde5455c345cf");
//                password, "E:/work/MOAC/Moac core/win/vnode/dev/keystore"
//                        +"/UTC--2018-12-01T03-07-20.204507100Z--1bc165d9015229c99b9f984a9104b57da5bf39b0");
//
//    }

    //testnet chain
    public Credentials LoadCredentialsFromKeystoreFile(String password) throws Exception {
        return WalletUtils.loadCredentials(
                password, "E:\\work\\MOAC\\Moaccore\\win\\vnode\\dev\\keystore"
                        +"/UTC--2018-12-01T03-07-20.204507100Z--1bc165d9015229c99b9f984a9104b57da5bf39b0");

    }

    // Send value
    private RawTransaction createTX(BigInteger nonce, String des, BigInteger sendValue) {
        // nonce, gasPrice, gasLimit, des, amount to send in Sha
        return RawTransaction.createMcTransaction(
                nonce,
                BigInteger.valueOf(20000000000L),
                BigInteger.valueOf(21000), des,
                sendValue);
    }
}
