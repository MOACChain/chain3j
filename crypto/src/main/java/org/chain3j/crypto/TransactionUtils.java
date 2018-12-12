package org.chain3j.crypto;

import org.chain3j.utils.Numeric;

/**
 * WalletDemo utility functions.
 * MOAC only allow transaction signed with chainId info
 */
public class TransactionUtils {

    /**
     * Utility method to provide the transaction hash for a given transaction.
     *
     * @param rawTransaction we wish to send
     * @param credentials of the sender
     * @return encoded transaction hash
     */
    public static byte[] generateTransactionHash(
            RawTransaction rawTransaction, Integer chainId, Credentials credentials) 
            throws CipherException {
        byte[] signedMessage = TransactionEncoder.signMessage(rawTransaction, chainId, credentials);
        return Hash.sha3(signedMessage);
    }

    /**
     * Utility method to provide the transaction hash for a given 
     * transaction with EIP155 signature.
     *
     * @param rawTransaction we wish to send
     * @param chainId of the intended chain
     * @param credentials of the sender
     * @return encoded raw transaction with signature fields
     */
    public static byte[] generateRawTransactionEIP155(
            RawTransaction rawTransaction, Integer chainId, Credentials credentials) 
            throws CipherException {
        byte[] signedMessage = TransactionEncoder.signTxEIP155(rawTransaction, chainId, credentials);
        return signedMessage;
    }

    /**
     * Utility method to provide the transaction hash for a given transaction.
     *
     * @param rawTransaction we wish to send
     * @param credentials of the sender
     * @return transaction hash as a hex encoded string
     */
    // public static String generateTransactionHashHexEncoded(
    //         RawTransaction rawTransaction, Credentials credentials) {
    //     return Numeric.toHexString(generateTransactionHash(rawTransaction, credentials));
    // }

    /**
     * Utility method to provide the transaction hash for a given transaction.
     *
     * @param rawTransaction we wish to send
     * @param chainId of the intended chain
     * @param credentials of the sender
     * @return transaction hash as a hex encoded string
     */
    public static String generateTransactionHashHexEncoded(
            RawTransaction rawTransaction, Integer chainId, 
            Credentials credentials) throws CipherException {
        return Numeric.toHexString(generateTransactionHash(rawTransaction, chainId, credentials));
    }
}
