package org.chain3j.protocol.scs.methods.response;

import java.math.BigInteger;

import org.chain3j.protocol.core.Response;
import org.chain3j.utils.Numeric;

/**
 * BigNumber response type.
 * Return the block number of the MicroChain.
 */
public class ScsGetBalance extends Response<String> {
    public BigInteger getBalance() {
        return Numeric.decodeQuantity(getResult());
    }
}
