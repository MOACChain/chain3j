package org.chain3j.protocol.moac.response;

import org.chain3j.protocol.core.Response;

/**
 * personal_ecRecover.
 */
public class PersonalEcRecover extends Response<String> {
    public String getRecoverAccountId() {
        return getResult();
    }
}
