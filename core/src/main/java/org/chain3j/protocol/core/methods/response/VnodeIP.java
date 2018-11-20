package org.chain3j.protocol.core.methods.response;

import org.chain3j.protocol.core.Response;

/**
 * vnode_IP.
 */
public class VnodeIP extends Response<String> {
    public String getIP() {
        return getResult();
    }
}
