package org.chain3j.protocol.scs.methods.response;

import java.math.BigInteger;

import org.chain3j.protocol.core.Response;
import org.chain3j.utils.Numeric;

/**
 * structure response type.
 * Return the structure info of the MicroChain.
 */
public class ScsGetMicroChainInfo extends Response<String> {
    public String getMicroChainInfo() {
        return getResult();
    }
}
