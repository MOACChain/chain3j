package org.chain3j.tx.response;

import org.chain3j.protocol.core.methods.response.TransactionReceipt;

/**
 * WalletDemo receipt processor callback.
 */
public interface Callback {
    void accept(TransactionReceipt transactionReceipt);

    void exception(Exception exception);
}
