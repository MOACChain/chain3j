package org.chain3j.protocol.core.methods.response;

import org.chain3j.protocol.core.Response;

/**
 * mc_coinbase.
 */
public class McCoinbase extends Response<String> {
    public String getAddress() {
        return getResult();
    }
}
