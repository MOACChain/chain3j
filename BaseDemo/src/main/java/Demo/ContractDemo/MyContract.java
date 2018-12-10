package Demo.ContractDemo;

import jnr.ffi.Struct;
import org.chain3j.abi.TypeReference;
import org.chain3j.abi.datatypes.Function;
import org.chain3j.abi.datatypes.Int;
import org.chain3j.abi.datatypes.Type;
import org.chain3j.abi.datatypes.Utf8String;
import org.chain3j.abi.datatypes.generated.Int256;
import org.chain3j.abi.datatypes.generated.Uint8;
import org.chain3j.crypto.Credentials;
import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.core.RemoteCall;
import org.chain3j.tx.Contract;
import org.chain3j.tx.TransactionManager;

import java.math.BigInteger;
import java.util.Arrays;

public final class MyContract extends Contract {
    //private static final String BINARY = "[{\"constant\":false,\"inputs\":[],\"name\":\"sayHello\",\"outputs\":[{\"name\":\"\",\"type\":\"int256\"}],\"payable\":false,\"stateMutability\":\"nonpayable\",\"type\":\"function\"}]";
    private static final String BINARY = "608060405260043610603e5763ffffffff7c0100000000000000000000000000000000000000000000000000000000600035041663f8a8fd6d81146043575b600080fd5b348015604e57600080fd5b5060556057565b005b5600a165627a7a72305820098eaf9ed99c04ec1e77485b94dc3ea12afe137623d0f00b7ebfee167701b85d0029";

    public RemoteCall sayHello(){
        Function function = new Function("testing",
                Arrays.<Type>asList(),
                Arrays.asList());
        return executeRemoteCallSingleValueReturn(function);
    }

//    protected MyContract(String contractAddress, Chain3j chain3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
//        super(BINARY, contractAddress, chain3j, credentials, gasPrice, gasLimit);
//    }

    protected MyContract(String contractAddress, Chain3j chain3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, chain3j, credentials, gasPrice, gasLimit);
    }

//    protected MyContract(String contractAddress, Chain3j chain3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
//        super(BINARY, contractAddress, chain3j, transactionManager, gasPrice, gasLimit);
//    }

    protected MyContract(String contractAddress, Chain3j chain3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(BINARY, contractAddress, chain3j, transactionManager, gasPrice, gasLimit);
    }

    public static MyContract load(String contractAddress, Chain3j chain3j,
                                  Credentials credentials, BigInteger gasPrice,
                                  BigInteger gasLimit) {
        return new MyContract(contractAddress, chain3j, credentials, gasPrice, gasLimit);
    }

//    public static MyContract load(String contractAddress, Chain3j chain3j,
//                                  Credentials credentials, BigInteger gasPrice,
//                                  BigInteger gasLimit) {
//        return new MyContract(BINARY, contractAddress, chain3j, credentials, gasPrice, gasLimit);
//    }

    public static MyContract load(String contractAddress, Chain3j chain3j,
                                  TransactionManager transactionManager,
                                  BigInteger gasPrice, BigInteger gasLimit) {
        return new MyContract(contractAddress, chain3j, transactionManager, gasPrice, gasLimit);
    }

//    public static MyContract load(String contractAddress, Chain3j chain3j,
//                                  TransactionManager transactionManager,
//                                  BigInteger gasPrice, BigInteger gasLimit) {
//        return new MyContract(BINARY, contractAddress, chain3j, transactionManager, gasPrice, gasLimit);
//    }
}
