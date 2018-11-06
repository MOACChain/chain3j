package org.chain3j.tx.response;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.core.Request;
import org.chain3j.protocol.core.Response;
import org.chain3j.protocol.core.methods.response.McGetTransactionReceipt;
import org.chain3j.protocol.core.methods.response.TransactionReceipt;
import org.chain3j.protocol.exceptions.TransactionException;

import static org.hamcrest.CoreMatchers.sameInstance;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PollingTransactionReceiptProcessorTest {
    private static final String TRANSACTION_HASH = "0x00";
    private Chain3j chain3j;
    private long sleepDuration;
    private int attempts;
    private PollingTransactionReceiptProcessor processor;

    @Before
    public void setUp() {
        chain3j = mock(Chain3j.class);
        sleepDuration = 100;
        attempts = 3;
        processor = new PollingTransactionReceiptProcessor(chain3j, sleepDuration, attempts);
    }

    @Test
    public void returnsTransactionReceiptWhenItIsAvailableInstantly() throws Exception {
        TransactionReceipt transactionReceipt = new TransactionReceipt();
        doReturn(requestReturning(response(transactionReceipt)))
                .when(chain3j).mcGetTransactionReceipt(TRANSACTION_HASH);

        TransactionReceipt receipt = processor.waitForTransactionReceipt(TRANSACTION_HASH);

        assertThat(receipt, sameInstance(transactionReceipt));
    }

    @Test
    public void throwsTransactionExceptionWhenReceiptIsNotAvailableInTime() throws Exception {
        doReturn(requestReturning(response(null)))
                .when(chain3j).mcGetTransactionReceipt(TRANSACTION_HASH);

        try {
            processor.waitForTransactionReceipt(TRANSACTION_HASH);
            fail("call should fail with TransactionException");
        } catch (TransactionException e) {
            // this is expected
        }
    }

    private static <T extends Response<?>> Request<String, T> requestReturning(T response) {
        Request<String, T> request = mock(Request.class);
        try {
            when(request.send()).thenReturn(response);
        } catch (IOException e) {
            // this will never happen
        }
        return request;
    }

    private static McGetTransactionReceipt response(TransactionReceipt transactionReceipt) {
        McGetTransactionReceipt response = new McGetTransactionReceipt();
        response.setResult(transactionReceipt);
        return response;
    }
}
