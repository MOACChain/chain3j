package org.chain3j.protocol.core.methods.response;

import java.util.List;

import org.chain3j.protocol.core.Response;

/**
 * mc_getCompilers.
 */
public class McGetCompilers extends Response<List<String>> {
    public List<String> getCompilers() {
        return getResult();
    }
}
