package org.chain3j.tx;

import java.math.BigDecimal;

import org.junit.Before;
import org.junit.Test;

import org.chain3j.crypto.Credentials;
import org.chain3j.crypto.SampleKeys;
import org.chain3j.protocol.core.methods.response.TransactionReceipt;
import org.chain3j.tx.exceptions.TxHashMismatchException;
import org.chain3j.utils.Convert;

public class RawTransactionManagerTest extends ManagedTransactionTester {

    @Test(expected = TxHashMismatchException.class)
    public void testTxHashMismatch() throws Exception {
        TransactionReceipt transactionReceipt = prepareTransfer();
        prepareTransaction(transactionReceipt);

        TransactionManager transactionManager =
                new RawTransactionManager(chain3j, SampleKeys.CREDENTIALS, (byte)101);
        Transfer transfer = new Transfer(chain3j, transactionManager);
        transfer.sendFunds(ADDRESS, BigDecimal.ONE, Convert.Unit.MC).send();
    }
}
