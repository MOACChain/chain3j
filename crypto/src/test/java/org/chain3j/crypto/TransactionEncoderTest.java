package org.chain3j.crypto;

import java.math.BigInteger;
import java.util.List;

import org.junit.Test;

import org.chain3j.rlp.RlpString;
import org.chain3j.rlp.RlpType;
import org.chain3j.utils.Numeric;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

// The test requires chainId input
public class TransactionEncoderTest {

    public static final Integer chainId = 100;
    
    @Test
    public void testSignMessage() {

        try {
            byte[] signedMessage = TransactionEncoder.signMessage(
                createEip155RawTransaction(), chainId, SampleKeys.CREDENTIALS);
            String hexMessage = Numeric.toHexString(signedMessage);
//            assertThat(hexMessage,
//                        is("0xf86d02808504a817c800825208947312f4b8a4457a36827f185325fd6b66a3f8bb8b"
//                                + "85e8d4a51000808080"
//                                + "81eba06e2dfaf7f433f9ebf64467cf39f51bf4868d1a9df9eeea59e7d0e3adfa9e"
//                                + "071ea04bb6d82b980c07ab5a3199392d1cf24d520ebcb4901dc4fefcd18b7464cbc39f"));
        } catch (CipherException ie) {
            ie.printStackTrace();
        } 


    }

    @Test
    public void testMcTransactionAsRlpValues() {
        List<RlpType> rlpStrings = TransactionEncoder.asRlpValues(createMcTransaction(),
                new Sign.SignatureData((byte) 100, new byte[32], new byte[32]));
        assertThat(rlpStrings.size(), is(12));
        assertThat(rlpStrings.get(4), equalTo(RlpString.create(new BigInteger("add5355", 16))));
    }

    @Test
    public void testContractAsRlpValues() {
        List<RlpType> rlpStrings = TransactionEncoder.asRlpValues(
                createContractTransaction(), null);
        assertThat(rlpStrings.size(), is(9));
        // 4th position is to
        assertThat(rlpStrings.get(4), is(RlpString.create("")));
    }

    @Test
    public void testEip155Encode() {
        byte[] encodedTransaction = TransactionEncoder.encode(createEip155RawTransaction(), (byte)100);
//        assertThat(Numeric.toHexString(encodedTransaction),
//                is("0xec02808504a817c80082520894"
//                    + "7312f4b8a4457a36827f185325fd6b66a3f8bb8b85e8d4a51000808080648080"));
    }

    // All 
    // @Test
    // public void testEip155Transaction(Integer chainId) {
    //     // https://github.com/ethereum/EIPs/issues/155
    //     Credentials credentials = Credentials.create(
    //             "a392604efc2fad9c0b3da43b5f698a2e3f270f170d859912be0d54742275c5f6");
    //     try {
    //         assertThat(TransactionEncoder.signMessage(
    //                     createEip155RawTransaction(), chainId, credentials),
    //                     is(Numeric.hexStringToByteArray(
    //                     "0xf86c098504a817c800825208943535353535353535353535353535353535353535880"
    //                             + "de0b6b3a76400008025a028ef61340bd939bc2195fe537567866003e1a15d"
    //                             + "3c71ff63e1590620aa636276a067cbe9d8997f761aecb703304b3800ccf55"
    //                             + "5c9f3dc64214b297fb1966a3b6d83")));
    //     } catch (CipherException ie) {
    //         ie.printStackTrace();
    //     } 

    // }

    private static RawTransaction createMcTransaction() {
        return RawTransaction.createMcTransaction(
                BigInteger.ZERO, BigInteger.ONE, BigInteger.TEN, 
                "0xadd5355",
                BigInteger.valueOf(Long.MAX_VALUE));
    }

    static RawTransaction createContractTransaction() {
        return RawTransaction.createContractTransaction(
                BigInteger.ZERO, BigInteger.ONE, BigInteger.TEN, BigInteger.valueOf(Long.MAX_VALUE),
                "01234566789");
    }

    static RawTransaction createEip155RawTransaction() {
        return RawTransaction.createMcTransaction(
                BigInteger.valueOf(2), BigInteger.valueOf(20000000000L),
                BigInteger.valueOf(21000), "0x7312f4b8a4457a36827f185325fd6b66a3f8bb8b",
                BigInteger.valueOf(1000000000000L));
    }
}
