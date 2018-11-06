package org.chain3j.protocol.core.methods.response;

import java.util.List;

import org.chain3j.protocol.core.Response;

/**
 * mc_accounts.
 */
public class McAccounts extends Response<List<String>> {
    public List<String> getAccounts() {
        return getResult();
    }
}
