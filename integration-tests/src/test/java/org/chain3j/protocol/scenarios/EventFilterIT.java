package org.chain3j.protocol.scenarios;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import org.chain3j.abi.EventEncoder;
import org.chain3j.abi.FunctionEncoder;
import org.chain3j.abi.FunctionReturnDecoder;
import org.chain3j.abi.TypeReference;
import org.chain3j.abi.datatypes.Event;
import org.chain3j.abi.datatypes.Function;
import org.chain3j.abi.datatypes.Type;
import org.chain3j.abi.datatypes.generated.Uint256;
import org.chain3j.crypto.Credentials;
import org.chain3j.protocol.core.DefaultBlockParameterName;
import org.chain3j.protocol.core.methods.request.McFilter;
import org.chain3j.protocol.core.methods.request.Transaction;
import org.chain3j.protocol.core.methods.response.Log;
import org.chain3j.protocol.core.methods.response.McEstimateGas;
import org.chain3j.protocol.core.methods.response.McLog;
import org.chain3j.protocol.core.methods.response.TransactionReceipt;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Filter scenario integration tests.
 */
public class EventFilterIT extends Scenario {

    // Deployed Fibonacci contract instance in testnet
    private static final String CONTRACT_ADDRESS = "0x3c05b2564139fb55820b18b72e94b2178eaace7d";
    private static final Integer testChainId = 101; 
    
    @Test
    public void testEventFilter() throws Exception {
        unlockAccount();

        Function function = createFibonacciFunction();
        String encodedFunction = FunctionEncoder.encode(function);

        BigInteger gas = estimateGas(encodedFunction);
        String transactionHash = sendTransaction(ALICE, CONTRACT_ADDRESS, gas, encodedFunction);

        TransactionReceipt transactionReceipt =
                waitForTransactionReceipt(transactionHash);

        assertFalse("Transaction execution ran out of gas",
                gas.equals(transactionReceipt.getGasUsed()));

        List<Log> logs = transactionReceipt.getLogs();
        assertFalse(logs.isEmpty());

        Log log = logs.get(0);

        List<String> topics = log.getTopics();
        assertThat(topics.size(), is(1));

        Event event = new Event("Notify",
                Arrays.asList(new TypeReference<Uint256>() {}, new TypeReference<Uint256>() {}));

        // check function signature - we only have a single topic our event signature,
        // there are no indexed parameters in this example
        String encodedEventSignature = EventEncoder.encode(event);
        assertThat(topics.get(0),
                is(encodedEventSignature));

        // verify our two event parameters
        List<Type> results = FunctionReturnDecoder.decode(
                log.getData(), event.getNonIndexedParameters());
        assertThat(results, equalTo(Arrays.asList(
                new Uint256(BigInteger.valueOf(7)), new Uint256(BigInteger.valueOf(13)))));

        // finally check it shows up in the event filter
        List<McLog.LogResult> filterLogs = createFilterForEvent(
                encodedEventSignature, CONTRACT_ADDRESS);
        assertFalse(filterLogs.isEmpty());
    }

    private BigInteger estimateGas(String encodedFunction) throws Exception {
        McEstimateGas moacEstimateGas = chain3j.mcEstimateGas(
                Transaction.createMcCallTransaction(ALICE.getAddress(), null, encodedFunction))
                .sendAsync().get();
        // this was coming back as 50,000,000 which is > the block gas limit of 4,712,388
        // see eth.getBlock("latest")
        return moacEstimateGas.getAmountUsed().divide(BigInteger.valueOf(100));
    }

    private String sendTransaction(
            Credentials credentials, String contractAddress, BigInteger gas,
            String encodedFunction) throws Exception {
        BigInteger nonce = getNonce(credentials.getAddress());
        Transaction transaction = Transaction.createFunctionCallTransaction(
                credentials.getAddress(), nonce, Transaction.DEFAULT_GAS, gas, contractAddress,
                encodedFunction);

        org.chain3j.protocol.core.methods.response.McSendTransaction transactionResponse =
                chain3j.mcSendTransaction(transaction).sendAsync().get();

        assertFalse(transactionResponse.hasError());

        return transactionResponse.getTransactionHash();
    }

    private List<McLog.LogResult> createFilterForEvent(
            String encodedEventSignature, String contractAddress) throws Exception {
        McFilter mcFilter = new McFilter(
                DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST,
                contractAddress
        );

        mcFilter.addSingleTopic(encodedEventSignature);

        McLog ethLog = chain3j.mcGetLogs(mcFilter).send();
        return ethLog.getLogs();
    }
}
