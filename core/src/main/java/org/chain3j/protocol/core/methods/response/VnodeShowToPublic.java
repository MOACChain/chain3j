package org.chain3j.protocol.core.methods.response;

import org.chain3j.protocol.core.Response;

/**
 * vnode_showToPublic.
 * return if the vnode address can be seen
 * to the public.
 */
public class VnodeShowToPublic extends Response<Boolean> {
    public boolean getShowToPublic() {
        return getResult();
    }
}
