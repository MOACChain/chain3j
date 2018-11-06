package org.chain3j.tx;

import java.io.IOException;

import org.junit.Before;

import org.chain3j.crypto.Credentials;
import org.chain3j.crypto.SampleKeys;
import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.core.DefaultBlockParameterName;
import org.chain3j.protocol.core.Request;
import org.chain3j.protocol.core.methods.response.McGasPrice;
import org.chain3j.protocol.core.methods.response.McGetTransactionCount;
import org.chain3j.protocol.core.methods.response.McGetTransactionReceipt;
import org.chain3j.protocol.core.methods.response.McSendTransaction;
import org.chain3j.protocol.core.methods.response.TransactionReceipt;
import org.chain3j.utils.TxHashVerifier;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public abstract class ManagedTransactionTester {

    static final String ADDRESS = "0x3d6cb163f7c72d20b0fcd6baae5889329d138a4a";
    static final String TRANSACTION_HASH = "0xHASH";
    protected Chain3j chain3j;
    protected TxHashVerifier txHashVerifier;

    @Before
    public void setUp() throws Exception {
        chain3j = mock(Chain3j.class);
        txHashVerifier = mock(TxHashVerifier.class);
        when(txHashVerifier.verify(any(), any())).thenReturn(true);
    }

    public TransactionManager getVerifiedTransactionManager(Credentials credentials,
                                                            Integer chainId, 
                                                            int attempts, 
                                                            int sleepDuration) {
        RawTransactionManager transactionManager =
                new RawTransactionManager(chain3j, credentials, chainId.byteValue(), attempts, sleepDuration);
        transactionManager.setTxHashVerifier(txHashVerifier);
        return transactionManager;
    }

    public TransactionManager getVerifiedTransactionManager(Credentials credentials, Integer chainId) {
        RawTransactionManager transactionManager = new RawTransactionManager(chain3j, 
                credentials, chainId.byteValue());
        transactionManager.setTxHashVerifier(txHashVerifier);
        return transactionManager;
    }

    void prepareTransaction(TransactionReceipt transactionReceipt) throws IOException {
        prepareNonceRequest();
        prepareTransactionRequest();
        prepareTransactionReceipt(transactionReceipt);
    }

    @SuppressWarnings("unchecked")
    void prepareNonceRequest() throws IOException {
        McGetTransactionCount mcGetTransactionCount = new McGetTransactionCount();
        mcGetTransactionCount.setResult("0x1");

        Request<?, McGetTransactionCount> transactionCountRequest = mock(Request.class);
        when(transactionCountRequest.send())
                .thenReturn(mcGetTransactionCount);
        when(chain3j.mcGetTransactionCount(SampleKeys.ADDRESS, DefaultBlockParameterName.PENDING))
                .thenReturn((Request) transactionCountRequest);
    }

    @SuppressWarnings("unchecked")
    void prepareTransactionRequest() throws IOException {
        McSendTransaction mcSendTransaction = new McSendTransaction();
        mcSendTransaction.setResult(TRANSACTION_HASH);

        Request<?, McSendTransaction> rawTransactionRequest = mock(Request.class);
        when(rawTransactionRequest.send()).thenReturn(mcSendTransaction);
        when(chain3j.mcSendRawTransaction(any(String.class)))
                .thenReturn((Request) rawTransactionRequest);
    }

    @SuppressWarnings("unchecked")
    void prepareTransactionReceipt(TransactionReceipt transactionReceipt) throws IOException {
        McGetTransactionReceipt mcGetTransactionReceipt = new McGetTransactionReceipt();
        mcGetTransactionReceipt.setResult(transactionReceipt);

        Request<?, McGetTransactionReceipt> getTransactionReceiptRequest = mock(Request.class);
        when(getTransactionReceiptRequest.send())
                .thenReturn(mcGetTransactionReceipt);
        when(chain3j.mcGetTransactionReceipt(TRANSACTION_HASH))
                .thenReturn((Request) getTransactionReceiptRequest);
    }

    @SuppressWarnings("unchecked")
    protected TransactionReceipt prepareTransfer() throws IOException {
        TransactionReceipt transactionReceipt = new TransactionReceipt();
        transactionReceipt.setTransactionHash(TRANSACTION_HASH);
        transactionReceipt.setStatus("0x1");
        prepareTransaction(transactionReceipt);

        McGasPrice mcGasPrice = new McGasPrice();
        mcGasPrice.setResult("0x1");

        Request<?, McGasPrice> gasPriceRequest = mock(Request.class);
        when(gasPriceRequest.send()).thenReturn(mcGasPrice);
        when(chain3j.mcGasPrice()).thenReturn((Request) gasPriceRequest);

        return transactionReceipt;
    }
}
