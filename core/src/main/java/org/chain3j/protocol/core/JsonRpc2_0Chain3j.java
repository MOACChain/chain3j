package org.chain3j.protocol.core;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

import rx.Observable;

import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.Chain3jService;
// import org.chain3j.protocol.core.methods.request.ShhFilter;
// import org.chain3j.protocol.core.methods.request.ShhPost;
import org.chain3j.protocol.core.methods.request.Transaction;
// import org.chain3j.protocol.core.methods.response.DbGetHex;
// import org.chain3j.protocol.core.methods.response.DbGetString;
// import org.chain3j.protocol.core.methods.response.DbPutHex;
// import org.chain3j.protocol.core.methods.response.DbPutString;
import org.chain3j.protocol.core.methods.response.Chain3ClientVersion;
import org.chain3j.protocol.core.methods.response.Chain3Sha3;
import org.chain3j.protocol.core.methods.response.Log;
import org.chain3j.protocol.core.methods.response.McAccounts;
import org.chain3j.protocol.core.methods.response.McBlock;
import org.chain3j.protocol.core.methods.response.McBlockNumber;
import org.chain3j.protocol.core.methods.response.McCoinbase;
import org.chain3j.protocol.core.methods.response.McEstimateGas;
import org.chain3j.protocol.core.methods.response.McFilter;
import org.chain3j.protocol.core.methods.response.McGasPrice;
import org.chain3j.protocol.core.methods.response.McGetBalance;
import org.chain3j.protocol.core.methods.response.McGetBlockTransactionCountByHash;
import org.chain3j.protocol.core.methods.response.McGetBlockTransactionCountByNumber;
import org.chain3j.protocol.core.methods.response.McGetCode;
import org.chain3j.protocol.core.methods.response.McGetCompilers;
import org.chain3j.protocol.core.methods.response.McGetStorageAt;
import org.chain3j.protocol.core.methods.response.McGetTransactionCount;
import org.chain3j.protocol.core.methods.response.McGetTransactionReceipt;
import org.chain3j.protocol.core.methods.response.McGetUncleCountByBlockHash;
import org.chain3j.protocol.core.methods.response.McGetUncleCountByBlockNumber;
import org.chain3j.protocol.core.methods.response.McGetWork;
import org.chain3j.protocol.core.methods.response.McHashrate;
import org.chain3j.protocol.core.methods.response.McLog;
import org.chain3j.protocol.core.methods.response.McMining;
import org.chain3j.protocol.core.methods.response.McProtocolVersion;
import org.chain3j.protocol.core.methods.response.McSign;
import org.chain3j.protocol.core.methods.response.McSubmitHashrate;
import org.chain3j.protocol.core.methods.response.McSubmitWork;
import org.chain3j.protocol.core.methods.response.McSubscribe;
import org.chain3j.protocol.core.methods.response.McSyncing;
import org.chain3j.protocol.core.methods.response.McTransaction;
import org.chain3j.protocol.core.methods.response.McUninstallFilter;
import org.chain3j.protocol.core.methods.response.NetListening;
import org.chain3j.protocol.core.methods.response.NetPeerCount;
import org.chain3j.protocol.core.methods.response.NetVersion;
// import org.chain3j.protocol.core.methods.response.ShhAddToGroup;
// import org.chain3j.protocol.core.methods.response.ShhHasIdentity;
// import org.chain3j.protocol.core.methods.response.ShhMessages;
// import org.chain3j.protocol.core.methods.response.ShhNewFilter;
// import org.chain3j.protocol.core.methods.response.ShhNewGroup;
// import org.chain3j.protocol.core.methods.response.ShhNewIdentity;
// import org.chain3j.protocol.core.methods.response.ShhUninstallFilter;
// import org.chain3j.protocol.core.methods.response.ShhVersion;
import org.chain3j.protocol.rx.JsonRpc2_0Rx;
import org.chain3j.protocol.websocket.events.LogNotification;
import org.chain3j.protocol.websocket.events.NewHeadsNotification;
import org.chain3j.utils.Async;
import org.chain3j.utils.Numeric;

/**
 * JSON-RPC 2.0 factory implementation.
 */
public class JsonRpc2_0Chain3j implements Chain3j {

    public static final int DEFAULT_BLOCK_TIME = 15 * 1000;

    protected final Chain3jService chain3jService;
    private final JsonRpc2_0Rx chain3jRx;
    private final long blockTime;
    private final ScheduledExecutorService scheduledExecutorService;

