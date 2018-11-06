package org.chain3j.tx;

import java.io.IOException;
import java.math.BigInteger;

import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.core.methods.request.Transaction;
import org.chain3j.protocol.core.methods.response.McSendTransaction;
import org.chain3j.tx.response.TransactionReceiptProcessor;

/**
 * TransactionManager implementation for using an Moac node to transact.
 *
 * <p><b>Note</b>: accounts must be unlocked on the node for transactions to be successful.
 */
public class ClientTransactionManager extends TransactionManager {

    private final Chain3j chain3j;

    public ClientTransactionManager(
            Chain3j chain3j, String fromAddress) {
        super(chain3j, fromAddress);
        this.chain3j = chain3j;
    }

    public ClientTransactionManager(
            Chain3j chain3j, String fromAddress, int attempts, int sleepDuration) {
        super(chain3j, attempts, sleepDuration, fromAddress);
        this.chain3j = chain3j;
    }

    public ClientTransactionManager(
            Chain3j chain3j, String fromAddress,
            TransactionReceiptProcessor transactionReceiptProcessor) {
        super(transactionReceiptProcessor, fromAddress);
        this.chain3j = chain3j;
    }

    @Override
    public McSendTransaction sendTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to,
            String data, BigInteger value)
            throws IOException {

        Transaction transaction = new Transaction(
                getFromAddress(), null, gasPrice, gasLimit, to, value, data);

        return chain3j.mcSendTransaction(transaction)
                .send();
    }
}
