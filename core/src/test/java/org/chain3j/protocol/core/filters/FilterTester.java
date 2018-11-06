package org.chain3j.protocol.core.filters;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import rx.Observable;
import rx.Subscription;

import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.Chain3jService;
import org.chain3j.protocol.ObjectMapperFactory;
import org.chain3j.protocol.core.Request;
import org.chain3j.protocol.core.methods.response.McFilter;
import org.chain3j.protocol.core.methods.response.McLog;
import org.chain3j.protocol.core.methods.response.McUninstallFilter;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public abstract class FilterTester {

    private Chain3jService chain3jService;
    Chain3j chain3j;

    final ObjectMapper objectMapper = ObjectMapperFactory.getObjectMapper();
    final ScheduledExecutorService scheduledExecutorService =
            Executors.newSingleThreadScheduledExecutor();

    @Before
    public void setUp() {
        chain3jService = mock(Chain3jService.class);
        chain3j = Chain3j.build(chain3jService, 1000, scheduledExecutorService);
    }

    <T> void runTest(McLog ethLog, Observable<T> observable) throws Exception {
        McFilter ethFilter = objectMapper.readValue(
                "{\n"
                        + "  \"id\":1,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": \"0x1\"\n"
                        + "}", McFilter.class);

        McUninstallFilter ethUninstallFilter = objectMapper.readValue(
                "{\"jsonrpc\":\"2.0\",\"id\":1,\"result\":true}", McUninstallFilter.class);

        McLog notFoundFilter = objectMapper.readValue(
                "{\"jsonrpc\":\"2.0\",\"id\":1,"
                + "\"error\":{\"code\":-32000,\"message\":\"filter not found\"}}",
                McLog.class);

        @SuppressWarnings("unchecked")
        List<T> expected = createExpected(ethLog);
        Set<T> results = Collections.synchronizedSet(new HashSet<T>());

        CountDownLatch transactionLatch = new CountDownLatch(expected.size());

        CountDownLatch completedLatch = new CountDownLatch(1);

        when(chain3jService.send(any(Request.class), eq(McFilter.class)))
                .thenReturn(ethFilter);
        when(chain3jService.send(any(Request.class), eq(McLog.class)))
            .thenReturn(ethLog).thenReturn(notFoundFilter).thenReturn(ethLog);
        when(chain3jService.send(any(Request.class), eq(McUninstallFilter.class)))
                .thenReturn(ethUninstallFilter);

        Subscription subscription = observable.subscribe(
                result -> {
                    results.add(result);
                    transactionLatch.countDown();
                },
                throwable -> fail(throwable.getMessage()),
                () -> completedLatch.countDown());

        transactionLatch.await(1, TimeUnit.SECONDS);
        assertThat(results, equalTo(new HashSet<>(expected)));

        subscription.unsubscribe();

        completedLatch.await(1, TimeUnit.SECONDS);
        assertTrue(subscription.isUnsubscribed());
    }

    List createExpected(McLog ethLog) {
        List<McLog.LogResult> logResults = ethLog.getLogs();
        if (logResults.isEmpty()) {
            fail("Results cannot be empty");
        }

        return ethLog.getLogs().stream()
                .map(t -> t.get()).collect(Collectors.toList());
    }
}