    public JsonRpc2_0Chain3j(Chain3jService chain3jService) {
        this(chain3jService, DEFAULT_BLOCK_TIME, Async.defaultExecutorService());
    }

    public JsonRpc2_0Chain3j(
            Chain3jService chain3jService, long pollingInterval,
            ScheduledExecutorService scheduledExecutorService) {
        this.chain3jService = chain3jService;
        this.chain3jRx = new JsonRpc2_0Rx(this, scheduledExecutorService);
        this.blockTime = pollingInterval;
        this.scheduledExecutorService = scheduledExecutorService;
    }

    @Override
    public Request<?, Chain3ClientVersion> chain3ClientVersion() {
        return new Request<>(
                "chain3_clientVersion",
                Collections.<String>emptyList(),
                chain3jService,
                Chain3ClientVersion.class);
    }

    @Override
    public Request<?, Chain3Sha3> chain3Sha3(String data) {
        return new Request<>(
                "chain3_sha3",
                Arrays.asList(data),
                chain3jService,
                Chain3Sha3.class);
    }

    @Override
    public Request<?, NetVersion> netVersion() {
        return new Request<>(
                "net_version",
                Collections.<String>emptyList(),
                chain3jService,
                NetVersion.class);
    }

    @Override
    public Request<?, NetListening> netListening() {
        return new Request<>(
                "net_listening",
                Collections.<String>emptyList(),
                chain3jService,
                NetListening.class);
    }

    @Override
    public Request<?, NetPeerCount> netPeerCount() {
        return new Request<>(
                "net_peerCount",
                Collections.<String>emptyList(),
                chain3jService,
                NetPeerCount.class);
    }

    @Override
    public Request<?, McProtocolVersion> mcProtocolVersion() {
        return new Request<>(
                "mc_protocolVersion",
                Collections.<String>emptyList(),
                chain3jService,
                McProtocolVersion.class);
    }

    @Override
    public Request<?, McCoinbase> mcCoinbase() {
        return new Request<>(
                "mc_coinbase",
                Collections.<String>emptyList(),
                chain3jService,
                McCoinbase.class);
    }

    @Override
    public Request<?, McSyncing> mcSyncing() {
        return new Request<>(
                "mc_syncing",
                Collections.<String>emptyList(),
                chain3jService,
                McSyncing.class);
    }

    @Override
    public Request<?, McMining> mcMining() {
        return new Request<>(
                "mc_mining",
                Collections.<String>emptyList(),
                chain3jService,
                McMining.class);
    }

    @Override
    public Request<?, McHashrate> mcHashrate() {
        return new Request<>(
                "mc_hashrate",
                Collections.<String>emptyList(),
                chain3jService,
                McHashrate.class);
    }

    @Override
    public Request<?, McGasPrice> mcGasPrice() {
        return new Request<>(
                "mc_gasPrice",
                Collections.<String>emptyList(),
                chain3jService,
                McGasPrice.class);
    }

    @Override
    public Request<?, McAccounts> mcAccounts() {
        return new Request<>(
                "mc_accounts",
                Collections.<String>emptyList(),
                chain3jService,
                McAccounts.class);
    }

    @Override
    public Request<?, McBlockNumber> mcBlockNumber() {
        return new Request<>(
                "mc_blockNumber",
                Collections.<String>emptyList(),
                chain3jService,
                McBlockNumber.class);
    }

