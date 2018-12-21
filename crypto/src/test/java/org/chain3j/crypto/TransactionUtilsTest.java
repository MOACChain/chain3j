package org.chain3j.crypto;

import org.junit.Test;

import org.chain3j.utils.Numeric;

import static org.chain3j.crypto.TransactionEncoder.encode;
import static org.chain3j.crypto.TransactionUtils.generateTransactionHashHexEncoded;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TransactionUtilsTest {

    // test the encode of a RawTransaction and signing
    // This need to work with the
    @Test
    public void testGenerateTransactionRLP() throws CipherException {
        RawTransaction rt = TransactionEncoderTest.createEip155RawTransaction();
        byte[] encodedTransaction = encode(rt, (byte)100);
        
        String hextx = Numeric.toHexString(encodedTransaction);
        assertThat(hextx, is("0xf84011808504a817c8008203e894d814f2ac2c4"
                + "ca49b33066582e4e97ebae02f2ab985e8d4a510008080"
                + "940000000000000000000000000000000000000000648080"));
                
        assertThat(Hash.sha3(hextx),
                is("0xd10e88a4fad7c26f71f80b626ce5e4218b587c057ca6c9ebd7f33e3db4c9331f"));
    }

    @Test
    public void testGenerateTransactionHash() throws CipherException {
        assertThat(generateTransactionHashHexEncoded(
                TransactionEncoderTest.createEip155RawTransaction(), 100, SampleKeys.CREDENTIALS),
                is("0xc7b41528bb64d4109391a5be65d82ca26876a3cac45aa77512ffc3098f323141"));
    }

    @Test
    public void testGenerateEip155TransactionHash() throws CipherException {
        assertThat(generateTransactionHashHexEncoded(
                TransactionEncoderTest.createContractTransaction(), 100,
                SampleKeys.CREDENTIALS),
                is("0x1b3b22c4799e82d7d73469ff898191dd9ea6aa135ce54523f4ec23c0ffb6c132"));
    }
}
