package org.chain3j.crypto;

import java.math.BigInteger;
import java.util.Arrays;

import org.junit.Test;

import org.chain3j.crypto.Sign.SignatureData;
import org.chain3j.utils.Numeric;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

public class ECRecoverTest {

    public static final String PERSONAL_MESSAGE_PREFIX = "\u0019MOAC Signed Message:\n";

    @Test
    public void testRecoverAddressFromSignature() {
        //CHECKSTYLE:OFF
        //The signature of signed message
        String signature = "0x17db5523013824f4fd974fca0629ea8dfc8aab51586983a1b777"
            + "4ef187a58010266577e7602673786bedd99e122bd297bbae555015805924904a3769de9048501b";
        //CHECKSTYLE:ON
        //Address of the signed KeyPair
        String address = "0xef678007d18427e6022059dbc264f27507cd1ffc";
        String message = "MOAC test message";

        byte[] msgHash = Hash.sha3((message).getBytes());

        byte[] signatureBytes = Numeric.hexStringToByteArray(signature);
        byte v = signatureBytes[64];
        System.out.println(v);
        if (v < 27) { 
            v += 27; 
        }
           
        SignatureData sd = new SignatureData(
                v, 
                (byte[]) Arrays.copyOfRange(signatureBytes, 0, 32), 
                (byte[]) Arrays.copyOfRange(signatureBytes, 32, 64));

        String addressRecovered = null;
        boolean match = false;
        
        // Iterate for each possible key to recover
        for (int i = 0; i < 4; i++) {
            BigInteger publicKey = Sign.recoverFromSignature(
                    (byte) i, 
                    new ECDSASignature(new BigInteger(1, sd.getR()), new BigInteger(1, sd.getS())), 
                    msgHash);

            if (publicKey != null) {
                addressRecovered = "0x" + Keys.getAddress(publicKey);
                //System.out.println("addressRecovered:"+ addressRecovered);
                if (addressRecovered.equals(address)) {
                    match = true;
                    break;
                }
            }
        }
        
        assertThat(addressRecovered, is(address));
        assertTrue(match);
    }
}
