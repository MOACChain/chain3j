package org.chain3j.crypto;

import java.util.ArrayList;
import java.util.List;

import org.chain3j.crypto.Credentials;
import org.chain3j.crypto.Sign;
import org.chain3j.rlp.RlpDecoder;
import org.chain3j.rlp.RlpEncoder;
import org.chain3j.rlp.RlpList;
import org.chain3j.rlp.RlpString;
import org.chain3j.rlp.RlpType;
import org.chain3j.utils.Bytes;
import org.chain3j.utils.Numeric;

// import org.chain3j.crypto.Hash;
/**
 * Create RLP encoded transaction, implementation as per p4 of the
 * <a href="http://gavwood.com/paper.pdf">yellow paper</a>.
 * Added the fields to RLP with the following sequences
 * Bytes.fromNat(transaction.nonce),
 * Bytes.fromNat(transaction.systemContract),
 * Bytes.fromNat(transaction.gasPrice),
 * Bytes.fromNat(transaction.gasLimit),
 * transaction.to.toLowerCase(),
 * Bytes.fromNat(transaction.value),
 * transaction.data,
 * Bytes.fromNat(transaction.shardingFlag),
 * transaction.via.toLowerCase(),
 * Bytes.fromNat(transaction.chainId),
 * "0x",
 * "0x"]);
 * signMessage apply 
 * signTransaction 
 */
public class TransactionEncoder {

    // All MOAC RawTransaction requires the chainId in the signature

    // Create Eip155 Signature by adding an empty signature
    // in the rawTransaction and do the HASH.
    // Input: Transaction with info with shardingFlag, systemFlag and via
    //
    // 1. RLP encoded the transaction with chainID as signature V field, empty R and S;
    // 2. Used keccak256 HASH the RLP encoded transaction;
    // 3. Signed the hash with private key and generated the new signature;
    // 4. Replaced the signature fields with new signature V, R and S;
    // 5. RLP encoded the transaction with signature.

    public static byte[] signTxEIP155(
            RawTransaction rawTransaction, Integer chainId, Credentials credentials) 
            throws CipherException {

        //Check if the input chainId is the same as the WalletDemo, if not, return error
        if (chainId <= 0) {
            throw new CipherException("Invalid chainId in the rawTransaction!");
        }

        byte id = chainId.byteValue();

        // Add chainId in the transaction object
        byte[] encodedTransaction = encode(rawTransaction, id);

        //The SignatureData process will hash the input encodedTransaction
        // and sign with private key
        // Note by default need to use the hash before sign
        Sign.SignatureData signatureData = Sign.signMessage(
                encodedTransaction, credentials.getEcKeyPair(), true);

        //int encodeV = signatureData.getV() + 8 + id * 2;
        byte v = (byte) (signatureData.getV() + (id << 1) + 8);
        Sign.SignatureData sdata = new Sign.SignatureData(
                v, signatureData.getR(), signatureData.getS());

        RlpList decode = (RlpList) RlpDecoder.decode(encodedTransaction).getValues().get(0);

        List<RlpType> list = decode.getValues().subList(0, 9);
        list.add(9, RlpString.create(sdata.getV()));
        list.add(10, RlpString.create(Bytes.trimLeadingZeroes(sdata.getR())));
        list.add(11, RlpString.create(Bytes.trimLeadingZeroes(sdata.getS())));

        RlpList rlpList = new RlpList(list);

        // byte [] result = RlpEncoder.encode(rlpList);
        // String hexRes = Numeric.toHexString(result);

        return RlpEncoder.encode(rlpList);
    }

    // Add chainId in the signature
    public static Sign.SignatureData createEip155SignatureData(
            Sign.SignatureData signatureData, byte chainId) {
        byte v = (byte) (signatureData.getV() + (chainId << 1) + 8);

        return new Sign.SignatureData(
                v, signatureData.getR(), signatureData.getS());
    }



    public static byte[] signMessage(
            RawTransaction rawTransaction, Integer chainId, Credentials credentials) throws CipherException {
   
        //Check if the input chainId is the same as the WalletDemo, if not, return error
        if (chainId <= 0) {
            throw new CipherException("Invalid chainId in the rawTransaction!");
        }
        return signMessage(rawTransaction, chainId.byteValue(), credentials);
    }

    public static byte[] signMessage(RawTransaction rawTransaction, 
            byte chainId, Credentials credentials) {

        byte[] encodedTransaction = encode(rawTransaction);

        //The SignatureData process will hash the input encodedTransaction
        // and sign with private key
        Sign.SignatureData signatureData = Sign.signMessage(
                encodedTransaction, credentials.getEcKeyPair());
        // Note, if the input is true, more hash will be done
        // The following 
        // byte[] hashtrans = Hash.sha3(encodedTransaction);
        // Sign.SignatureData signatureData2 = Sign.signMessage(
        //         hashtrans, credentials.getEcKeyPair(), false);

        // assertEquals(signatureData,signatureData2);

        byte id = chainId;
        //int encodeV = signatureData.getV() + 8 + id * 2;
        byte v = (byte) (signatureData.getV() + (id << 1) + 8);
        Sign.SignatureData sdata = new Sign.SignatureData(
                v, signatureData.getR(), signatureData.getS());

        RlpList decode = (RlpList) RlpDecoder.decode(encodedTransaction).getValues().get(0);

        List<RlpType> list = decode.getValues().subList(0, 9);
        list.add(9, RlpString.create(sdata.getV()));
        list.add(10, RlpString.create(Bytes.trimLeadingZeroes(sdata.getR())));
        list.add(11, RlpString.create(Bytes.trimLeadingZeroes(sdata.getS())));

        RlpList rlpList = new RlpList(list);

        // byte [] result = RlpEncoder.encode(rlpList);
        // String hexRes = Numeric.toHexString(result);

        return RlpEncoder.encode(rlpList);
    }

