package org.chain3j.protocol.core.methods.response;

import java.math.BigInteger;

import org.chain3j.protocol.core.Response;
import org.chain3j.utils.Numeric;

/**
 * mc_gasPrice.
 */
public class McGasPrice extends Response<String> {
    public BigInteger getGasPrice() {
        return Numeric.decodeQuantity(getResult());
    }
}
