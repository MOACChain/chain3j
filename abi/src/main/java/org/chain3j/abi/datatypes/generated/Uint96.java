package org.chain3j.abi.datatypes.generated;

import java.math.BigInteger;
import org.chain3j.abi.datatypes.Uint;

/**
 * Auto generated code.
 * <p><strong>Do not modifiy!</strong>
 * <p>Please use org.chain3j.codegen.AbiTypesGenerator in the 
 * <a href="https://github.com/chain3j/chain3j/tree/master/codegen">codegen module</a> to update.
 */
public class Uint96 extends Uint {
    public static final Uint96 DEFAULT = new Uint96(BigInteger.ZERO);

    public Uint96(BigInteger value) {
        super(96, value);
    }

    public Uint96(long value) {
        this(BigInteger.valueOf(value));
    }
}
