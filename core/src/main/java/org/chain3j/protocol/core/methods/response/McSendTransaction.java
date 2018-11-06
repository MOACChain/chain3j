package org.chain3j.protocol.core.methods.response;

import org.chain3j.protocol.core.Response;

/**
 * mc_sendTransaction.
 */
public class McSendTransaction extends Response<String> {
    public String getTransactionHash() {
        return getResult();
    }
}
