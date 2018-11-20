package org.chain3j.protocol.core.methods.response;

import org.chain3j.protocol.core.Response;

/**
 * vnode_scsServices.
 * return if the vnode provide any PROXY service for the SCSs
 */
public class VnodeScsService extends Response<Boolean> {
    public boolean getScsService() {
        return getResult();
    }
}
