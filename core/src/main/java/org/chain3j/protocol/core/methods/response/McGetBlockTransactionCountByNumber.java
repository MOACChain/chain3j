package org.chain3j.protocol.core.methods.response;

import java.math.BigInteger;

import org.chain3j.protocol.core.Response;
import org.chain3j.utils.Numeric;

/**
 * mc_getBlockTransactionCountByNumber.
 */
public class McGetBlockTransactionCountByNumber extends Response<String> {
    public BigInteger getTransactionCount() {
        return Numeric.decodeQuantity(getResult());
    }
}
