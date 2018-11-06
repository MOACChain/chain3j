package org.chain3j.protocol.moac;

import rx.Observable;

import org.chain3j.protocol.Chain3jService;
import org.chain3j.protocol.admin.Admin;
import org.chain3j.protocol.admin.methods.response.BooleanResponse;
import org.chain3j.protocol.admin.methods.response.PersonalSign;
import org.chain3j.protocol.core.Request;
import org.chain3j.protocol.core.methods.response.MinerStartResponse;
import org.chain3j.protocol.moac.response.PersonalEcRecover;
import org.chain3j.protocol.moac.response.PersonalImportRawKey;
import org.chain3j.protocol.websocket.events.PendingTransactionNotification;
import org.chain3j.protocol.websocket.events.SyncingNotfication;

/**
 * JSON-RPC Request object building factory for Moac. 
 */
public interface Moac extends Admin {
    static Moac build(Chain3jService chain3jService) {
        return new JsonRpc2_0Moac(chain3jService);
    }
        
    Request<?, PersonalImportRawKey> personalImportRawKey(String keydata, String password);

    Request<?, BooleanResponse> personalLockAccount(String accountId);
    
    Request<?, PersonalSign> personalSign(String message, String accountId, String password);
    
    Request<?, PersonalEcRecover> personalEcRecover(String message, String signiture);

    Request<?, MinerStartResponse> minerStart(int threadCount);

    Request<?, BooleanResponse> minerStop();

    /**
     * Creates an observable that emits a notification when a new transaction is added
     * to the pending state and is signed with a key that is available in the node.
     *
     * @return Observable that emits a notification when a new transaction is added
     *         to the pending state
     */
    Observable<PendingTransactionNotification> newPendingTransactionsNotifications();

    /**
     * Creates an observable that emits a notification when a node starts or stops syncing.
     * @return Observalbe that emits changes to syncing status
     */
    Observable<SyncingNotfication> syncingStatusNotifications();

}
