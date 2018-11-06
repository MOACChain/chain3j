package org.chain3j.protocol.core.methods.response;

import org.chain3j.protocol.core.Response;

/**
 * mc_mining.
 */
public class McMining extends Response<Boolean> {
    public boolean isMining() {
        return getResult();
    }
}
