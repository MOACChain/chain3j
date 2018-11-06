package org.chain3j.tx.response;

import java.io.IOException;
import java.util.Optional;

import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.core.methods.response.McGetTransactionReceipt;
import org.chain3j.protocol.core.methods.response.TransactionReceipt;
import org.chain3j.protocol.exceptions.TransactionException;

/**
 * Abstraction for managing how we wait for transaction receipts to be generated on the network.
 */
public abstract class TransactionReceiptProcessor {

    private final Chain3j chain3j;

    public TransactionReceiptProcessor(Chain3j chain3j) {
        this.chain3j = chain3j;
    }

    public abstract TransactionReceipt waitForTransactionReceipt(
            String transactionHash)
            throws IOException, TransactionException;

    Optional<TransactionReceipt> sendTransactionReceiptRequest(
            String transactionHash) throws IOException, TransactionException {
        McGetTransactionReceipt transactionReceipt =
                chain3j.mcGetTransactionReceipt(transactionHash).send();
        if (transactionReceipt.hasError()) {
            throw new TransactionException("Error processing request: "
                    + transactionReceipt.getError().getMessage());
        }

        return transactionReceipt.getTransactionReceipt();
    }
}
