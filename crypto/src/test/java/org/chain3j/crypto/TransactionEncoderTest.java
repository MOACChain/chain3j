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

    public static final Integer chainId = 101;
    
    @Test
    public void testSignEIP155Transaction() {

        try {
            byte[] signedTx = TransactionEncoder.signTxEIP155(
                createEip155RawTransaction(), chainId, SampleKeys.CREDENTIALS);
            String hexMessage = Numeric.toHexString(signedTx);
            assertThat(hexMessage,
                        is("0xf88111808504a817c8008203e894d814f2ac2c4ca49b33066582e4e97ebae02f2ab9"
                                + "85e8d4a510008080"
                                + "94000000000000000000000000000000000000000081e"
                                + "ea04e136a288094eca1988f5dfdf66256133b6782140d61448092d390c70c358cc4"
                                + "a0792ee8ba12381c1c998cc408c34fdcfd0f099fb7c63c955b0fa1bb446c38ba09"));
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
        // 4th position is to null
        assertThat(rlpStrings.get(4), is(RlpString.create("")));
    }

    @Test
    public void testEip155Encode() {
        byte[] encodedTransaction = TransactionEncoder.encode(createEip155RawTransaction(), (byte)100);
        assertThat(Numeric.toHexString(encodedTransaction),
                is("0xf84011808504a817c8008203e894d814f2ac2c4ca49b33066582e4e"
                    + "97ebae02f2ab985e8d4a510008080940000000000000000000000000000000000000000648080"));
    }


    @Test
    public void testEip155Transaction() {
        // https://github.com/ethereum/EIPs/issues/155
        Integer chainId = 100;
        Credentials credentials = Credentials.create(
                "c75a5f85ef779dcf95c651612efb3c3b9a6dfafb1bb5375905454d9fc8be8a6b");

        try {
            assertThat(TransactionEncoder.signTxEIP155(
                    createEip155RawTransaction(), chainId, credentials),
                    is(Numeric.hexStringToByteArray(
                            "0xf88111808504a817c8008203e894d814f2ac2c4ca49b33066"
                             + "582e4e97ebae02f2ab985e8d4a5100080809400000000000"
                             + "0000000000000000000000000000081eba0a2e181eb2a4cf"
                             + "ab7c2e057ce4715b517293f3902256fb56b4b7d8c36dab95"
                             + "a4fa02e39e861f97c7a579bd14d4f5152ba2d35f3f948df3"
                             + "3fe527e8a16d866ec2bcd")));
        } catch (CipherException ie) {
            ie.printStackTrace();
        }

    }

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
                BigInteger.valueOf(17), BigInteger.valueOf(20000000000L),
                BigInteger.valueOf(1000), "0xD814F2ac2c4cA49b33066582E4e97EBae02F2aB9",
                BigInteger.valueOf(1000000000000L));
    }
}
