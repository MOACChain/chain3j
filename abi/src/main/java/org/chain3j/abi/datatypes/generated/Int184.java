package org.chain3j.abi.datatypes.generated;

import java.math.BigInteger;
import org.chain3j.abi.datatypes.Int;

/**
 * Auto generated code.
 * <p><strong>Do not modifiy!</strong>
 * <p>Please use org.chain3j.codegen.AbiTypesGenerator in the 
 * <a href="https://github.com/chain3j/chain3j/tree/master/codegen">codegen module</a> to update.
 */
public class Int184 extends Int {
    public static final Int184 DEFAULT = new Int184(BigInteger.ZERO);

    public Int184(BigInteger value) {
        super(184, value);
    }

    public Int184(long value) {
        this(BigInteger.valueOf(value));
    }
}
