package org.chain3j.protocol.scenarios;

import java.math.BigInteger;

import org.junit.Test;

import org.chain3j.crypto.Hash;
import org.chain3j.crypto.RawTransaction;
import org.chain3j.crypto.TransactionEncoder;
import org.chain3j.protocol.core.methods.response.McSign;
import org.chain3j.utils.Convert;
import org.chain3j.utils.Numeric;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Sign transaction using Moac node.
 */
public class SignTransactionIT extends Scenario {

    @Test
    public void testSignTransaction() throws Exception {
        boolean accountUnlocked = unlockAccount();
        assertTrue(accountUnlocked);

        RawTransaction rawTransaction = createTransaction();

        byte[] encoded = TransactionEncoder.encode(rawTransaction);
        byte[] hashed = Hash.sha3(encoded);

        McSign mcSign = chain3j.mcSign(ALICE.getAddress(), Numeric.toHexString(hashed))
                .sendAsync().get();

        String signature = mcSign.getSignature();
        assertNotNull(signature);
        assertFalse(signature.isEmpty());
    }

    private static RawTransaction createTransaction() {
        BigInteger value = Convert.toSha("1", Convert.Unit.MC).toBigInteger();

        return RawTransaction.createMcTransaction(
                BigInteger.valueOf(1048587), BigInteger.valueOf(500000), BigInteger.valueOf(500000),
                "0x9C98E381Edc5Fe1Ac514935F3Cc3eDAA764cf004",
                value);
    }
}