    // private static byte[] encode(RawTransaction rawTransaction) {
    //     List<RlpType> values = asRlpValues(rawTransaction);
    //     RlpList rlpList = new RlpList(values);
    //     return RlpEncoder.encode(rlpList);
    // }
    private static byte[] encode(RawTransaction rawTransaction, Sign.SignatureData signatureData) {
        List<RlpType> values = asRlpValues(rawTransaction, signatureData);
        RlpList rlpList = new RlpList(values);
        return RlpEncoder.encode(rlpList);
    }

    //Encode without signature, should not use to send 
    public static byte[] encode(RawTransaction rawTransaction) {
        return encode(rawTransaction, null);
    }

    //Encode with signature 
    public static byte[] encode(RawTransaction rawTransaction, byte chainId) {
        Sign.SignatureData signatureData = new Sign.SignatureData(
                chainId, new byte[] {}, new byte[] {});
        return encode(rawTransaction, signatureData);
    }

    // Convert the raw TX with signature to RLP format
    // nonce， 
    // systemContract， 
    // gasPrice),
    // gasLimit),
    // * transaction.to.toLowerCase(),
    // value, amount of MOAC in the TX
    // data,
    // shardingFlag,
    // via,
    static List<RlpType> asRlpValues(
            RawTransaction rawTransaction, Sign.SignatureData signatureData) {
        List<RlpType> result = new ArrayList<>();

        result.add(RlpString.create(rawTransaction.getNonce()));
        result.add(RlpString.create(rawTransaction.getSystemFlag()));//system flag, always 0
        result.add(RlpString.create(rawTransaction.getGasPrice()));
        result.add(RlpString.create(rawTransaction.getGasLimit()));

        // an empty to address (contract creation) should not be encoded as a numeric 0 value
        String to = rawTransaction.getTo();
        if (to != null && to.length() > 0) {
            // addresses that start with zeros should be encoded with the zeros included, not
            // as numeric values
            result.add(RlpString.create(Numeric.hexStringToByteArray(to)));
        } else {
            result.add(RlpString.create(""));
        }

        result.add(RlpString.create(rawTransaction.getValue()));

        // value field will already be hex encoded, so we need to convert into binary first
        byte[] data = Numeric.hexStringToByteArray(rawTransaction.getData());
        result.add(RlpString.create(data));

        // Get shardingFlag and Via
        result.add(RlpString.create(rawTransaction.getShardingFlag()));

        String viaAddress = rawTransaction.getVia();
        if (viaAddress != null && viaAddress.length() > 0) {
            // addresses that start with zeros should be encoded with the zeros included, not
            // as numeric values
            result.add(RlpString.create(Numeric.hexStringToByteArray(viaAddress)));
        } else {
            result.add(RlpString.create(""));
        }

        // Add signature data
        if (signatureData != null) {
            result.add(RlpString.create(signatureData.getV()));
            result.add(RlpString.create(Bytes.trimLeadingZeroes(signatureData.getR())));
            result.add(RlpString.create(Bytes.trimLeadingZeroes(signatureData.getS())));
        }

        return result;
    }

    // Convert the raw TX only to RLP format
    static List<RlpType> asRlpValues(RawTransaction rawTransaction) {
        List<RlpType> result = new ArrayList<RlpType>();

        result.add(RlpString.create(rawTransaction.getNonce()));
        result.add(RlpString.create(""));//result.add(RlpString.create(rawTransaction.getSystemContract()));
        result.add(RlpString.create(rawTransaction.getGasPrice()));
        result.add(RlpString.create(rawTransaction.getGasLimit()));

        // an empty to address (contract creation) should not be encoded as a numeric 0 value
        String to = rawTransaction.getTo();
        if (to != null && to.length() > 0) {
            // addresses that start with zeros should be encoded with the zeros included, not
            // as numeric values
            result.add(RlpString.create(Numeric.hexStringToByteArray(to)));
        } else {
            result.add(RlpString.create(""));
        }

        result.add(RlpString.create(rawTransaction.getValue()));

        // value field will already be hex encoded, so we need to convert into binary first
        byte[] data = Numeric.hexStringToByteArray(rawTransaction.getData());
        result.add(RlpString.create(data));

        result.add(RlpString.create(0));//result.add(RlpString.create(rawTransaction.getShardingFlag()));
        result.add(RlpString.create(Numeric.hexStringToByteArray("")));
        
        //result.add(RlpString.create(rawTransaction.getVia()));
        result.add(RlpString.create(0));//result.add(RlpString.create(rawTransaction.getVia()));
        return result;
    }
}
