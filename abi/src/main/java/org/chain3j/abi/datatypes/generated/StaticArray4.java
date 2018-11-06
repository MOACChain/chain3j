package org.chain3j.abi.datatypes.generated;

import java.util.List;
import org.chain3j.abi.datatypes.StaticArray;
import org.chain3j.abi.datatypes.Type;

/**
 * Auto generated code.
 * <p><strong>Do not modifiy!</strong>
 * <p>Please use org.chain3j.codegen.AbiTypesGenerator in the 
 * <a href="https://github.com/chain3j/chain3j/tree/master/codegen">codegen module</a> to update.
 */
public class StaticArray4<T extends Type> extends StaticArray<T> {
    public StaticArray4(List<T> values) {
        super(4, values);
    }

    @SafeVarargs
    public StaticArray4(T... values) {
        super(4, values);
    }
}
