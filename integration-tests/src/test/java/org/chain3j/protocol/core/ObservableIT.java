package org.chain3j.protocol.core;

import java.math.BigInteger;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.Before;
import org.junit.Test;
import rx.Observable;
import rx.Subscription;

import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.core.methods.request.McFilter;
import org.chain3j.protocol.core.methods.response.McBlock;
import org.chain3j.protocol.http.HttpService;

import static org.junit.Assert.assertTrue;

/**
 * Observable callback tests.
 */
public class ObservableIT {

    private static final int EVENT_COUNT = 5;
    private static final int TIMEOUT_MINUTES = 5;

    private Chain3j chain3j;

    @Before
    public void setUp() {
        this.chain3j = Chain3j.build(new HttpService());
    }

    @Test
    public void testBlockObservable() throws Exception {
        run(chain3j.blockObservable(false));
    }

    @Test
    public void testPendingTransactionObservable() throws Exception {
        run(chain3j.pendingTransactionObservable());
    }

    @Test
    public void testTransactionObservable() throws Exception {
        run(chain3j.transactionObservable());
    }

    @Test
    public void testLogObservable() throws Exception {
        run(chain3j.mcLogObservable(new McFilter()));
    }

    @Test
    public void testReplayObservable() throws Exception {
        run(chain3j.replayBlocksObservable(
                new DefaultBlockParameterNumber(0),
                new DefaultBlockParameterNumber(EVENT_COUNT), true));
    }

    @Test
    public void testCatchUpToLatestAndSubscribeToNewBlocksObservable() throws Exception {
        McBlock mcBlock = chain3j.mcGetBlockByNumber(DefaultBlockParameterName.LATEST, false)
                .send();
        BigInteger latestBlockNumber = mcBlock.getBlock().getNumber();
        run(chain3j.catchUpToLatestAndSubscribeToNewBlocksObservable(
                new DefaultBlockParameterNumber(latestBlockNumber.subtract(BigInteger.ONE)),
                false));
    }

    private <T> void run(Observable<T> observable) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(EVENT_COUNT);
        CountDownLatch completedLatch = new CountDownLatch(EVENT_COUNT);

        Subscription subscription = observable.subscribe(
                x -> countDownLatch.countDown(),
                Throwable::printStackTrace,
                completedLatch::countDown
        );

        countDownLatch.await(TIMEOUT_MINUTES, TimeUnit.MINUTES);
        subscription.unsubscribe();
        completedLatch.await(1, TimeUnit.SECONDS);
        assertTrue(subscription.isUnsubscribed());
    }
}
