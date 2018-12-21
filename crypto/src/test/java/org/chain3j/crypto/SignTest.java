package org.chain3j.crypto;

import java.math.BigInteger;
import java.security.SignatureException;

import org.junit.Test;

import org.chain3j.utils.Numeric;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

public class SignTest {

    private static final byte[] TEST_MESSAGE = "MOAC test message".getBytes();

    @Test
    public void testSignMessage() {
        Sign.SignatureData signatureData = Sign.signMessage(TEST_MESSAGE, SampleKeys.KEY_PAIR);

        //Debug use only
        //String r = Numeric.toHexString(signatureData.getR());
        //String s = Numeric.toHexString(signatureData.getS());
        //System.out.println(r);
        //System.out.println(s);
        Sign.SignatureData expected = new Sign.SignatureData(
                (byte) 28,
                Numeric.hexStringToByteArray(
                        "0x17db5523013824f4fd974fca0629ea8dfc8aab51586983a1b7774ef187a58010"),
                Numeric.hexStringToByteArray(
                        "0x266577e7602673786bedd99e122bd297bbae555015805924904a3769de904850")
        );


        assertThat(signatureData, is(expected));
    }

    @Test
    public void testSignedMessageToKey() throws SignatureException {
        Sign.SignatureData signatureData = Sign.signMessage(TEST_MESSAGE, SampleKeys.KEY_PAIR);
        BigInteger key = Sign.signedMessageToKey(TEST_MESSAGE, signatureData);
        assertThat(key, equalTo(SampleKeys.PUBLIC_KEY));
    }

    @Test
    public void testSignedEIP155MessageToKey() throws SignatureException {
        int chainId = 100;
        Sign.SignatureData signatureData = Sign.signEIP155Message(chainId, TEST_MESSAGE, SampleKeys.KEY_PAIR);
        BigInteger key = Sign.signedEIP155MessageToKey(chainId, TEST_MESSAGE, signatureData);
        assertThat(key, equalTo(SampleKeys.PUBLIC_KEY));
    }

    @Test
    public void testPublicKeyFromPrivateKey() {
        assertThat(Sign.publicKeyFromPrivate(SampleKeys.PRIVATE_KEY),
                equalTo(SampleKeys.PUBLIC_KEY));
    }

    @Test(expected = RuntimeException.class)
    public void testInvalidSignature() throws SignatureException {
        Sign.signedMessageToKey(
                TEST_MESSAGE, new Sign.SignatureData((byte) 27, new byte[]{1}, new byte[]{0}));
    }
}
