package org.chain3j.tx;

import java.io.IOException;
import java.math.BigInteger;

import org.chain3j.crypto.Credentials;
import org.chain3j.protocol.Chain3j;
import org.chain3j.tx.response.Callback;
import org.chain3j.tx.response.QueuingTransactionReceiptProcessor;
import org.chain3j.tx.response.TransactionReceiptProcessor;

/**
 * Simple RawTransactionManager derivative that manages nonces to facilitate multiple transactions
 * per block.
 */
public class FastRawTransactionManager extends RawTransactionManager {

    private volatile BigInteger nonce = BigInteger.valueOf(-1);

    public FastRawTransactionManager(Chain3j chain3j, Credentials credentials, byte chainId) {
        super(chain3j, credentials, chainId);
    }

    public FastRawTransactionManager(Chain3j chain3j, Credentials credentials) {
        super(chain3j, credentials);
    }

    public FastRawTransactionManager(
            Chain3j chain3j, Credentials credentials,
            TransactionReceiptProcessor transactionReceiptProcessor) {
        super(chain3j, credentials, ChainId.NONE, transactionReceiptProcessor);
    }

    public FastRawTransactionManager(
            Chain3j chain3j, Credentials credentials, byte chainId,
            TransactionReceiptProcessor transactionReceiptProcessor) {
        super(chain3j, credentials, chainId, transactionReceiptProcessor);
    }

    @Override
    protected synchronized BigInteger getNonce() throws IOException {
        if (nonce.signum() == -1) {
            // obtain lock
            nonce = super.getNonce();
        } else {
            nonce = nonce.add(BigInteger.ONE);
        }
        return nonce;
    }

    public BigInteger getCurrentNonce() {
        return nonce;
    }

    public synchronized void resetNonce() throws IOException {
        nonce = super.getNonce();
    }

    public synchronized void setNonce(BigInteger value) {
        nonce = value;
    }
}
