package org.chain3j.tx;

import java.io.IOException;
import java.math.BigInteger;

import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.core.methods.response.McSendTransaction;

/**
 * Transaction manager implementation for read-only operations on smart contracts.
 */
public class ReadonlyTransactionManager extends TransactionManager {

    public ReadonlyTransactionManager(Chain3j chain3j, String fromAddress) {
        super(chain3j, fromAddress);
    }

    @Override
    public McSendTransaction sendTransaction(
            BigInteger gasPrice, BigInteger gasLimit, String to, 
            String data, BigInteger value)
            throws IOException {
        throw new UnsupportedOperationException(
                "Only read operations are supported by this transaction manager");
    }
}
