package org.chain3j.protocol.core.methods.response;

import java.math.BigInteger;

import org.chain3j.protocol.core.Response;
import org.chain3j.utils.Numeric;

/**
 * mc_getBalance.
 */
public class McGetBalance extends Response<String> {
    public BigInteger getBalance() {
        return Numeric.decodeQuantity(getResult());
    }
}
