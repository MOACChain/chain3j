package org.chain3j.crypto;

import org.junit.Test;

import org.chain3j.utils.Numeric;

import static org.chain3j.crypto.TransactionEncoder.encode;
import static org.chain3j.crypto.TransactionUtils.generateTransactionHashHexEncoded;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class TransactionUtilsTest {

    // test the encode of a RawTransaction and signing
    @Test
    public void testGenerateTransactionRLP() throws CipherException {
        RawTransaction rt = TransactionEncoderTest.createEip155RawTransaction();
        byte[] encodedTransaction = encode(rt, (byte)100);
        
        String hextx = Numeric.toHexString(encodedTransaction);
        assertThat(hextx, is("0xec02808504a817c80082520894"
                + "7312f4b8a4457a36827f185325fd6b66a3f8bb8b85e8d4a51000808080648080"));
                
        assertThat(Hash.sha3(hextx),
                is("0xcc414315bf1c13ad34b27db6d487e15b6058ff9e842f4d99a0aacec1a02fc44e"));
    }

    @Test
    public void testGenerateTransactionHash() throws CipherException {
        assertThat(generateTransactionHashHexEncoded(
                TransactionEncoderTest.createEip155RawTransaction(), 100, SampleKeys.CREDENTIALS),
                is("0xab4b4b1878e20bf4cfc32e12c8becbf5356e9ea3232d01fba648dab16947541b"));
    }

    @Test
    public void testGenerateEip155TransactionHash() throws CipherException {
        assertThat(generateTransactionHashHexEncoded(
                TransactionEncoderTest.createContractTransaction(), 100,
                SampleKeys.CREDENTIALS),
                is("0xf47ff93912825ab3df2c2871b66c2e4bcd439c875694d879711d7883cfdd9623"));
        // is("0x568c7f6920c1cee8332e245c473657b9c53044eb96ed7532f5550f1139861e9e"));
    }
}
