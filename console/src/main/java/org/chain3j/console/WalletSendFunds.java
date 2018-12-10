package org.chain3j.console;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.chain3j.crypto.Credentials;
import org.chain3j.crypto.WalletUtils;
import org.chain3j.ens.EnsResolver;
import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.core.methods.response.Chain3ClientVersion;
import org.chain3j.protocol.core.methods.response.TransactionReceipt;
import org.chain3j.protocol.exceptions.TransactionException;
import org.chain3j.protocol.http.HttpService;
//import org.chain3j.protocol.infura.InfuraHttpService;
import org.chain3j.tx.Transfer;
import org.chain3j.utils.Convert;

import static org.chain3j.codegen.Console.exitError;

/**
 * Simple class for creating a wallet file.
 */
public class WalletSendFunds extends WalletManager {

    private static final String USAGE = "send <walletfile> <destination-address>";

    public static void main(String[] args) {
        if (args.length != 2) {
            exitError(USAGE);
        } else {
            new WalletSendFunds().run(args[0], args[1]);
        }
    }

    private void run(String walletFileLocation, String destinationAddress) {
        File walletFile = new File(walletFileLocation);
        Credentials credentials = getCredentials(walletFile);
        console.printf("Wallet for address " + credentials.getAddress() + " loaded\n");

        if (!WalletUtils.isValidAddress(destinationAddress)
                && !EnsResolver.isValidEnsName(destinationAddress)) {
            exitError("Invalid destination address specified");
        }

        Chain3j chain3j = getMoacClient();

        BigDecimal amountToTransfer = getAmountToTransfer();
        Convert.Unit transferUnit = getTransferUnit();
        BigDecimal amountInSha = Convert.toSha(amountToTransfer, transferUnit);

        confirmTransfer(amountToTransfer, transferUnit, amountInSha, destinationAddress);

        TransactionReceipt transactionReceipt = performTransfer(
                chain3j, destinationAddress, credentials, amountInSha);

        console.printf("Funds have been successfully transferred from %s to %s%n"
                        + "WalletDemo hash: %s%nMined block number: %s%n",
                credentials.getAddress(),
                destinationAddress,
                transactionReceipt.getTransactionHash(),
                transactionReceipt.getBlockNumber());
    }

    private BigDecimal getAmountToTransfer() {
        String amount = console.readLine("What amound would you like to transfer "
                + "(please enter a numeric value): ")
                .trim();
        try {
            return new BigDecimal(amount);
        } catch (NumberFormatException e) {
            exitError("Invalid amount specified");
        }
        throw new RuntimeException("Application exit failure");
    }

    private Convert.Unit getTransferUnit() {
        String unit = console.readLine("Please specify the unit (mc, sha, ...) [mc]: ")
                .trim();

        Convert.Unit transferUnit;
        if (unit.equals("")) {
            transferUnit = Convert.Unit.MC;
        } else {
            transferUnit = Convert.Unit.fromString(unit.toLowerCase());
        }

        return transferUnit;
    }

    private void confirmTransfer(
            BigDecimal amountToTransfer, Convert.Unit transferUnit, BigDecimal amountInSha,
            String destinationAddress) {

        console.printf("Please confim that you wish to transfer %s %s (%s %s) to address %s%n",
                amountToTransfer.stripTrailingZeros().toPlainString(), transferUnit,
                amountInSha.stripTrailingZeros().toPlainString(),
                Convert.Unit.SHA, destinationAddress);
        String confirm = console.readLine("Please type 'yes' to proceed: ").trim();
        if (!confirm.toLowerCase().equals("yes")) {
            exitError("OK, some other time perhaps...");
        }
    }

    private TransactionReceipt performTransfer(
            Chain3j chain3j, String destinationAddress, Credentials credentials,
            BigDecimal amountInSha) {

        console.printf("Commencing transfer (this may take a few minutes) ");
        try {
            Future<TransactionReceipt> future = Transfer.sendFunds(
                    chain3j, credentials, destinationAddress, amountInSha, Convert.Unit.SHA)
                    .sendAsync();

            while (!future.isDone()) {
                console.printf(".");
                Thread.sleep(500);
            }
            console.printf("$%n%n");
            return future.get();
        } catch (InterruptedException | ExecutionException | TransactionException | IOException e) {
            exitError("Problem encountered transferring funds: \n" + e.getMessage());
        }
        throw new RuntimeException("Application exit failure");
    }

    private Chain3j getMoacClient() {
        String clientAddress = console.readLine(
                "Please confirm address of running MOAC client you wish to send "
                + "the transfer request to [" + HttpService.DEFAULT_URL + "]: ")
                .trim();

        Chain3j chain3j;
        //Note we need to remove the infura.io for MOAC
        if (clientAddress.equals("")) {
            chain3j = Chain3j.build(new HttpService());
        // } else if (clientAddress.contains("infura.io")) {
        //     chain3j = Chain3j.build(new InfuraHttpService(clientAddress));
        } else {
            chain3j = Chain3j.build(new HttpService(clientAddress));
        }

        try {
            Chain3ClientVersion chain3ClientVersion = chain3j.chain3ClientVersion().sendAsync().get();
            if (chain3ClientVersion.hasError()) {
                exitError("Unable to process response from client: "
                        + chain3ClientVersion.getError());
            } else {
                console.printf("Connected successfully to client: %s%n",
                        chain3ClientVersion.getChain3ClientVersion());
                return chain3j;
            }
        } catch (InterruptedException | ExecutionException e) {
            exitError("Problem encountered verifying client: " + e.getMessage());
        }
        throw new RuntimeException("Application exit failure");
    }
}
