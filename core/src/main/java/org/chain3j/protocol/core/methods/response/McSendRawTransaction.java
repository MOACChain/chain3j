package org.chain3j.protocol.core.methods.response;

import org.chain3j.protocol.core.Response;

/**
 * mc_sendRawTransaction.
 */
public class McSendRawTransaction extends Response<String> {
    public String getTransactionHash() {
        return getResult();
    }
}
