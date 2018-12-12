package Demo.BaseDemo;

import org.chain3j.crypto.WalletUtils;
import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.admin.Admin;
import org.chain3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.chain3j.protocol.admin.methods.response.PersonalListAccounts;
import org.chain3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.chain3j.protocol.core.DefaultBlockParameter;
import org.chain3j.protocol.core.methods.response.Chain3ClientVersion;
import org.chain3j.protocol.core.methods.response.McBlock;
import org.chain3j.protocol.core.methods.response.McGetBalance;
import org.chain3j.protocol.http.HttpService;
import org.chain3j.utils.Convert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.List;

public class BaseFunctionDemo {
    private static final Logger log = LoggerFactory.getLogger(BaseFunctionDemo.class);

    public static void main(String[] args){
        new BaseFunctionDemo().run();

    }

    public void run(){
        //Check the Client Version
        Chain3j chain3j = Chain3j.build(new HttpService("http://127.0.0.1:8545")); // using loaclhost for development
        Admin admin = Admin.build(new HttpService("http://127.0.0.1:8545")); //
        try{
            chechClientVersion(chain3j);
        }catch (IOException e){
            log.info("IOException",e.toString());
        }

        //Get account list
//        try{
//            getAccountList(admin);
//        }catch (IOException e){
//
//        }

        //Create an account
        try{
            createAccount(admin);
        }catch (IOException e){

        }

        //Get balance of
        try {
            getBlanceOf(chain3j);
        }catch (IOException e){

        }

        //Unlock an account
        try {
            unlockAccount(admin);
        }catch (IOException e){

        }


//        try{
//            getBlanceByHash(chain3j);
//        }catch (Exception e){
//
//        }

    }

//    private void getBlanceByHash(Chain3j chain3j)throws IOException{
//        String hashNum = "5d3baec2ebc268c7b6dab07039d04278ffac5697988a8f7999211c293494e015";
//        McBlock mcBlock = chain3j.mcGetBlockByHash(hashNum,true).send();
//        if (mcBlock == null){
//            System.out.println("the mcBlock is null");
//        }else{
//            System.out.println("the block is "+mcBlock);
//        }
//
//        McBlock.Block block = mcBlock.getBlock();
//
//        //BigInteger timestamp =mcBlock.getBlock().getTimestamp();
//        System.out.println("Time is "+block);
//    }

    private void unlockAccount(Admin admin) throws  IOException{
        String address =""; //Your wallet address
        String password="";
        BigInteger unlockDuration = BigInteger.valueOf(60L);
        PersonalUnlockAccount personalUnlockAccount = admin.personalUnlockAccount(address,password,unlockDuration).send();
        Boolean isUnclock = personalUnlockAccount.accountUnlocked();
        //if()
        System.out.println("The account "+address+"is unclocked now");
    }

    private void getBlanceOf(Chain3j chain3j) throws IOException{
        String adress = ""; //
        McGetBalance balacne = chain3j.mcGetBalance(adress, DefaultBlockParameter.valueOf("latest")).send();
        String balanceVule = Convert.fromSha(balacne.getBalance().toString(),Convert.Unit.MC).toPlainString().concat("mc");
        System.out.println("balanceVule: "+balanceVule);
    }

    private void chechClientVersion(Chain3j chain3j) throws IOException{
        Chain3ClientVersion chain3ClientVersion = chain3j.chain3ClientVersion().send();
        //log.info("Client Version",chain3ClientVersion.getChain3ClientVersion());
        System.out.println("Client Version: " + chain3ClientVersion.getChain3ClientVersion());

    }

    private void getAccountList(Admin admin) throws IOException{
        PersonalListAccounts accountsList = admin.personalListAccounts().send();

        List<String> addressList = accountsList.getAccountIds();
        System.out.println("account size: "+addressList.size());
        for (String address : addressList){
            System.out.println(address);
        }
    }

    private void createAccount(Admin admin) throws IOException{
//        String password = null;
//        String checkkey = null;
//        System.out.print("Plz enter you password");

        String password = "";
        NewAccountIdentifier newAccount = admin.personalNewAccount(password).send();
        String newAccoundtAdress = newAccount.getAccountId();
        System.out.println("New Account adress is:"+newAccoundtAdress);

        //NewAccountIdentifier newAccount1 = WalletUtils.generateWalletFile(password,new File("file adrees"),false);

    }

}
