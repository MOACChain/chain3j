package org.chain3j.crypto;

import java.math.BigInteger;
import java.security.SignatureException;

public class SignedRawTransaction extends RawTransaction {

    private static final int CHAIN_ID_INC = 35;
    private static final int LOWER_REAL_V = 27;

    private Sign.SignatureData signatureData;

    public SignedRawTransaction(BigInteger nonce, BigInteger gasPrice,
            BigInteger gasLimit, String to, BigInteger value, String data,
            Integer shardingFlag,
            String via,
            Sign.SignatureData signatureData) {
        //Check
        super(nonce, gasPrice, gasLimit, to, value, data, shardingFlag, via);
        this.signatureData = signatureData;
    }

    public Sign.SignatureData getSignatureData() {
        return signatureData;
    }

    public String getFrom() throws SignatureException {
        Integer chainId = getChainId();
        byte[] encodedTransaction;
        if (null == chainId) {
            encodedTransaction = TransactionEncoder.encode(this);
        } else {
            encodedTransaction = TransactionEncoder.encode(this, chainId.byteValue());
        }
        byte v = signatureData.getV();
        byte[] r = signatureData.getR();
        byte[] s = signatureData.getS();
        Sign.SignatureData signatureDataV = new Sign.SignatureData(getRealV(v), r, s);
        BigInteger key = Sign.signedMessageToKey(encodedTransaction, signatureDataV);
        return "0x" + Keys.getAddress(key);
    }

    public void verify(String from) throws SignatureException {
        String actualFrom = getFrom();
        if (!actualFrom.equals(from)) {
            throw new SignatureException("from mismatch");
        }
    }


    private byte getRealV(byte v) {
        if (v == LOWER_REAL_V || v == (LOWER_REAL_V + 1)) {
            return v;
        }
        byte realV = LOWER_REAL_V;
        int inc = 0;
        if ((int) v % 2 == 0) {
            inc = 1;
        }
        return (byte) (realV + inc);
    }

    // Note, byte range from -128 - +127
    // should convert negative v to positive by add 255
    public Integer getChainId() {
        byte v = signatureData.getV();
        if (v == LOWER_REAL_V || v == (LOWER_REAL_V + 1)) {
            return null;
        }

        //chainId should be larger than 0
        Integer chainId;
        if (v > 0) {
            chainId = (v - CHAIN_ID_INC) / 2;
        } else {
            chainId = ((int)v + 255 - CHAIN_ID_INC) / 2;
        }

        return chainId;
    }
}
