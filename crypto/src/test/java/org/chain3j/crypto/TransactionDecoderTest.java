package org.chain3j.crypto;

import java.math.BigInteger;

import org.junit.Test;

import org.chain3j.utils.Numeric;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class TransactionDecoderTest {

    @Test
    public void testDecoding() throws Exception {
        BigInteger nonce = BigInteger.ZERO;
        BigInteger gasPrice = BigInteger.ONE;
        BigInteger gasLimit = BigInteger.TEN;
        String to = "0x0add5355";
        BigInteger value = BigInteger.valueOf(Long.MAX_VALUE);

        RawTransaction rawTransaction = RawTransaction.createMcTransaction(
                nonce, gasPrice, gasLimit, to, value);
        byte[] encodedMessage = TransactionEncoder.encode(rawTransaction);
        String hexMessage = Numeric.toHexString(encodedMessage);

        RawTransaction result = TransactionDecoder.decode(hexMessage);
        assertNotNull(result);
        assertEquals(nonce, result.getNonce());
        assertEquals(gasPrice, result.getGasPrice());
        assertEquals(gasLimit, result.getGasLimit());
        assertEquals(to, result.getTo());
        assertEquals(value, result.getValue());
        assertEquals("", result.getData());
    }

    @Test
    public void testDecodingSigned() throws Exception {
        BigInteger nonce = BigInteger.valueOf(2);//BigInteger.ZERO;
        BigInteger gasPrice = BigInteger.valueOf(20000000000L);//BigInteger.ONE;
        BigInteger gasLimit = BigInteger.valueOf(21000);//BigInteger.TEN;
        String to = "0x7312F4B8A4457a36827f185325Fd6B66a3f8BB8B";
        BigInteger value = BigInteger.valueOf(1000000000000L);//BigInteger.valueOf(Long.MAX_VALUE);
        Integer chainId = 100;
        //Only create the mc transfer
        RawTransaction rawTransaction = RawTransaction.createMcTransaction(
                nonce, gasPrice, gasLimit, to, value);


        byte[] signedMessage = TransactionEncoder.signTxEIP155(
                rawTransaction, chainId, SampleKeys.CREDENTIALS);
        String hexMessage = Numeric.toHexString(signedMessage);

        assertEquals(hexMessage,
                "0xf88102808504a817c800825208947312f4b8a4457a36827f185325fd6b66"
                        + "a3f8bb8b85e8d4a51000808094000000000000000000000000000000000000000081"
                        + "eca0bdeecffc4167652762a3f0cc056d7c29e26d19ed37a5a16e7478d7ab28da775c"
                        + "a0445adeefa1c00d82385493209a9609704f6152560a3439c4c46567bda167b095");

        RawTransaction result = TransactionDecoder.decode(hexMessage);
        assertNotNull(result);
        assertEquals(nonce, result.getNonce());
        assertEquals(gasPrice, result.getGasPrice());
        assertEquals(gasLimit, result.getGasLimit());
        assertEquals(to.toLowerCase(), result.getTo());
        assertEquals(value, result.getValue());
        assertEquals("", result.getData());
        assertTrue(result instanceof SignedRawTransaction);
        SignedRawTransaction signedResult = (SignedRawTransaction) result;
        assertNotNull(signedResult.getSignatureData());

        // For EIP155, need to encode the rawTransaction with chainId
        // and signed with chainId.
        Sign.SignatureData signatureData = signedResult.getSignatureData();
        byte[] encodedTransaction = TransactionEncoder.encode(rawTransaction, chainId.byteValue());

        BigInteger key = Sign.signedEIP155MessageToKey(chainId, encodedTransaction, signatureData);
        assertEquals(key, SampleKeys.PUBLIC_KEY);
        assertEquals(SampleKeys.ADDRESS, signedResult.getFrom());
        signedResult.verify(SampleKeys.ADDRESS);
        assertEquals(signedResult.getChainId(), chainId);
    }

    @Test
    public void testDecodingSignedChainId() throws Exception {
        BigInteger nonce = BigInteger.ZERO;
        BigInteger gasPrice = BigInteger.ONE;
        BigInteger gasLimit = BigInteger.TEN;
        String to = "0x0add5355";
        BigInteger value = BigInteger.valueOf(Long.MAX_VALUE);
        Integer chainId = 101;
        RawTransaction rawTransaction = RawTransaction.createMcTransaction(
                nonce, gasPrice, gasLimit, to, value);
        byte[] signedMessage = TransactionEncoder.signTxEIP155(
                rawTransaction, chainId, SampleKeys.CREDENTIALS);

        String hexMessage = Numeric.toHexString(signedMessage);

        RawTransaction result = TransactionDecoder.decode(hexMessage);
        assertNotNull(result);
        assertEquals(nonce, result.getNonce());
        assertEquals(gasPrice, result.getGasPrice());
        assertEquals(gasLimit, result.getGasLimit());
        assertEquals(to, result.getTo());
        assertEquals(value, result.getValue());
        assertEquals("", result.getData());
        assertTrue(result instanceof SignedRawTransaction);
        SignedRawTransaction signedResult = (SignedRawTransaction) result;
        assertEquals(SampleKeys.ADDRESS, signedResult.getFrom());
        signedResult.verify(SampleKeys.ADDRESS);
        assertEquals(chainId, signedResult.getChainId());
    }

    @Test
    public void testRSize31() throws Exception {
        //CHECKSTYLE:OFF
        String hexTransaction = "0xf86d01808504a817c8008203e8947312f4b8a4457a36827f185325fd6b66a3f8bb8b85e8d4a5100000808081eca03fdbd0b4b2d20a8c7b7f21b2f0238f1990ceae6df3b58cb00ce279a284a2d96fa0205eeefab21cddc6d1d10b050fa568efbf7d46dd6dbc04557ef7c57d67bf5440";
        //CHECKSTYLE:ON
        RawTransaction result = TransactionDecoder.decode(hexTransaction);
        SignedRawTransaction signedResult = (SignedRawTransaction) result;
        
        // Example signed rawTx: 
        // '0x01',
        // '0x',
        // '0x04a817c800',
        // '0x03e8',
        // '0x7312f4b8a4457a36827f185325fd6b66a3f8bb8b',
        // '0xe8d4a51000',
        // '0x00',
        // '0x',
        // '0x',
        // '0xec',
        // '0x3fdbd0b4b2d20a8c7b7f21b2f0238f1990ceae6df3b58cb00ce279a284a2d96f',
        // '0x205eeefab21cddc6d1d10b050fa568efbf7d46dd6dbc04557ef7c57d67bf5440' ]
        assertEquals(Integer.valueOf(100), signedResult.getChainId());
        assertEquals("0x7312f4b8a4457a36827f185325fd6b66a3f8bb8b", signedResult.getTo());
        //Should make it work after signature is set.
        assertEquals("0xef678007d18427e6022059dbc264f27507cd1ffc", signedResult.getFrom());
    }
}
