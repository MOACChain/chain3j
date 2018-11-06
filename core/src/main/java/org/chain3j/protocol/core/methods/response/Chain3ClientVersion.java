package org.chain3j.protocol.core.methods.response;

import org.chain3j.protocol.core.Response;

/**
 * chain3_clientVersion.
 */
public class Chain3ClientVersion extends Response<String> {

    public String getChain3ClientVersion() {
        return getResult();
    }
}