    @Override
    public Request<?, McGetBalance> mcGetBalance(
            String address, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "mc_getBalance",
                Arrays.asList(address, defaultBlockParameter.getValue()),
                chain3jService,
                McGetBalance.class);
    }

    @Override
    public Request<?, McGetStorageAt> mcGetStorageAt(
            String address, BigInteger position, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "mc_getStorageAt",
                Arrays.asList(
                        address,
                        Numeric.encodeQuantity(position),
                        defaultBlockParameter.getValue()),
                chain3jService,
                McGetStorageAt.class);
    }

    @Override
    public Request<?, McGetTransactionCount> mcGetTransactionCount(
            String address, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "mc_getTransactionCount",
                Arrays.asList(address, defaultBlockParameter.getValue()),
                chain3jService,
                McGetTransactionCount.class);
    }

    @Override
    public Request<?, McGetBlockTransactionCountByHash> mcGetBlockTransactionCountByHash(
            String blockHash) {
        return new Request<>(
                "mc_getBlockTransactionCountByHash",
                Arrays.asList(blockHash),
                chain3jService,
                McGetBlockTransactionCountByHash.class);
    }

    @Override
    public Request<?, McGetBlockTransactionCountByNumber> mcGetBlockTransactionCountByNumber(
            DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "mc_getBlockTransactionCountByNumber",
                Arrays.asList(defaultBlockParameter.getValue()),
                chain3jService,
                McGetBlockTransactionCountByNumber.class);
    }

    @Override
    public Request<?, McGetUncleCountByBlockHash> mcGetUncleCountByBlockHash(String blockHash) {
        return new Request<>(
                "mc_getUncleCountByBlockHash",
                Arrays.asList(blockHash),
                chain3jService,
                McGetUncleCountByBlockHash.class);
    }

    @Override
    public Request<?, McGetUncleCountByBlockNumber> mcGetUncleCountByBlockNumber(
            DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "mc_getUncleCountByBlockNumber",
                Arrays.asList(defaultBlockParameter.getValue()),
                chain3jService,
                McGetUncleCountByBlockNumber.class);
    }

    @Override
    public Request<?, McGetCode> mcGetCode(
            String address, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "mc_getCode",
                Arrays.asList(address, defaultBlockParameter.getValue()),
                chain3jService,
                McGetCode.class);
    }

    @Override
    public Request<?, McSign> mcSign(String address, String sha3HashOfDataToSign) {
        return new Request<>(
                "mc_sign",
                Arrays.asList(address, sha3HashOfDataToSign),
                chain3jService,
                McSign.class);
    }

    @Override
    public Request<?, org.chain3j.protocol.core.methods.response.McSendTransaction>
            mcSendTransaction(
            Transaction transaction) {
        return new Request<>(
                "mc_sendTransaction",
                Arrays.asList(transaction),
                chain3jService,
                org.chain3j.protocol.core.methods.response.McSendTransaction.class);
    }

    @Override
    public Request<?, org.chain3j.protocol.core.methods.response.McSendTransaction>
            mcSendRawTransaction(
            String signedTransactionData) {
        return new Request<>(
                "mc_sendRawTransaction",
                Arrays.asList(signedTransactionData),
                chain3jService,
                org.chain3j.protocol.core.methods.response.McSendTransaction.class);
    }

    @Override
    public Request<?, org.chain3j.protocol.core.methods.response.McCall> mcCall(
            Transaction transaction, DefaultBlockParameter defaultBlockParameter) {
        return new Request<>(
                "mc_call",
                Arrays.asList(transaction, defaultBlockParameter),
                chain3jService,
                org.chain3j.protocol.core.methods.response.McCall.class);
    }

    @Override
    public Request<?, McEstimateGas> mcEstimateGas(Transaction transaction) {
        return new Request<>(
                "mc_estimateGas",
                Arrays.asList(transaction),
                chain3jService,
                McEstimateGas.class);
    }

    @Override
    public Request<?, McBlock> mcGetBlockByHash(
            String blockHash, boolean returnFullTransactionObjects) {
        return new Request<>(
                "mc_getBlockByHash",
                Arrays.asList(
                        blockHash,
                        returnFullTransactionObjects),
                chain3jService,
                McBlock.class);
    }

    @Override
    public Request<?, McBlock> mcGetBlockByNumber(
            DefaultBlockParameter defaultBlockParameter,
            boolean returnFullTransactionObjects) {
        return new Request<>(
                "mc_getBlockByNumber",
                Arrays.asList(
                        defaultBlockParameter.getValue(),
                        returnFullTransactionObjects),
                chain3jService,
                McBlock.class);
    }

    @Override
    public Request<?, McTransaction> mcGetTransactionByHash(String transactionHash) {
        return new Request<>(
                "mc_getTransactionByHash",
                Arrays.asList(transactionHash),
                chain3jService,
                McTransaction.class);
    }

    @Override
    public Request<?, McTransaction> mcGetTransactionByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex) {
        return new Request<>(
                "mc_getTransactionByBlockHashAndIndex",
                Arrays.asList(
                        blockHash,
                        Numeric.encodeQuantity(transactionIndex)),
                chain3jService,
                McTransaction.class);
    }

    @Override
    public Request<?, McTransaction> mcGetTransactionByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex) {
        return new Request<>(
                "mc_getTransactionByBlockNumberAndIndex",
                Arrays.asList(
                        defaultBlockParameter.getValue(),
                        Numeric.encodeQuantity(transactionIndex)),
                chain3jService,
                McTransaction.class);
    }

    @Override
    public Request<?, McGetTransactionReceipt> mcGetTransactionReceipt(String transactionHash) {
        return new Request<>(
                "mc_getTransactionReceipt",
                Arrays.asList(transactionHash),
                chain3jService,
                McGetTransactionReceipt.class);
    }

    @Override
    public Request<?, McBlock> mcGetUncleByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex) {
        return new Request<>(
                "mc_getUncleByBlockHashAndIndex",
                Arrays.asList(
                        blockHash,
                        Numeric.encodeQuantity(transactionIndex)),
                chain3jService,
                McBlock.class);
    }

    @Override
    public Request<?, McBlock> mcGetUncleByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger uncleIndex) {
        return new Request<>(
                "mc_getUncleByBlockNumberAndIndex",
                Arrays.asList(
                        defaultBlockParameter.getValue(),
                        Numeric.encodeQuantity(uncleIndex)),
                chain3jService,
                McBlock.class);
    }

    @Override
    public Request<?, McGetCompilers> mcGetCompilers() {
        return new Request<>(
                "mc_getCompilers",
                Collections.<String>emptyList(),
                chain3jService,
                McGetCompilers.class);
    }

    //     @Override
    //     public Request<?, McCompileLLL> mcCompileLLL(String sourceCode) {
    //         return new Request<>(
    //                 "mc_compileLLL",
    //                 Arrays.asList(sourceCode),
    //                 chain3jService,
    //                 McCompileLLL.class);
    //     }

    //     @Override
    //     public Request<?, McCompileSolidity> mcCompileSolidity(String sourceCode) {
    //         return new Request<>(
    //                 "mc_compileSolidity",
    //                 Arrays.asList(sourceCode),
    //                 chain3jService,
    //                 McCompileSolidity.class);
    //     }

    //     @Override
    //     public Request<?, McCompileSerpent> mcCompileSerpent(String sourceCode) {
    //         return new Request<>(
    //                 "mc_compileSerpent",
    //                 Arrays.asList(sourceCode),
    //                 chain3jService,
    //                 McCompileSerpent.class);
    //     }

    @Override
    public Request<?, McFilter> mcNewFilter(
            org.chain3j.protocol.core.methods.request.McFilter mcFilter) {
        return new Request<>(
                "mc_newFilter",
                Arrays.asList(mcFilter),
                chain3jService,
                McFilter.class);
    }

    @Override
    public Request<?, McFilter> mcNewBlockFilter() {
        return new Request<>(
                "mc_newBlockFilter",
                Collections.<String>emptyList(),
                chain3jService,
                McFilter.class);
    }

    @Override
    public Request<?, McFilter> mcNewPendingTransactionFilter() {
        return new Request<>(
                "mc_newPendingTransactionFilter",
                Collections.<String>emptyList(),
                chain3jService,
                McFilter.class);
    }

    @Override
    public Request<?, McUninstallFilter> mcUninstallFilter(BigInteger filterId) {
        return new Request<>(
                "mc_uninstallFilter",
                Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
                chain3jService,
                McUninstallFilter.class);
    }

    @Override
    public Request<?, McLog> mcGetFilterChanges(BigInteger filterId) {
        return new Request<>(
                "mc_getFilterChanges",
                Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
                chain3jService,
                McLog.class);
    }

    @Override
    public Request<?, McLog> mcGetFilterLogs(BigInteger filterId) {
        return new Request<>(
                "mc_getFilterLogs",
                Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
                chain3jService,
                McLog.class);
    }

    @Override
    public Request<?, McLog> mcGetLogs(
            org.chain3j.protocol.core.methods.request.McFilter mcFilter) {
        return new Request<>(
                "mc_getLogs",
                Arrays.asList(mcFilter),
                chain3jService,
                McLog.class);
    }

    @Override
    public Request<?, McGetWork> mcGetWork() {
        return new Request<>(
                "mc_getWork",
                Collections.<String>emptyList(),
                chain3jService,
                McGetWork.class);
    }

    @Override
    public Request<?, McSubmitWork> mcSubmitWork(
            String nonce, String headerPowHash, String mixDigest) {
        return new Request<>(
                "mc_submitWork",
                Arrays.asList(nonce, headerPowHash, mixDigest),
                chain3jService,
                McSubmitWork.class);
    }

    @Override
    public Request<?, McSubmitHashrate> mcSubmitHashrate(String hashrate, String clientId) {
        return new Request<>(
                "mc_submitHashrate",
                Arrays.asList(hashrate, clientId),
                chain3jService,
                McSubmitHashrate.class);
    }

    //     @Override
    //     public Request<?, DbPutString> dbPutString(
    //             String databaseName, String keyName, String stringToStore) {
    //         return new Request<>(
    //                 "db_putString",
    //                 Arrays.asList(databaseName, keyName, stringToStore),
    //                 chain3jService,
    //                 DbPutString.class);
    //     }

    //     @Override
    //     public Request<?, DbGetString> dbGetString(String databaseName, String keyName) {
    //         return new Request<>(
    //                 "db_getString",
    //                 Arrays.asList(databaseName, keyName),
    //                 chain3jService,
    //                 DbGetString.class);
    //     }

    //     @Override
    //     public Request<?, DbPutHex> dbPutHex(String databaseName, String keyName, String dataToStore) {
    //         return new Request<>(
    //                 "db_putHex",
    //                 Arrays.asList(databaseName, keyName, dataToStore),
    //                 chain3jService,
    //                 DbPutHex.class);
    //     }

    //     @Override
    //     public Request<?, DbGetHex> dbGetHex(String databaseName, String keyName) {
    //         return new Request<>(
    //                 "db_getHex",
    //                 Arrays.asList(databaseName, keyName),
    //                 chain3jService,
    //                 DbGetHex.class);
    //     }

    //     @Override
    //     public Request<?, org.chain3j.protocol.core.methods.response.ShhPost> shhPost(ShhPost shhPost) {
    //         return new Request<>(
    //                 "shh_post",
    //                 Arrays.asList(shhPost),
    //                 chain3jService,
    //                 org.chain3j.protocol.core.methods.response.ShhPost.class);
    //     }

    //     @Override
    //     public Request<?, ShhVersion> shhVersion() {
    //         return new Request<>(
    //                 "shh_version",
    //                 Collections.<String>emptyList(),
    //                 chain3jService,
    //                 ShhVersion.class);
    //     }

    //     @Override
    //     public Request<?, ShhNewIdentity> shhNewIdentity() {
    //         return new Request<>(
    //                 "shh_newIdentity",
    //                 Collections.<String>emptyList(),
    //                 chain3jService,
    //                 ShhNewIdentity.class);
    //     }

    //     @Override
    //     public Request<?, ShhHasIdentity> shhHasIdentity(String identityAddress) {
    //         return new Request<>(
    //                 "shh_hasIdentity",
    //                 Arrays.asList(identityAddress),
    //                 chain3jService,
    //                 ShhHasIdentity.class);
    //     }

    //     @Override
    //     public Request<?, ShhNewGroup> shhNewGroup() {
    //         return new Request<>(
    //                 "shh_newGroup",
    //                 Collections.<String>emptyList(),
    //                 chain3jService,
    //                 ShhNewGroup.class);
    //     }

    //     @Override
    //     public Request<?, ShhAddToGroup> shhAddToGroup(String identityAddress) {
    //         return new Request<>(
    //                 "shh_addToGroup",
    //                 Arrays.asList(identityAddress),
    //                 chain3jService,
    //                 ShhAddToGroup.class);
    //     }

    //     @Override
    //     public Request<?, ShhNewFilter> shhNewFilter(ShhFilter shhFilter) {
    //         return new Request<>(
    //                 "shh_newFilter",
    //                 Arrays.asList(shhFilter),
    //                 chain3jService,
    //                 ShhNewFilter.class);
    //     }

    //     @Override
    //     public Request<?, ShhUninstallFilter> shhUninstallFilter(BigInteger filterId) {
    //         return new Request<>(
    //                 "shh_uninstallFilter",
    //                 Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
    //                 chain3jService,
    //                 ShhUninstallFilter.class);
    //     }

    //     @Override
    //     public Request<?, ShhMessages> shhGetFilterChanges(BigInteger filterId) {
    //         return new Request<>(
    //                 "shh_getFilterChanges",
    //                 Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
    //                 chain3jService,
    //                 ShhMessages.class);
    //     }

    //     @Override
    //     public Request<?, ShhMessages> shhGetMessages(BigInteger filterId) {
    //         return new Request<>(
    //                 "shh_getMessages",
    //                 Arrays.asList(Numeric.toHexStringWithPrefixSafe(filterId)),
    //                 chain3jService,
    //                 ShhMessages.class);
    //     }

    @Override
    public Observable<NewHeadsNotification> newHeadsNotifications() {
        return chain3jService.subscribe(
                new Request<>(
                        "mc_subscribe",
                        Collections.singletonList("newHeads"),
                        chain3jService,
                        McSubscribe.class),
                "mc_unsubscribe",
                NewHeadsNotification.class
        );
    }

    @Override
    public Observable<LogNotification> logsNotifications(
            List<String> addresses, List<String> topics) {

        Map<String, Object> params = createLogsParams(addresses, topics);

        return chain3jService.subscribe(
                new Request<>(
                        "mc_subscribe",
                        Arrays.asList("logs", params),
                        chain3jService,
                        McSubscribe.class),
                "mc_unsubscribe",
                LogNotification.class
        );
    }

    private Map<String, Object> createLogsParams(List<String> addresses, List<String> topics) {
        Map<String, Object> params = new HashMap<>();
        if (!addresses.isEmpty()) {
            params.put("address", addresses);
        }
        if (!topics.isEmpty()) {
            params.put("topics", topics);
        }
        return params;
    }

    @Override
    public Observable<String> mcBlockHashObservable() {
        return chain3jRx.mcBlockHashObservable(blockTime);
    }

    @Override
    public Observable<String> mcPendingTransactionHashObservable() {
        return chain3jRx.mcPendingTransactionHashObservable(blockTime);
    }

    @Override
    public Observable<Log> mcLogObservable(
            org.chain3j.protocol.core.methods.request.McFilter mcFilter) {
        return chain3jRx.mcLogObservable(mcFilter, blockTime);
    }

    @Override
    public Observable<org.chain3j.protocol.core.methods.response.Transaction>
            transactionObservable() {
        return chain3jRx.transactionObservable(blockTime);
    }

    @Override
    public Observable<org.chain3j.protocol.core.methods.response.Transaction>
            pendingTransactionObservable() {
        return chain3jRx.pendingTransactionObservable(blockTime);
    }

    @Override
    public Observable<McBlock> blockObservable(boolean fullTransactionObjects) {
        return chain3jRx.blockObservable(fullTransactionObjects, blockTime);
    }

    @Override
    public Observable<McBlock> replayBlocksObservable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock,
            boolean fullTransactionObjects) {
        return chain3jRx.replayBlocksObservable(startBlock, endBlock, fullTransactionObjects);
    }

    @Override
    public Observable<McBlock> replayBlocksObservable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock,
            boolean fullTransactionObjects, boolean ascending) {
        return chain3jRx.replayBlocksObservable(startBlock, endBlock,
                fullTransactionObjects, ascending);
    }

    @Override
    public Observable<org.chain3j.protocol.core.methods.response.Transaction>
            replayTransactionsObservable(
            DefaultBlockParameter startBlock, DefaultBlockParameter endBlock) {
        return chain3jRx.replayTransactionsObservable(startBlock, endBlock);
    }

    @Override
    public Observable<McBlock> catchUpToLatestBlockObservable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects,
            Observable<McBlock> onCompleteObservable) {
        return chain3jRx.catchUpToLatestBlockObservable(
                startBlock, fullTransactionObjects, onCompleteObservable);
    }

    @Override
    public Observable<McBlock> catchUpToLatestBlockObservable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects) {
        return chain3jRx.catchUpToLatestBlockObservable(startBlock, fullTransactionObjects);
    }

    @Override
    public Observable<org.chain3j.protocol.core.methods.response.Transaction>
            catchUpToLatestTransactionObservable(DefaultBlockParameter startBlock) {
        return chain3jRx.catchUpToLatestTransactionObservable(startBlock);
    }

    @Override
    public Observable<McBlock> catchUpToLatestAndSubscribeToNewBlocksObservable(
            DefaultBlockParameter startBlock, boolean fullTransactionObjects) {
        return chain3jRx.catchUpToLatestAndSubscribeToNewBlocksObservable(
                startBlock, fullTransactionObjects, blockTime);
    }

    @Override
    public Observable<org.chain3j.protocol.core.methods.response.Transaction>
            catchUpToLatestAndSubscribeToNewTransactionsObservable(
            DefaultBlockParameter startBlock) {
        return chain3jRx.catchUpToLatestAndSubscribeToNewTransactionsObservable(
                startBlock, blockTime);
    }

    @Override
    public void shutdown() {
        scheduledExecutorService.shutdown();
        try {
            chain3jService.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to close chain3j service", e);
        }
    }
}
