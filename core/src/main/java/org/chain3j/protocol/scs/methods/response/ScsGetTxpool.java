package org.chain3j.protocol.scs.methods.response;

import org.chain3j.protocol.core.Response;

public class ScsGetTxpool extends Response<ScsGetTxpool> {

    public static class Result {
        private Object pending;
        private Object queued;
    }
}
