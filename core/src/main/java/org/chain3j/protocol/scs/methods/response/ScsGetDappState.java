package org.chain3j.protocol.scs.methods.response;

import org.chain3j.protocol.core.Response;

/**
 * Boolean response type.
 */
public class ScsGetDappState extends Response<Boolean> {
    public boolean getDappState() {
        return getResult();
    }
}
