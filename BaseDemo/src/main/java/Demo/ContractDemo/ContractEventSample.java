package Demo.ContractDemo;


import org.chain3j.crypto.Credentials;
import org.chain3j.crypto.WalletUtils;
import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.core.RemoteCall;
import org.chain3j.protocol.core.methods.response.TransactionReceipt;
import org.chain3j.protocol.http.HttpService;
import org.chain3j.utils.Convert;

import java.math.BigInteger;


public class ContractEventSample {
    private static String contractAddress = "0x001e1AA3772111f82c0612401B0152CD73619697";
//    private static Chain3j chain3j;

    public static void main(String[] args) throws Exception{
        new ContractEventSample().deploy();
        new ContractEventSample().use();
    }

    private void deploy() throws Exception{
        Chain3j chain3j = Chain3j.build(new HttpService("http://127.0.0.1"));
        Credentials credentials = LoadCredentialsFromKeystoreFile("test123");
        RemoteCall<TokenERC20> deploy = TokenERC20.deploy(chain3j, credentials,
                Convert.toSha("10", Convert.Unit.GSHA).toBigInteger(),
                BigInteger.valueOf(3000000),
                BigInteger.valueOf(5201314),
                "my token", "mt");
        try {
            TokenERC20 tokenERC20 = deploy.send();
            tokenERC20.isValid();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void use() throws Exception{
        Chain3j chain3j = Chain3j.build(new HttpService("http://127.0.0.1:8545"));
        String contractAddress = "0x49CD8C70Ad800B73EF5C4C70ee16B182738E7587";
        Credentials credentials = LoadCredentialsFromKeystoreFile("test123");;
        TokenERC20 contract = TokenERC20.load(contractAddress,chain3j,credentials,
                Convert.toSha("10", Convert.Unit.GSHA).toBigInteger(),
                BigInteger.valueOf(100000));
        String myAddress = "0x8FC6e625B5491beaeA8a81E5799a5C975A4381De";
        String toAddress = "0x49CD8C70Ad800B73EF5C4C70ee16B182738E7587";
        BigInteger amount = BigInteger.ONE;
        try {
            System.out.println("test3");
            BigInteger balance = contract.balanceOf(myAddress).send();
//            System.out.println("test2");
//            TransactionReceipt receipt = contract.transfer(toAddress, amount).send();
            //etc..
            System.out.println("test");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Credentials LoadCredentialsFromKeystoreFile(String password) throws Exception {
        return WalletUtils.loadCredentials(
                password, "E:/work/MOAC/Moac core/win/vnode/test/keystore"
                        + "/UTC--2018-11-28T02-25-58."
                        + "433499700Z--8fc6e625b5491beaea8a81e5799a5c975a4381de");

    }
}