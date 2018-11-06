package org.chain3j.protocol.scenarios;

import java.math.BigInteger;

import org.junit.Test;

import org.chain3j.crypto.RawTransaction;
import org.chain3j.crypto.TransactionEncoder;
import org.chain3j.protocol.core.DefaultBlockParameterName;
import org.chain3j.protocol.core.methods.response.McGetTransactionCount;
import org.chain3j.protocol.core.methods.response.McSendTransaction;
import org.chain3j.protocol.core.methods.response.TransactionReceipt;
import org.chain3j.utils.Convert;
import org.chain3j.utils.Numeric;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Create, sign and send a raw transaction.
 * For MOAC, this requires a chainId to sign all the transaction.
 * 
 */
public class CreateRawTransactionIT extends Scenario {

    private static final Integer testChainId = 101; 
    
    @Test
    public void testTransferMc() throws Exception {
        BigInteger nonce = getNonce(ALICE.getAddress());
        RawTransaction rawTransaction = createMcTransaction(
                nonce, BOB.getAddress());

        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, testChainId, ALICE);
        String hexValue = Numeric.toHexString(signedMessage);

        McSendTransaction mcSendTransaction =
                chain3j.mcSendRawTransaction(hexValue).sendAsync().get();
        String transactionHash = mcSendTransaction.getTransactionHash();

        assertFalse(transactionHash.isEmpty());

        TransactionReceipt transactionReceipt =
                waitForTransactionReceipt(transactionHash);

        assertThat(transactionReceipt.getTransactionHash(), is(transactionHash));
    }

    @Test
    public void testDeploySmartContract() throws Exception {
        BigInteger nonce = getNonce(ALICE.getAddress());
        RawTransaction rawTransaction = createSmartContractTransaction(nonce);

        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, testChainId, ALICE);
        String hexValue = Numeric.toHexString(signedMessage);

        McSendTransaction mcSendTransaction =
                chain3j.mcSendRawTransaction(hexValue).sendAsync().get();
        String transactionHash = mcSendTransaction.getTransactionHash();

        assertFalse(transactionHash.isEmpty());

        TransactionReceipt transactionReceipt =
                waitForTransactionReceipt(transactionHash);

        assertThat(transactionReceipt.getTransactionHash(), is(transactionHash));

        assertFalse("Contract execution ran out of gas",
                rawTransaction.getGasLimit().equals(transactionReceipt.getGasUsed()));
    }

    private static RawTransaction createMcTransaction(BigInteger nonce, String toAddress) {
        BigInteger value = Convert.toSha("0.5", Convert.Unit.MC).toBigInteger();

        return RawTransaction.createMcTransaction(
                nonce, GAS_PRICE, GAS_LIMIT, toAddress, value);
    }

    private static RawTransaction createSmartContractTransaction(BigInteger nonce)
            throws Exception {
        return RawTransaction.createContractTransaction(
                nonce, GAS_PRICE, GAS_LIMIT, BigInteger.ZERO, 
                getFibonacciSolidityBinary());
    }

    BigInteger getNonce(String address) throws Exception {
        McGetTransactionCount mcGetTransactionCount = chain3j.mcGetTransactionCount(
                address, DefaultBlockParameterName.LATEST).sendAsync().get();

        return mcGetTransactionCount.getTransactionCount();
    }
}
