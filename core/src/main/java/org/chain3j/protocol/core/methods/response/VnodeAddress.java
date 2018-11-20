package org.chain3j.protocol.core.methods.response;

import org.chain3j.protocol.core.Response;

/**
 * vnode_address.
 * return the vnode proxy address for 
 * SCS to connect with
 */
public class VnodeAddress extends Response<String> {
    public String getAddress() {
        return getResult();
    }
}
