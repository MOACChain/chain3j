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
import org.chain3j.abi.datatypes.Address;
import org.chain3j.abi.datatypes.Bool;
import org.chain3j.abi.datatypes.Event;
import org.chain3j.abi.datatypes.Function;
import org.chain3j.abi.datatypes.Type;
import org.chain3j.abi.datatypes.Utf8String;
import org.chain3j.abi.datatypes.generated.Uint256;
import org.chain3j.abi.datatypes.generated.Uint8;
import org.chain3j.crypto.Credentials;
import org.chain3j.crypto.RawTransaction;
import org.chain3j.crypto.TransactionEncoder;
import org.chain3j.protocol.core.DefaultBlockParameterName;
import org.chain3j.protocol.core.methods.request.Transaction;
import org.chain3j.protocol.core.methods.response.Log;
import org.chain3j.protocol.core.methods.response.McSendTransaction;
import org.chain3j.protocol.core.methods.response.TransactionReceipt;
import org.chain3j.utils.Convert;
import org.chain3j.utils.Numeric;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * Integration test demonstrating integration with
 * <a href="https://github.com/ethereum/EIPs/issues/20">EIP-20</a>. Solidity implementation is
 * taken from <a href="https://github.com/ConsenSys/Tokens">ConsenSys Tokens</a>.
 */
public class HumanStandardTokenIT extends Scenario {

    @Test
    public void testContract() throws Exception {

        // Test on testnet
        Integer chainId = 101;

        // deploy contract
        BigInteger aliceQty = BigInteger.valueOf(1_000_000);

        String contractAddress = createContract(ALICE, aliceQty);

        assertThat(getTotalSupply(contractAddress), is(aliceQty));

        confirmBalance(ALICE.getAddress(), contractAddress, aliceQty);

        // transfer tokens
        BigInteger transferQuantity = BigInteger.valueOf(100_000);

        sendTransferTokensTransaction(
                ALICE, BOB.getAddress(), contractAddress, transferQuantity, chainId);

        aliceQty = aliceQty.subtract(transferQuantity);

        BigInteger bobQty = BigInteger.ZERO;
        bobQty = bobQty.add(transferQuantity);

        confirmBalance(
                ALICE.getAddress(), contractAddress, aliceQty);
        confirmBalance(BOB.getAddress(), contractAddress, bobQty);

        // set an allowance
        confirmAllowance(ALICE.getAddress(), BOB.getAddress(), contractAddress, BigInteger.ZERO);

        transferQuantity = BigInteger.valueOf(50);
        sendApproveTransaction(ALICE, BOB.getAddress(), transferQuantity, contractAddress, chainId);

        confirmAllowance(
                ALICE.getAddress(), BOB.getAddress(), contractAddress, transferQuantity);

        // perform a transfer
        transferQuantity = BigInteger.valueOf(25);

        sendTransferFromTransaction(BOB,
                ALICE.getAddress(), BOB.getAddress(), transferQuantity, contractAddress, chainId);

        aliceQty = aliceQty.subtract(transferQuantity);
        bobQty = bobQty.add(transferQuantity);

        confirmBalance(
                ALICE.getAddress(), contractAddress, aliceQty);
        confirmBalance(BOB.getAddress(), contractAddress, bobQty);
    }

    private BigInteger getTotalSupply(String contractAddress) throws Exception {
        Function function = totalSupply();
        String responseValue = callSmartContractFunction(function, contractAddress);

        List<Type> response = FunctionReturnDecoder.decode(
                responseValue, function.getOutputParameters());

        assertThat(response.size(), is(1));
        return (BigInteger) response.get(0).getValue();
    }

    private void confirmBalance(
            String address, String contractAddress, BigInteger expected) throws Exception {
        Function function = balanceOf(address);
        String responseValue = callSmartContractFunction(function, contractAddress);

        List<Type> response = FunctionReturnDecoder.decode(
                responseValue, function.getOutputParameters());
        assertThat(response.size(), is(1));
        assertThat(response.get(0), equalTo(new Uint256(expected)));
    }

    private void confirmAllowance(String owner, String spender, String contractAddress,
                                        BigInteger expected) throws Exception {
        Function function = allowance(owner, spender);
        String responseValue = callSmartContractFunction(function, contractAddress);

        List<Type> response = FunctionReturnDecoder.decode(
                responseValue, function.getOutputParameters());

        assertThat(response.size(), is(function.getOutputParameters().size()));
        assertThat(response.get(0), equalTo(new Uint256(expected)));
    }

