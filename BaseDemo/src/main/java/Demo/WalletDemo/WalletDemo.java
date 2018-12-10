package Demo.WalletDemo;

import Demo.BaseDemo.BaseFunctionDemo;
import org.chain3j.crypto.CipherException;
import org.chain3j.crypto.Credentials;
import org.chain3j.crypto.WalletUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.InvalidAlgorithmParameterException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;

public class WalletDemo {
    private static final Logger log = LoggerFactory.getLogger(BaseFunctionDemo.class);
    private static String path="C:/Users/david/AppData/Roaming/MoacNode/devnet/keystore/";
    private static String filekey="test123";

    public static void main(String[] args){
        new WalletDemo().run();

    }

    public void run(){
        String walletID = null;
        try{
           walletID = creatAccount();
        }catch (Exception e){

        }
    }

    //create a wallet file
    private String creatAccount() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidAlgorithmParameterException, IOException, CipherException {
        String walletFileName="";
        String walletFilePath=path;
        String password=filekey;

        walletFileName = WalletUtils.generateNewWalletFile(password, new File(walletFilePath), false);
        System.out.println("The wallet is "+walletFileName);
        return  walletFileName;
    }

    //load the wallet file
    private void loadWallet(String walletID) throws IOException, CipherException {
        Credentials credentials = WalletUtils.loadCredentials(filekey,path);
        String address = credentials.getAddress();
        BigInteger publicKey = credentials.getEcKeyPair().getPublicKey();
        BigInteger privateKey = credentials.getEcKeyPair().getPrivateKey();

        System.out.println("public key=" + publicKey);
        System.out.println("private key=" + privateKey);
    }



}
