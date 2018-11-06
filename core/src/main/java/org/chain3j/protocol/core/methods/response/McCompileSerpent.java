package org.chain3j.protocol.core.methods.response;

import org.chain3j.protocol.core.Response;

/**
 * mc_compileSerpent.
 */
public class McCompileSerpent extends Response<String> {
    public String getCompiledSourceCode() {
        return getResult();
    }
}