    private String createContract(
            Credentials credentials, BigInteger initialSupply) throws Exception {
        String createTransactionHash = sendCreateContractTransaction(credentials, initialSupply);
        assertFalse(createTransactionHash.isEmpty());

        TransactionReceipt createTransactionReceipt =
                waitForTransactionReceipt(createTransactionHash);

        assertThat(createTransactionReceipt.getTransactionHash(), is(createTransactionHash));

        assertFalse("Contract execution ran out of gas",
                createTransactionReceipt.getGasUsed().equals(GAS_LIMIT));

        String contractAddress = createTransactionReceipt.getContractAddress();

        assertNotNull(contractAddress);
        return contractAddress;
    }

    // If send with RawTransaction, need to input chainId
    private String sendCreateContractTransaction(
            Credentials credentials, BigInteger initialSupply) throws Exception {
        BigInteger nonce = getNonce(credentials.getAddress());
        Integer testChainId = 101; 

        String encodedConstructor =
                FunctionEncoder.encodeConstructor(
                        Arrays.asList(
                                new Uint256(initialSupply),
                                new Utf8String("chain3j tokens"),
                                new Uint8(BigInteger.TEN),
                                new Utf8String("w3j$")));

        RawTransaction rawTransaction = RawTransaction.createContractTransaction(
                nonce,
                GAS_PRICE,
                GAS_LIMIT,
                BigInteger.ZERO,
                getHumanStandardTokenBinary() + encodedConstructor);

        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, testChainId, credentials);
        String hexValue = Numeric.toHexString(signedMessage);

        McSendTransaction transactionResponse = chain3j.mcSendRawTransaction(hexValue)
                .sendAsync().get();

