package org.chain3j.protocol;

import java.util.concurrent.ScheduledExecutorService;

import org.chain3j.protocol.core.JsonRpc2_0Chain3j;
import org.chain3j.protocol.core.Moac;
import org.chain3j.protocol.rx.Chain3jRx;

/**
 * JSON-RPC Request object building factory.
 * Add SCS service in addition to MOAC VNODE
 */
public interface Chain3j extends Moac, Chain3jRx {

    /**
     * Construct a new chain3j instance.
     *
     * @param chain3jService chain3j service instance - i.e. HTTP or IPC
     * @return new chain3j instance
     */
    static Chain3j build(Chain3jService chain3jService) {
        return new JsonRpc2_0Chain3j(chain3jService);
    }

    /**
     * Construct a new chain3j instance.
     *
     * @param chain3jService chain3j service instance - i.e. HTTP or IPC
     * @param pollingInterval polling interval for responses from network nodes
     * @param scheduledExecutorService executor service to use for scheduled tasks.
     *                                 <strong>You are responsible for terminating this thread
     *                                 pool</strong>
     * @return new chain3j instance
     */
    static Chain3j build(
            Chain3jService chain3jService, long pollingInterval,
            ScheduledExecutorService scheduledExecutorService) {
        return new JsonRpc2_0Chain3j(chain3jService, pollingInterval, scheduledExecutorService);
    }

    /**
     * Shutdowns a chain3j instance and closes opened resources.
     */
    void shutdown();
}
