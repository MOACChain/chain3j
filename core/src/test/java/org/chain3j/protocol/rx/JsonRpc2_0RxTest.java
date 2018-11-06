package org.chain3j.protocol.rx;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.mockito.stubbing.OngoingStubbing;
import rx.Observable;
import rx.Subscription;

import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.Chain3jService;
import org.chain3j.protocol.ObjectMapperFactory;
import org.chain3j.protocol.core.DefaultBlockParameterNumber;
import org.chain3j.protocol.core.Request;
import org.chain3j.protocol.core.methods.response.McBlock;
import org.chain3j.protocol.core.methods.response.McFilter;
import org.chain3j.protocol.core.methods.response.McLog;
import org.chain3j.protocol.core.methods.response.McUninstallFilter;
import org.chain3j.utils.Numeric;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class JsonRpc2_0RxTest {

    private final ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();

    private Chain3j chain3j;

    private Chain3jService chain3jService;

    @Before
    public void setUp() {
        chain3jService = mock(Chain3jService.class);
        chain3j = Chain3j.build(chain3jService, 1000, Executors.newSingleThreadScheduledExecutor());
    }

    @Test
    public void testReplayBlocksObservable() throws Exception {

        List<McBlock> ethBlocks = Arrays.asList(createBlock(0), createBlock(1), createBlock(2));

        OngoingStubbing<McBlock> stubbing =
                when(chain3jService.send(any(Request.class), eq(McBlock.class)));
        for (McBlock ethBlock : ethBlocks) {
            stubbing = stubbing.thenReturn(ethBlock);
        }

        Observable<McBlock> observable = chain3j.replayBlocksObservable(
                new DefaultBlockParameterNumber(BigInteger.ZERO),
                new DefaultBlockParameterNumber(BigInteger.valueOf(2)),
                false);

        CountDownLatch transactionLatch = new CountDownLatch(ethBlocks.size());
        CountDownLatch completedLatch = new CountDownLatch(1);

        List<McBlock> results = new ArrayList<>(ethBlocks.size());
        Subscription subscription = observable.subscribe(
                result -> {
                    results.add(result);
                    transactionLatch.countDown();
                },
                throwable -> fail(throwable.getMessage()),
                () -> completedLatch.countDown());

        transactionLatch.await(1, TimeUnit.SECONDS);
        assertThat(results, equalTo(ethBlocks));

        subscription.unsubscribe();

        completedLatch.await(1, TimeUnit.SECONDS);
        assertTrue(subscription.isUnsubscribed());
    }

    @Test
    public void testReplayBlocksDescendingObservable() throws Exception {

        List<McBlock> ethBlocks = Arrays.asList(createBlock(2), createBlock(1), createBlock(0));

        OngoingStubbing<McBlock> stubbing =
                when(chain3jService.send(any(Request.class), eq(McBlock.class)));
        for (McBlock ethBlock : ethBlocks) {
            stubbing = stubbing.thenReturn(ethBlock);
        }

        Observable<McBlock> observable = chain3j.replayBlocksObservable(
                new DefaultBlockParameterNumber(BigInteger.ZERO),
                new DefaultBlockParameterNumber(BigInteger.valueOf(2)),
                false, false);

        CountDownLatch transactionLatch = new CountDownLatch(ethBlocks.size());
        CountDownLatch completedLatch = new CountDownLatch(1);

        List<McBlock> results = new ArrayList<>(ethBlocks.size());
        Subscription subscription = observable.subscribe(
                result -> {
                    results.add(result);
                    transactionLatch.countDown();
                },
                throwable -> fail(throwable.getMessage()),
                () -> completedLatch.countDown());

        transactionLatch.await(1, TimeUnit.SECONDS);
        assertThat(results, equalTo(ethBlocks));

        subscription.unsubscribe();

        completedLatch.await(1, TimeUnit.SECONDS);
        assertTrue(subscription.isUnsubscribed());
    }

    @Test
    public void testCatchUpToLatestAndSubscribeToNewBlockObservable() throws Exception {
        List<McBlock> expected = Arrays.asList(
                createBlock(0), createBlock(1), createBlock(2),
                createBlock(3), createBlock(4), createBlock(5),
                createBlock(6));

        List<McBlock> ethBlocks = Arrays.asList(
                expected.get(2),  // greatest block
                expected.get(0), expected.get(1), expected.get(2),
                expected.get(4), // greatest block
                expected.get(3), expected.get(4),
                expected.get(4),  // greatest block
                expected.get(5),  // initial response from ethGetFilterLogs call
                expected.get(6)); // subsequent block from new block observable

        OngoingStubbing<McBlock> stubbing =
                when(chain3jService.send(any(Request.class), eq(McBlock.class)));
        for (McBlock ethBlock : ethBlocks) {
            stubbing = stubbing.thenReturn(ethBlock);
        }

        McFilter ethFilter = objectMapper.readValue(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": \"0x1\"\n"
                        + "}", McFilter.class);
        McLog ethLog = objectMapper.readValue(
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":["
                        + "\"0x31c2342b1e0b8ffda1507fbffddf213c4b3c1e819ff6a84b943faabb0ebf2403\""
                        + "]}",
                McLog.class);
        McUninstallFilter ethUninstallFilter = objectMapper.readValue(
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":true}", McUninstallFilter.class);

        when(chain3jService.send(any(Request.class), eq(McFilter.class)))
                .thenReturn(ethFilter);
        when(chain3jService.send(any(Request.class), eq(McLog.class)))
                .thenReturn(ethLog);
        when(chain3jService.send(any(Request.class), eq(McUninstallFilter.class)))
                .thenReturn(ethUninstallFilter);

        Observable<McBlock> observable = chain3j.catchUpToLatestAndSubscribeToNewBlocksObservable(
                new DefaultBlockParameterNumber(BigInteger.ZERO),
                false);

        CountDownLatch transactionLatch = new CountDownLatch(expected.size());
        CountDownLatch completedLatch = new CountDownLatch(1);

        List<McBlock> results = new ArrayList<>(expected.size());
        Subscription subscription = observable.subscribe(
                result -> {
                    results.add(result);
                    transactionLatch.countDown();
                },
                throwable -> fail(throwable.getMessage()),
                () -> completedLatch.countDown());

        transactionLatch.await(1250, TimeUnit.MILLISECONDS);
        assertThat(results, equalTo(expected));

        subscription.unsubscribe();

        completedLatch.await(1, TimeUnit.SECONDS);
        assertTrue(subscription.isUnsubscribed());
    }

    private McBlock createBlock(int number) {
        McBlock ethBlock = new McBlock();
        McBlock.Block block = new McBlock.Block();
        block.setNumber(Numeric.encodeQuantity(BigInteger.valueOf(number)));

        ethBlock.setResult(block);
        return ethBlock;
    }
}
