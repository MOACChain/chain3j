package org.chain3j.tx;

import java.io.IOException;
import java.math.BigInteger;

import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.core.methods.response.McSendTransaction;
import org.chain3j.protocol.core.methods.response.TransactionReceipt;
import org.chain3j.protocol.exceptions.TransactionException;
import org.chain3j.tx.response.PollingTransactionReceiptProcessor;
import org.chain3j.tx.response.TransactionReceiptProcessor;

import static org.chain3j.protocol.core.JsonRpc2_0Chain3j.DEFAULT_BLOCK_TIME;

/**
 * WalletDemo manager abstraction for executing transactions with Moac client via
 * various mechanisms.
 * Notice the WalletDemo requires
 */
public abstract class TransactionManager {

    public static final int DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH = 40;
    public static final long DEFAULT_POLLING_FREQUENCY = DEFAULT_BLOCK_TIME;

    private final TransactionReceiptProcessor transactionReceiptProcessor;
    private final String fromAddress;

    protected TransactionManager(
            TransactionReceiptProcessor transactionReceiptProcessor, 
            String fromAddress) {
        this.transactionReceiptProcessor = transactionReceiptProcessor;
        this.fromAddress = fromAddress;
    }

    protected TransactionManager(Chain3j chain3j, String fromAddress) {
        this(new PollingTransactionReceiptProcessor(
                        chain3j, DEFAULT_POLLING_FREQUENCY, DEFAULT_POLLING_ATTEMPTS_PER_TX_HASH),
                fromAddress);
    }

    protected TransactionManager(
            Chain3j chain3j, int attempts, long sleepDuration, String fromAddress) {
        this(new PollingTransactionReceiptProcessor(chain3j, sleepDuration, attempts), 
        fromAddress);
    }

    // Send the TX for MotherChain
    protected TransactionReceipt executeTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to,
            String data, BigInteger value)
            throws IOException, TransactionException {

        McSendTransaction mcSendTransaction = sendTransaction(
                gasPrice, gasLimit, to, data, value);
        return processResponse(mcSendTransaction);
    }

    public abstract McSendTransaction sendTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to,
            String data, BigInteger value)
            throws IOException;

    public String getFromAddress() {
        return fromAddress;
    }

    private TransactionReceipt processResponse(McSendTransaction transactionResponse)
            throws IOException, TransactionException {
        if (transactionResponse.hasError()) {
            throw new RuntimeException("Error processing transaction request: "
                    + transactionResponse.getError().getMessage());
        }

        String transactionHash = transactionResponse.getTransactionHash();

        return transactionReceiptProcessor.waitForTransactionReceipt(transactionHash);
    }


}
