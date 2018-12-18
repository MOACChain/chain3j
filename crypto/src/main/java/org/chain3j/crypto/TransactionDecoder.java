package org.chain3j.crypto;

import java.math.BigInteger;

import org.chain3j.rlp.RlpDecoder;
import org.chain3j.rlp.RlpList;
import org.chain3j.rlp.RlpString;
import org.chain3j.utils.Numeric;

/*
    The transaction should have the following fields:
    type txdata struct {
    AccountNonce uint64          `json:"nonce"    gencodec:"required"`
    Price        *big.Int        `json:"gasPrice" gencodec:"required"`
    GasLimit     *big.Int        `json:"gas"      gencodec:"required"`
        // nil means contract creation
    Amount       *big.Int        `json:"value"    gencodec:"required"`
    Payload      []byte          `json:"input"    gencodec:"required"`
    SystemContract uint64          `json:"syscnt" gencodec:"required"`
    ShardingFlag uint64 `json:"shardingFlag" gencodec:"required"`
    Via            *common.Address `json:"to"       rlp:"nil"`

    // Signature values
    V *big.Int `json:"v" gencodec:"required"`
    R *big.Int `json:"r" gencodec:"required"`
    S *big.Int `json:"s" gencodec:"required"`
    }

*/
public class TransactionDecoder {

    public static RawTransaction decode(String hexTransaction) throws CipherException {
        byte[] transaction = Numeric.hexStringToByteArray(hexTransaction);
        RlpList rlpList = RlpDecoder.decode(transaction);

        //Extract the values from the list
        RlpList values = (RlpList) rlpList.getValues().get(0);
        BigInteger nonce = ((RlpString) values.getValues().get(0)).asPositiveBigInteger();
        String systemFlag = ((RlpString) values.getValues().get(1)).asString();
        BigInteger gasPrice = ((RlpString) values.getValues().get(2)).asPositiveBigInteger();
        BigInteger gasLimit = ((RlpString) values.getValues().get(3)).asPositiveBigInteger();
        String to = ((RlpString) values.getValues().get(4)).asString();
        BigInteger value = ((RlpString) values.getValues().get(5)).asPositiveBigInteger();
        String data = ((RlpString) values.getValues().get(6)).asString();
        String shardingFlag = ((RlpString) values.getValues().get(7)).asString();
        String via = ((RlpString) values.getValues().get(8)).asString();

        // With no signature data, the size of values is 9
        if (values.getValues().size() > 9) {
            // Extract the chainId
            byte v = ((RlpString) values.getValues().get(9)).getBytes()[0];

            byte[] r = Numeric.toBytesPadded(
                Numeric.toBigInt(((RlpString) values.getValues().get(10)).getBytes()), 32);
            byte[] s = Numeric.toBytesPadded(
                Numeric.toBigInt(((RlpString) values.getValues().get(11)).getBytes()), 32);
            Sign.SignatureData signatureData = new Sign.SignatureData(v, r, s);

            try {
                if (shardingFlag.equals("0x")) {
                    return new SignedRawTransaction(nonce, gasPrice, gasLimit,
                            to, value, data, 0, via, signatureData);
                } else {
                    Integer sFlag = Numeric.toBigInt(shardingFlag).intValue();//Integer.valueOf(shardingFlag);
                    if (sFlag >= 0) {
                        return new SignedRawTransaction(nonce, gasPrice, gasLimit,
                                to, value, data, sFlag, via, signatureData);
                    } else {
                        throw new CipherException("TransactionDecode: shardingFlag less than 0");
                    }
                }



            } catch (NumberFormatException e) {
                throw new CipherException("TransactionDecoder: shardingFlag is not a valid Integer");
            }

        } else {
             // Return a decoded
            try {
                if (shardingFlag.equals("0x")) {
                    return RawTransaction.createTransaction(nonce,
                            gasPrice, gasLimit, to, value, data, 0, via);
                } else {
                    Integer sFlag = Numeric.toBigInt(shardingFlag).intValue();//Integer.valueOf(shardingFlag);
                    if (sFlag >= 0) {
                        return RawTransaction.createTransaction(nonce,
                                gasPrice, gasLimit, to, value, data, sFlag, via);
                    } else {
                        throw new CipherException("TransactionDecode: shardingFlag less than 0");
                    }
                }

            } catch (NumberFormatException e) {
                throw new CipherException("TransactionDecoder: shardingFlag is not a valid Integer");
            }

        }
    }
    
}
