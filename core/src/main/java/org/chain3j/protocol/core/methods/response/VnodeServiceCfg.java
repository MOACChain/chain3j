package org.chain3j.protocol.core.methods.response;

import org.chain3j.protocol.core.Response;

/**
 * vnode_serviceCfg.
 * return the VNODE service configuration as string
 * 
 */
public class VnodeServiceCfg extends Response<String> {
    public String getServiceCfg() {
        return getResult();
    }
}
