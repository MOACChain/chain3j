package org.chain3j.protocol.moac;

import java.util.Arrays;
import java.util.Collections;

import rx.Observable;

import org.chain3j.protocol.Chain3jService;
import org.chain3j.protocol.admin.JsonRpc2_0Admin;
import org.chain3j.protocol.admin.methods.response.BooleanResponse;
import org.chain3j.protocol.admin.methods.response.PersonalSign;
import org.chain3j.protocol.core.Request;
import org.chain3j.protocol.core.methods.response.McSubscribe;
import org.chain3j.protocol.core.methods.response.MinerStartResponse;
import org.chain3j.protocol.moac.response.PersonalEcRecover;
import org.chain3j.protocol.moac.response.PersonalImportRawKey;
import org.chain3j.protocol.websocket.events.PendingTransactionNotification;
import org.chain3j.protocol.websocket.events.SyncingNotfication;

/**
 * JSON-RPC 2.0 factory implementation for MOAC.
 */
public class JsonRpc2_0Moac extends JsonRpc2_0Admin implements Moac {

    public JsonRpc2_0Moac(Chain3jService chain3jService) {
        super(chain3jService);
    }
    
    @Override
    public Request<?, PersonalImportRawKey> personalImportRawKey(
            String keydata, String password) {
        return new Request<>(
                "personal_importRawKey",
                Arrays.asList(keydata, password),
                chain3jService,
                PersonalImportRawKey.class);
    }

    @Override
    public Request<?, BooleanResponse> personalLockAccount(String accountId) {
        return new Request<>(
                "personal_lockAccount",
                Arrays.asList(accountId),
                chain3jService,
                BooleanResponse.class);
    }

    @Override
    public Request<?, PersonalSign> personalSign(
            String message, String accountId, String password) {
        return new Request<>(
                "personal_sign",
                Arrays.asList(message,accountId,password),
                chain3jService,
                PersonalSign.class);
    }

    @Override
    public Request<?, PersonalEcRecover> personalEcRecover(
            String hexMessage, String signedMessage) {
        return new Request<>(
                "personal_ecRecover",
                Arrays.asList(hexMessage,signedMessage),
                chain3jService,
                PersonalEcRecover.class);
    }

    @Override
    public Request<?, MinerStartResponse> minerStart(int threadCount) {
        return new Request<>(
                "miner_start",
                Arrays.asList(threadCount),
                chain3jService,
                MinerStartResponse.class);
    }

    @Override
    public Request<?, BooleanResponse> minerStop() {
        return new Request<>(
                "miner_stop",
                Collections.<String>emptyList(),
                chain3jService,
                BooleanResponse.class);
    }

    public Observable<PendingTransactionNotification> newPendingTransactionsNotifications() {
        return chain3jService.subscribe(
                new Request<>(
                        "mc_subscribe",
                        Arrays.asList("newPendingTransactions"),
                        chain3jService,
                        McSubscribe.class),
                "mc_unsubscribe",
                PendingTransactionNotification.class
        );
    }

    @Override
    public Observable<SyncingNotfication> syncingStatusNotifications() {
        return chain3jService.subscribe(
                new Request<>(
                        "mc_subscribe",
                        Arrays.asList("syncing"),
                        chain3jService,
                        McSubscribe.class),
                "mc_unsubscribe",
                SyncingNotfication.class
        );
    }
}
