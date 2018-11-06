package org.chain3j.protocol.core.methods.response;

import java.math.BigInteger;

import org.chain3j.protocol.core.Response;
import org.chain3j.utils.Numeric;

/**
 * mc_getUncleCountByBlockNumber.
 */
public class McGetUncleCountByBlockNumber extends Response<String> {
    public BigInteger getUncleCount() {
        return Numeric.decodeQuantity(getResult());
    }
}
