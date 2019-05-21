package org.chain3j.protocol.core.methods.response;

import org.chain3j.protocol.core.Response;

/**
 * mc_call.
 */
public class McCall extends Response<String> {
    public String blockList() {
        return getResult();
    }
}
