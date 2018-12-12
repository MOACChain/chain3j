package Demo.ContractDemo.GreetContract;

import org.chain3j.abi.FunctionEncoder;
import org.chain3j.abi.TypeReference;
import org.chain3j.abi.datatypes.Function;
import org.chain3j.abi.datatypes.Type;
import org.chain3j.abi.datatypes.Utf8String;
import org.chain3j.crypto.Credentials;
import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.core.RemoteCall;
import org.chain3j.protocol.core.methods.response.*;
import org.chain3j.tx.Contract;
import org.chain3j.tx.exceptions.ContractCallException;
import org.chain3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;

public class GreeterContract extends Contract {
    public GreeterContract(
            String  contractBinary,
            String contractAddress, Chain3j chain3j, Credentials credentials,
            ContractGasProvider provider
    ){
        super(contractBinary,contractAddress,chain3j, credentials,provider);
    }



    public void init(){
        //
    }

    public static  GreeterContract deployMe(
            Chain3j chain3j, Credentials credentials,
            ContractGasProvider provider, String contractBinary, Utf8String _greeting,
            BigInteger initialValue
    ){
        String encodedConstrutor=
                FunctionEncoder.encodeConstructor(
                        Arrays.<Type>asList(_greeting)
                );
        try{
            return deploy(GreeterContract.class,chain3j,credentials,provider,
                    contractBinary,encodedConstrutor,initialValue); //.get();
            //return greeter;
        }catch(ContractCallException e1){
            return null;
        }
        catch(Exception e){
            System.out.println("deploy exception:"+e);
            return null;
        }
    }

        public RemoteCall<Utf8String> greet(){
        Function function = new Function("greet",
                Arrays.<Type>asList(), Arrays.<TypeReference<?>>asList(
                new TypeReference<Utf8String>() {
                }));
        try
        {
            return executeRemoteCallSingleValueReturn(function);
        }catch (Exception e) {
            System.out.println("basedemo greet exception:" + e);
            return null;
        }
    }

    //sign
    public RemoteCall<TransactionReceipt> newGreeting(Utf8String _greeting){
        Function function = new Function( "newGreeting",
                Arrays.<Type>asList(_greeting),
                Collections.<TypeReference<?>>emptyList()
                );
        return executeRemoteCallTransaction(function);
    }

    //without sign

}
