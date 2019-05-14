package org.chain3j.protocol.core;

import java.io.IOException;
import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

import com.sun.org.apache.xerces.internal.xs.StringList;
import org.chain3j.protocol.scs.methods.response.*;
import org.chain3j.utils.Collection;
import rx.Observable;

import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.Chain3jService;
import org.chain3j.protocol.core.methods.request.Transaction;
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
import org.chain3j.protocol.core.methods.response.VnodeAddress;
import org.chain3j.protocol.core.methods.response.VnodeIP;
import org.chain3j.protocol.core.methods.response.VnodeScsService;
import org.chain3j.protocol.core.methods.response.VnodeServiceCfg;
import org.chain3j.protocol.core.methods.response.VnodeShowToPublic;
import org.chain3j.protocol.rx.JsonRpc2_0Rx;
// import org.chain3j.protocol.core.methods.response.ShhAddToGroup;
// import org.chain3j.protocol.core.methods.response.ShhHasIdentity;
// import org.chain3j.protocol.core.methods.response.ShhMessages;
// import org.chain3j.protocol.core.methods.response.ShhNewFilter;
// import org.chain3j.protocol.core.methods.response.ShhNewGroup;
// import org.chain3j.protocol.core.methods.response.ShhNewIdentity;
// import org.chain3j.protocol.core.methods.response.ShhUninstallFilter;
// import org.chain3j.protocol.core.methods.response.ShhVersion;
import org.chain3j.protocol.websocket.events.LogNotification;
import org.chain3j.protocol.websocket.events.NewHeadsNotification;
import org.chain3j.utils.Async;
import org.chain3j.utils.Numeric;

/**
 * JSON-RPC 2.0 factory implementation.
 */
public class JsonRpc2_0Chain3j implements Chain3j {

    public static final int DEFAULT_BLOCK_TIME = 15 * 1000;

    protected final Chain3jService chain3jService;// used for vnode
    private final JsonRpc2_0Rx chain3jRx;
    private final long blockTime;
    private final ScheduledExecutorService scheduledExecutorService;

    // Constructors used with VNODE services
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

    // Vnode services methods handling VNODE info
    @Override
    public Request<?, VnodeAddress> vnodeAddress() {
        return new Request<>(
                "vnode_address",
                Collections.<String>emptyList(),
                chain3jService,
                VnodeAddress.class);
    }

    // 
    @Override
    public Request<?, VnodeShowToPublic> vnodeShowToPublic() {
        return new Request<>(
                "vnode_showToPublic",
                Collections.<String>emptyList(),
                chain3jService,
                VnodeShowToPublic.class);
    }

    @Override
    public Request<?, VnodeIP> vnodeIP() {
        return new Request<>(
                "vnode_vnodeIP",
                Collections.<String>emptyList(),
                chain3jService,
                VnodeIP.class);
    }

    @Override
    public Request<?, VnodeServiceCfg> vnodeServiceCfg() {
        return new Request<>(
                "vnode_serviceCfg",
                Collections.<String>emptyList(),
                chain3jService,
                VnodeServiceCfg.class);
    }

    @Override
    public Request<?, VnodeScsService> vnodeScsService() {
        return new Request<>(
                "vnode_scsService",
                Collections.<String>emptyList(),
                chain3jService,
                VnodeScsService.class);
    }

    //=================================================================
    // SCS related JSON RPC 2.0
    //=================================================================
//    @Override
//    public Request<?, ScsDirectCall> directCall(String){
//        return new Request<>(
//                "scs_directCall",
//                ,
//                ScsDirectCall.class);
//    }

    @Override
    public Request<?, ScsGetBlock> getBlock(String microChainAddress, DefaultBlockParameter defaultBlockParameter){
        return new Request<>(
                "scs_getBlock",
                Arrays.asList(microChainAddress, defaultBlockParameter.getValue()),
                chain3jService,
                ScsGetBlock.class);
    }

    @Override
    public Request<?, ScsGetDappState> getDappState(String mcAddress) {
        return new Request<>(
                "scs_getDappState",
                Arrays.asList(mcAddress),
                chain3jService,
                ScsGetDappState.class);
    }

    @Override
    public Request<?, ScsGetMicroChainList> getMicroChainList() {
        return new Request<>(
                "scs_getMicroChainList",
                Collections.<String>emptyList(),
                chain3jService,
                ScsGetMicroChainList.class);
    }

    @Override
    public Request<?,ScsGetSCSId> getSCSId(){
        return new Request<>(
                "scs_getSCSId",
                Collections.emptyList(),
                chain3jService,
                ScsGetSCSId.class
        );
    }

    @Override
    public Request<?, ScsGetMicroChainInfo> getMicroChainInfo(String mcAddress) {
        return new Request<>(
                "scs_getMicroChainInfo",
                Arrays.asList(mcAddress),
                chain3jService,
                ScsGetMicroChainInfo.class);
    }

    @Override
    public Request<?, ScsGetBalance> getBalance(String mcAddress, String account) {
        return new Request<>(
                "scs_getBalance",
                Arrays.asList(mcAddress, account),
                chain3jService,
                ScsGetBalance.class);
    }

    @Override
    public Request<?, ScsGetBlockNumber> getBlockNumber(String mcAddress) {
        return new Request<>(
                "scs_getBlockNumber",
                Arrays.asList(mcAddress),
                chain3jService,
                ScsGetBlockNumber.class);
    }

    @Override
    public Request<?, ScsGetNonce> getNonce(String mcAddress, String account) {
        return new Request<>(
                "scs_getNonce",
                Arrays.asList(mcAddress, account),
                chain3jService,
                ScsGetNonce.class);
    }

    @Override
    public Request<?, ScsGetTransactionReceipt> getTransactionReceipt(String mcAddress, String txHash) {
        return new Request<>(
                "scs_getTransactionReceipt",
                Arrays.asList(mcAddress, txHash),
                chain3jService,
                ScsGetTransactionReceipt.class);
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
