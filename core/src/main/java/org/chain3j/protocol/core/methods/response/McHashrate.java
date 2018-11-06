package org.chain3j.protocol.core.methods.response;

import java.math.BigInteger;

import org.chain3j.protocol.core.Response;
import org.chain3j.utils.Numeric;

/**
 * mc_hashrate.
 */
public class McHashrate extends Response<String> {
    public BigInteger getHashrate() {
        return Numeric.decodeQuantity(getResult());
    }
}
