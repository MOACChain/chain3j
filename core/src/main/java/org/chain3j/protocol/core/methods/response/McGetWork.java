package org.chain3j.protocol.core.methods.response;

import java.util.List;

import org.chain3j.protocol.core.Response;

/**
 * mc_getWork.
 */
public class McGetWork extends Response<List<String>> {

    public String getCurrentBlockHeaderPowHash() {
        return getResult().get(0);
    }

    public String getSeedHashForDag() {
        return getResult().get(1);
    }

    public String getBoundaryCondition() {
        return getResult().get(2);
    }
}
