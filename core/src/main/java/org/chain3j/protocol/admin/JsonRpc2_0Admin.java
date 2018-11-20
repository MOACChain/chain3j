package org.chain3j.protocol.admin;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;

import org.chain3j.protocol.Chain3jService;
import org.chain3j.protocol.admin.methods.response.NewAccountIdentifier;
import org.chain3j.protocol.admin.methods.response.PersonalListAccounts;
import org.chain3j.protocol.admin.methods.response.PersonalUnlockAccount;
import org.chain3j.protocol.core.JsonRpc2_0Chain3j;
import org.chain3j.protocol.core.Request;
import org.chain3j.protocol.core.methods.request.Transaction;
import org.chain3j.protocol.core.methods.response.McSendTransaction;

/**
 * JSON-RPC 2.0 factory implementation for MOAC VNODE admin methods.
 */
public class JsonRpc2_0Admin extends JsonRpc2_0Chain3j implements Admin {

    public JsonRpc2_0Admin(Chain3jService chain3jService) {
        super(chain3jService);
    }
    
    public JsonRpc2_0Admin(Chain3jService chain3jService, long pollingInterval,
            ScheduledExecutorService scheduledExecutorService) {
        super(chain3jService, pollingInterval, scheduledExecutorService);
    }

    @Override
    public Request<?, PersonalListAccounts> personalListAccounts() {
        return new Request<>(
                "personal_listAccounts",
                Collections.<String>emptyList(),
                chain3jService,
                PersonalListAccounts.class);
    }

    @Override
    public Request<?, NewAccountIdentifier> personalNewAccount(String password) {
        return new Request<>(
                "personal_newAccount",
                Arrays.asList(password),
                chain3jService,
                NewAccountIdentifier.class);
    }   

    @Override
    public Request<?, PersonalUnlockAccount> personalUnlockAccount(
            String accountId, String password,
            BigInteger duration) {
        List<Object> attributes = new ArrayList<>(3);
        attributes.add(accountId);
        attributes.add(password);
        
        if (duration != null) {
            // Parity has a bug where it won't support a duration
            // See https://github.com/ethcore/parity/issues/1215
            attributes.add(duration.longValue());
        } else {
            // we still need to include the null value, otherwise Parity rejects request
            attributes.add(null);
        }
        
        return new Request<>(
                "personal_unlockAccount",
                attributes,
                chain3jService,
                PersonalUnlockAccount.class);
    }
    
    @Override
    public Request<?, PersonalUnlockAccount> personalUnlockAccount(
            String accountId, String password) {
        
        return personalUnlockAccount(accountId, password, null);
    }
    
    @Override
    public Request<?, McSendTransaction> personalSendTransaction(
            Transaction transaction, String passphrase) {
        return new Request<>(
                "personal_sendTransaction",
                Arrays.asList(transaction, passphrase),
                chain3jService,
                McSendTransaction.class);
    }
    
}