        return transactionResponse.getTransactionHash();
    }

    private void sendTransferTokensTransaction(
            Credentials credentials, String to, String contractAddress, BigInteger qty, Integer chainId)
            throws Exception {

        Function function = transfer(to, qty);
        String functionHash = execute(credentials, function, contractAddress, chainId);

        TransactionReceipt transferTransactionReceipt =
                waitForTransactionReceipt(functionHash);
        assertThat(transferTransactionReceipt.getTransactionHash(), is(functionHash));

        List<Log> logs = transferTransactionReceipt.getLogs();
        assertFalse(logs.isEmpty());
        Log log = logs.get(0);

        // verify the event was called with the function parameters
        List<String> topics = log.getTopics();
        assertThat(topics.size(), is(3));

        Event transferEvent = transferEvent();

        // check function signature - we only have a single topic our event signature,
        // there are no indexed parameters in this example
        String encodedEventSignature = EventEncoder.encode(transferEvent);
        assertThat(topics.get(0), is(encodedEventSignature));
        assertThat(new Address(topics.get(1)), is(new Address(credentials.getAddress())));
        assertThat(new Address(topics.get(2)), is(new Address(to)));

        // verify qty transferred
        List<Type> results = FunctionReturnDecoder.decode(
                log.getData(), transferEvent.getNonIndexedParameters());
        assertThat(results, equalTo(Collections.singletonList(new Uint256(qty))));
    }

    private void sendApproveTransaction(
            Credentials credentials, String spender, BigInteger value,
            String contractAddress, Integer chainId) throws Exception {
        Function function = approve(spender, value);
        String functionHash = execute(credentials, function, contractAddress, chainId);

        TransactionReceipt transferTransactionReceipt =
                waitForTransactionReceipt(functionHash);
        assertThat(transferTransactionReceipt.getTransactionHash(), is(functionHash));

        List<Log> logs = transferTransactionReceipt.getLogs();
        assertFalse(logs.isEmpty());
        Log log = logs.get(0);

        // verify the event was called with the function parameters
        List<String> topics = log.getTopics();
        assertThat(topics.size(), is(3));

        // event Transfer(address indexed _from, address indexed _to, uint256 _value);
        Event event = approvalEvent();

        // check function signature - we only have a single topic our event signature,
        // there are no indexed parameters in this example
        String encodedEventSignature = EventEncoder.encode(event);
        assertThat(topics.get(0), is(encodedEventSignature));
        assertThat(new Address(topics.get(1)), is(new Address(credentials.getAddress())));
        assertThat(new Address(topics.get(2)), is(new Address(spender)));

        // verify our two event parameters
        List<Type> results = FunctionReturnDecoder.decode(
                log.getData(), event.getNonIndexedParameters());
        assertThat(results, equalTo(Collections.singletonList(new Uint256(value))));
    }

    public void sendTransferFromTransaction(
            Credentials credentials, String from, String to, BigInteger value,
            String contractAddress, Integer chainId) throws Exception {

        Function function = transferFrom(from, to, value);
        String functionHash = execute(credentials, function, contractAddress, chainId);

        TransactionReceipt transferTransactionReceipt =
                waitForTransactionReceipt(functionHash);
        assertThat(transferTransactionReceipt.getTransactionHash(), is(functionHash));

        List<Log> logs = transferTransactionReceipt.getLogs();
        assertFalse(logs.isEmpty());
        Log log = logs.get(0);

        Event transferEvent = transferEvent();
        List<String> topics = log.getTopics();

        // check function signature - we only have a single topic our event signature,
        // there are no indexed parameters in this example
        String encodedEventSignature = EventEncoder.encode(transferEvent);
        assertThat(topics.get(0), is(encodedEventSignature));
        assertThat(new Address(topics.get(1)), is(new Address(from)));
        assertThat(new Address(topics.get(2)), is(new Address(to)));

        // verify qty transferred
        List<Type> results = FunctionReturnDecoder.decode(
                log.getData(), transferEvent.getNonIndexedParameters());
        assertThat(results, equalTo(Collections.singletonList(new Uint256(value))));
    }

    private String execute(
            Credentials credentials, Function function, String contractAddress, 
            Integer chainId) throws Exception {
        BigInteger nonce = getNonce(credentials.getAddress());
        BigInteger value = Convert.toSha("0", Convert.Unit.MC).toBigInteger();

        String encodedFunction = FunctionEncoder.encode(function);

        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                GAS_PRICE,
                GAS_LIMIT,
                contractAddress, value,
                encodedFunction);

        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
        String hexValue = Numeric.toHexString(signedMessage);

        McSendTransaction transactionResponse = chain3j.mcSendRawTransaction(hexValue)
                .sendAsync().get();

        return transactionResponse.getTransactionHash();
    }

    private String callSmartContractFunction(
            Function function, String contractAddress) throws Exception {
        String encodedFunction = FunctionEncoder.encode(function);

        org.chain3j.protocol.core.methods.response.McCall response = chain3j.mcCall(
                Transaction.createMcCallTransaction(
                        ALICE.getAddress(), contractAddress, encodedFunction),
                DefaultBlockParameterName.LATEST)
                .sendAsync().get();

        return response.getValue();
    }

    private Function totalSupply() {
        return new Function(
                "totalSupply",
                Collections.emptyList(),
                Collections.singletonList(new TypeReference<Uint256>() {}));
    }

    private Function balanceOf(String owner) {
        return new Function(
                "balanceOf",
                Collections.singletonList(new Address(owner)),
                Collections.singletonList(new TypeReference<Uint256>() {}));
    }

    private Function transfer(String to, BigInteger value) {
        return new Function(
                "transfer",
                Arrays.asList(new Address(to), new Uint256(value)),
                Collections.singletonList(new TypeReference<Bool>() {}));
    }

    private Function allowance(String owner, String spender) {
        return new Function(
                "allowance",
                Arrays.asList(new Address(owner), new Address(spender)),
                Collections.singletonList(new TypeReference<Uint256>() {}));
    }

    private Function approve(String spender, BigInteger value) {
        return new Function(
                "approve",
                Arrays.asList(new Address(spender), new Uint256(value)),
                Collections.singletonList(new TypeReference<Bool>() {}));
    }

    private Function transferFrom(String from, String to, BigInteger value) {
        return new Function(
                "transferFrom",
                Arrays.asList(new Address(from), new Address(to), new Uint256(value)),
                Collections.singletonList(new TypeReference<Bool>() {}));
    }

    private Event transferEvent() {
        return new Event(
                "Transfer",
                Arrays.asList(
                        new TypeReference<Address>(true) {},
                        new TypeReference<Address>(true) {},
                        new TypeReference<Uint256>() {}));
    }

    private Event approvalEvent() {
        return new Event(
                "Approval",
                Arrays.asList(
                        new TypeReference<Address>(true) {},
                        new TypeReference<Address>(true) {},
                        new TypeReference<Uint256>() {}));
    }

    private static String getHumanStandardTokenBinary() throws Exception {
        return load("/solidity/contracts/build/HumanStandardToken.bin");
    }
}
