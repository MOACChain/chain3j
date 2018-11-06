package org.chain3j.protocol.core.methods.response;

import org.chain3j.protocol.core.Response;

public class McSubscribe extends Response<String> {
    public String getSubscriptionId() {
        return getResult();
    }
}
