package org.chain3j.protocol.scs.methods.response;

import org.chain3j.protocol.core.Response;

/**
 * ScsGetTransaction return a Transaction
 * response type.
 */
public class ScsGetTransaction extends Response<String> {
    public String getTransaction() {
        return getResult();
    }
}
