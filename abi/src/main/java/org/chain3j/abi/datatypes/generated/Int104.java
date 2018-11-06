package org.chain3j.abi.datatypes.generated;

import java.math.BigInteger;
import org.chain3j.abi.datatypes.Int;

/**
 * Auto generated code.
 * <p><strong>Do not modifiy!</strong>
 * <p>Please use org.chain3j.codegen.AbiTypesGenerator in the 
 * <a href="https://github.com/chain3j/chain3j/tree/master/codegen">codegen module</a> to update.
 */
public class Int104 extends Int {
    public static final Int104 DEFAULT = new Int104(BigInteger.ZERO);

    public Int104(BigInteger value) {
        super(104, value);
    }

    public Int104(long value) {
        this(BigInteger.valueOf(value));
    }
}
