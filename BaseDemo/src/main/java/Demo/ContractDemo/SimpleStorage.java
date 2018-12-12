package Demo.ContractDemo;

import java.io.IOException;
import java.lang.String;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.Future;
import org.chain3j.abi.TypeReference;
import org.chain3j.abi.datatypes.Function;
import org.chain3j.abi.datatypes.Type;
import org.chain3j.abi.datatypes.generated.Uint256;
import org.chain3j.crypto.Credentials;
import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.core.methods.response.TransactionReceipt;
import org.chain3j.tx.Contract;
import org.chain3j.tx.TransactionManager;

public class SimpleStorage extends Contract {
    private static final String BINARY = "60806040526004361060485763ffffffff7c010000000000000000000000000000000000000000000000000000000060003504166360fe47b18114604d5780636d4ce63c146064575b600080fd5b348015605857600080fd5b5060626004356088565b005b348015606f57600080fd5b506076608d565b60408051918252519081900360200190f35b600055565b600054905600a165627a7a72305820d7a217080358ed205eb0bb5b96b85b93b3387efc29c9457e811f8c1198bff3a50029";

    private SimpleStorage(String contractAddress, Chain3j chain3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, chain3j, credentials, gasPrice, gasLimit);
    }

    private SimpleStorage(String contractAddress, Chain3j chain3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        super(contractAddress, chain3j, transactionManager, gasPrice, gasLimit);
    }

    public Future<TransactionReceipt> set(Uint256 x) throws IOException {
        Function function = new Function("set", Arrays.<Type>asList(x), Collections.<TypeReference<?>>emptyList());
          return executeCallSingleValueReturn(function);
//        return executeTransaction(function);
    }

    public Future<Uint256> get() throws Exception{
        Function function = new Function("get",
                Arrays.<Type>asList(),
                Arrays.<TypeReference<?>>asList(new TypeReference<Uint256>() {}));
        return executeCallSingleValueReturn(function);
    }

//    public static Future<SimpleStorage> deploy(Chain3j chain3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialValue) throws Exception {
//        return deploy(SimpleStorage.class, chain3j, credentials, gasPrice, gasLimit, BINARY, "", initialValue);
//    }
//
//    public static Future<SimpleStorage> deploy(Chain3j chain3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit, BigInteger initialValue) {
//          return deploy(SimpleStorage.class, chain3j, transactionManager, gasPrice, gasLimit, BINARY, "", initialValue);
//
//    }

    public static SimpleStorage load(String contractAddress, Chain3j chain3j, Credentials credentials, BigInteger gasPrice, BigInteger gasLimit) {
        return new SimpleStorage(contractAddress, chain3j, credentials, gasPrice, gasLimit);
    }

    public static SimpleStorage load(String contractAddress, Chain3j chain3j, TransactionManager transactionManager, BigInteger gasPrice, BigInteger gasLimit) {
        return new SimpleStorage(contractAddress, chain3j, transactionManager, gasPrice, gasLimit);
    }

}
