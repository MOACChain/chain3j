package org.chain3j.tx.response;

import java.io.IOException;
import java.util.Optional;

import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.core.methods.response.TransactionReceipt;
import org.chain3j.protocol.exceptions.TransactionException;

/**
 * With each provided transaction hash, poll until we obtain a transaction receipt.
 */
public class PollingTransactionReceiptProcessor extends TransactionReceiptProcessor {

    private final long sleepDuration;
    private final int attempts;

    public PollingTransactionReceiptProcessor(Chain3j chain3j, long sleepDuration, int attempts) {
        super(chain3j);
        this.sleepDuration = sleepDuration;
        this.attempts = attempts;
    }

    @Override
    public TransactionReceipt waitForTransactionReceipt(
            String transactionHash)
            throws IOException, TransactionException {

        return getTransactionReceipt(transactionHash, sleepDuration, attempts);
    }

    private TransactionReceipt getTransactionReceipt(
            String transactionHash, long sleepDuration, int attempts)
            throws IOException, TransactionException {

        Optional<TransactionReceipt> receiptOptional =
                sendTransactionReceiptRequest(transactionHash);
        for (int i = 0; i < attempts; i++) {
            if (!receiptOptional.isPresent()) {
                try {
                    Thread.sleep(sleepDuration);
                } catch (InterruptedException e) {
                    throw new TransactionException(e);
                }
                receiptOptional = sendTransactionReceiptRequest(transactionHash);
            } else {
                return receiptOptional.get();
            }
        }

        throw new TransactionException("WalletDemo receipt was not generated after "
                + ((sleepDuration * attempts) / 1000
                + " seconds for transaction: " + transactionHash));
    }
}
