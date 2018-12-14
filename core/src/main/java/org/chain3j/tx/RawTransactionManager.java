package org.chain3j.tx;

import java.io.IOException;
import java.math.BigInteger;

import org.chain3j.crypto.CipherException;
import org.chain3j.crypto.Credentials;
import org.chain3j.crypto.Hash;
import org.chain3j.crypto.RawTransaction;
import org.chain3j.crypto.TransactionEncoder;
import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.core.DefaultBlockParameterName;
import org.chain3j.protocol.core.methods.response.McGetTransactionCount;
import org.chain3j.protocol.core.methods.response.McSendTransaction;
import org.chain3j.tx.exceptions.TxHashMismatchException;
// import org.chain3j.tx.exceptions.ChainIdMismatchException;
import org.chain3j.tx.response.TransactionReceiptProcessor;
import org.chain3j.utils.Numeric;
import org.chain3j.utils.TxHashVerifier;


/**
 * TransactionManager implementation using Moac wallet file to create and sign transactions
 * locally.
 *
 * <p>This transaction manager provides support for specifying the chain id for transactions as per
 * <a href="https://github.com/ethereum/EIPs/issues/155">EIP155</a>.
 * 
 * <p>Use MOACTransactionEncoder and MOACTransaction
 * to sign and send the raw transaction object.
 */
public class RawTransactionManager extends TransactionManager {

    private final Chain3j chain3j;
    final Credentials credentials;

    private final byte chainId;

    protected TxHashVerifier txHashVerifier = new TxHashVerifier();

    public RawTransactionManager(Chain3j chain3j, Credentials credentials, byte chainId) {

        super(chain3j, credentials.getAddress());

        this.chain3j = chain3j;
        this.credentials = credentials;

        this.chainId = chainId;
    }

    public RawTransactionManager(
            Chain3j chain3j, Credentials credentials, byte chainId,
            TransactionReceiptProcessor transactionReceiptProcessor) {

        super(transactionReceiptProcessor, credentials.getAddress());

        this.chain3j = chain3j;
        this.credentials = credentials;
        this.chainId = chainId;
    }

    public RawTransactionManager(
            Chain3j chain3j, Credentials credentials, byte chainId, int attempts, long sleepDuration) {
        super(chain3j, attempts, sleepDuration, credentials.getAddress());

        this.chain3j = chain3j;
        this.credentials = credentials;
        this.chainId = chainId;
    }

    public RawTransactionManager(Chain3j chain3j, Credentials credentials) {
        this(chain3j, credentials, ChainId.NONE);
    }

    public RawTransactionManager(
            Chain3j chain3j, Credentials credentials, int attempts, int sleepDuration) {
        this(chain3j, credentials, ChainId.NONE, attempts, sleepDuration);
    }

    protected BigInteger getNonce() throws IOException {
        McGetTransactionCount mcGetTransactionCount = chain3j.mcGetTransactionCount(
                credentials.getAddress(), DefaultBlockParameterName.PENDING).send();

        return mcGetTransactionCount.getTransactionCount();
    }

    public TxHashVerifier getTxHashVerifier() {
        return txHashVerifier;
    }

    public void setTxHashVerifier(TxHashVerifier txHashVerifier) {
        this.txHashVerifier = txHashVerifier;
    }

    @Override
    public McSendTransaction sendTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to,
            String data, BigInteger value) throws IOException {

        BigInteger nonce = getNonce();

        RawTransaction rawTransaction = RawTransaction.createTransaction(
                nonce,
                gasPrice,
                gasLimit,
                to,
                value,
                data);

        return signAndSend(rawTransaction);
    }

    public McSendTransaction signAndSend(RawTransaction rawTransaction)
            throws IOException {

        byte[] signedMessage;
        if (this.chainId > ChainId.NONE) {
            int id = this.chainId;

            try {
                // MOAC sign message need to use chainid
                signedMessage = TransactionEncoder.signTxEIP155(rawTransaction, id, credentials);
            } catch (CipherException e) {
                //
                throw new IOException(e.getMessage());
            }

        } else {
            // signedMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
            throw new IOException("Invalid chain id for signing the MOAC rawTransaction!");
        }

        String hexValue = Numeric.toHexString(signedMessage);
        McSendTransaction mcSendTransaction = chain3j.mcSendRawTransaction(hexValue).send();

        if (mcSendTransaction != null && !mcSendTransaction.hasError()) {
            String txHashLocal = Hash.sha3(hexValue);
            String txHashRemote = mcSendTransaction.getTransactionHash();
            if (!txHashVerifier.verify(txHashLocal, txHashRemote)) {
                throw new TxHashMismatchException(txHashLocal, txHashRemote);
            }
        }

        return mcSendTransaction;
    }
}
