package org.chain3j.protocol.admin;

import java.math.BigInteger;
import java.util.concurrent.ScheduledExecutorService;

import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.Chain3jService;
import org.chain3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.chain3j.protocol.admin.methods.response.PersonalListAccounts;
import org.chain3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.chain3j.protocol.core.Request;
import org.chain3j.protocol.core.methods.request.Transaction;
import org.chain3j.protocol.core.methods.response.McSendTransaction;

/**
 * JSON-RPC Request object building factory for common Parity and Geth. 
 */
public interface Admin extends Chain3j {

    static Admin build(Chain3jService chain3jService) {
        return new JsonRpc2_0Admin(chain3jService);
    }
    
    static Admin build(
            Chain3jService chain3jService, long pollingInterval,
            ScheduledExecutorService scheduledExecutorService) {
        return new JsonRpc2_0Admin(chain3jService, pollingInterval, scheduledExecutorService);
    }

    public Request<?, PersonalListAccounts> personalListAccounts();
    
    public Request<?, NewAccountIdentifier> personalNewAccount(String password);
    
    public Request<?, PersonalUnlockAccount> personalUnlockAccount(
            String address, String passphrase, BigInteger duration);
    
    public Request<?, PersonalUnlockAccount> personalUnlockAccount(
            String address, String passphrase);
    
    public Request<?, McSendTransaction> personalSendTransaction(
            Transaction transaction, String password);

}   
