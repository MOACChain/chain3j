package org.chain3j.protocol.scs.methods.response;

import java.math.BigInteger;

import org.chain3j.protocol.core.Response;
import org.chain3j.utils.Numeric;

/**
 * Return the block number of the MicroChain.
 */
public class ScsGetBlockNumber extends Response<String> {
    public BigInteger getBlockNumber() {
        return Numeric.decodeQuantity(getResult());
    }

    public String getBlockNumberRaw(){
        return getResult();
    }
}
