package org.chain3j.protocol.core;

import java.io.IOException;
import java.util.concurrent.ScheduledExecutorService;

import org.junit.Test;

import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.Chain3jService;

import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class JsonRpc2_0Chain3jTest {

    private ScheduledExecutorService scheduledExecutorService
            = mock(ScheduledExecutorService.class);
    private Chain3jService service = mock(Chain3jService.class);

    private Chain3j chain3j = Chain3j.build(service, 10, scheduledExecutorService);

    @Test
    public void testStopExecutorOnShutdown() throws Exception {
        chain3j.shutdown();

        verify(scheduledExecutorService).shutdown();
        verify(service).close();
    }

    @Test(expected = RuntimeException.class)
    public void testThrowsRuntimeExceptionIfFailedToCloseService() throws Exception {
        doThrow(new IOException("Failed to close"))
                .when(service).close();

        chain3j.shutdown();
    }
}