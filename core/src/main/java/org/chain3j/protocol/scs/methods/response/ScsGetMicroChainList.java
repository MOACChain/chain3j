package org.chain3j.protocol.scs.methods.response;

import java.util.List;

import org.chain3j.protocol.core.Response;

/**
 * ScsGetMicroChainList
 * Return the list of the microChains 
 * on the SCS server.
 */
public class ScsGetMicroChainList extends Response<List<String>> {
    public List<String> getMicroChainList() {
        return getResult();
    }    
}
