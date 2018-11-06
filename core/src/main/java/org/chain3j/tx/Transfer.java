package org.chain3j.tx;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.chain3j.crypto.Credentials;
import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.core.RemoteCall;
import org.chain3j.protocol.core.methods.response.TransactionReceipt;
import org.chain3j.protocol.exceptions.TransactionException;
import org.chain3j.utils.Convert;
import org.chain3j.utils.Numeric;

/**
 * Class for performing Mc transactions on the Moac blockchain.
 */
public class Transfer extends ManagedTransaction {

    // This is the cost to send Mc between parties
    public static final BigInteger GAS_LIMIT = BigInteger.valueOf(21000);

    public Transfer(Chain3j chain3j, TransactionManager transactionManager) {
        super(chain3j, transactionManager);
    }

    /**
     * Given the duration required to execute a transaction, asyncronous execution is strongly
     * recommended via {@link Transfer#sendFunds(String, BigDecimal, Convert.Unit)}.
     *
     * @param toAddress destination address
     * @param value amount to send
     * @param unit of specified send
     *
     * @return {@link Optional} containing our transaction receipt
     * @throws ExecutionException if the computation threw an
     *                            exception
     * @throws InterruptedException if the current thread was interrupted
     *                              while waiting
     * @throws TransactionException if the transaction was not mined while waiting
     */
    private TransactionReceipt send(String toAddress, BigDecimal value, Convert.Unit unit)
            throws IOException, InterruptedException,
            TransactionException {

        BigInteger gasPrice = requestCurrentGasPrice();
        return send(toAddress, value, unit, gasPrice, GAS_LIMIT);
    }

    private TransactionReceipt send(
            String toAddress, BigDecimal value, Convert.Unit unit, BigInteger gasPrice,
            BigInteger gasLimit) throws IOException, InterruptedException,
            TransactionException {

        BigDecimal shaValue = Convert.toSha(value, unit);
        if (!Numeric.isIntegerValue(shaValue)) {
            throw new UnsupportedOperationException(
                    "Non decimal Sha value provided: " + value + " " + unit.toString()
                            + " = " + shaValue + " Sha");
        }

        String resolvedAddress = ensResolver.resolve(toAddress);
        return send(resolvedAddress, "", shaValue.toBigIntegerExact(), gasPrice, gasLimit);
    }

    public static RemoteCall<TransactionReceipt> sendFunds(
            Chain3j chain3j, Credentials credentials,
            String toAddress, BigDecimal value, Convert.Unit unit) throws InterruptedException,
            IOException, TransactionException {

        TransactionManager transactionManager = new RawTransactionManager(chain3j, credentials);

        return new RemoteCall<>(() ->
                new Transfer(chain3j, transactionManager).send(toAddress, value, unit));
    }

    /**
     * Execute the provided function as a transaction asynchronously. This is intended for one-off
     * fund transfers. For multiple, create an instance.
     *
     * @param toAddress destination address
     * @param value amount to send
     * @param unit of specified send
     *
     * @return {@link RemoteCall} containing executing transaction
     */
    public RemoteCall<TransactionReceipt> sendFunds(
            String toAddress, BigDecimal value, Convert.Unit unit) {
        return new RemoteCall<>(() -> send(toAddress, value, unit));
    }

    public RemoteCall<TransactionReceipt> sendFunds(
            String toAddress, BigDecimal value, Convert.Unit unit, BigInteger gasPrice,
            BigInteger gasLimit) {
        return new RemoteCall<>(() -> send(toAddress, value, unit, gasPrice, gasLimit));
    }
}
