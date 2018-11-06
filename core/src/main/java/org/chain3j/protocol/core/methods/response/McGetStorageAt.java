package org.chain3j.protocol.core.methods.response;

import org.chain3j.protocol.core.Response;

/**
 * mc_getStorageAt.
 */
public class McGetStorageAt extends Response<String> {
    public String getData() {
        return getResult();
    }
}
