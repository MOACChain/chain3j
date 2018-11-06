package org.chain3j.tx.response;

import java.io.IOException;

import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.core.methods.response.TransactionReceipt;
import org.chain3j.protocol.exceptions.TransactionException;

/**
 * Return an {@link EmptyTransactionReceipt} receipt back to callers containing only the
 * transaction hash.
 */
public class NoOpProcessor extends TransactionReceiptProcessor {

    public NoOpProcessor(Chain3j chain3j) {
        super(chain3j);
    }

    @Override
    public TransactionReceipt waitForTransactionReceipt(String transactionHash)
            throws IOException, TransactionException {
        return new EmptyTransactionReceipt(transactionHash);
    }
}
