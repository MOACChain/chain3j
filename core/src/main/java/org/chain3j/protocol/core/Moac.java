package org.chain3j.protocol.core;

import java.math.BigInteger;

import org.chain3j.protocol.core.methods.response.*;
import org.chain3j.protocol.scs.methods.response.*;

/**
 * Core MOAC JSON-RPC API.
 * Remove some unused RPC APIs, db, shh, compilers
 * TODO: vnode, scs APIs.
 */
public interface Moac {
    Request<?, Chain3ClientVersion> chain3ClientVersion();

    Request<?, Chain3Sha3> chain3Sha3(String data);

    Request<?, NetVersion> netVersion();

    Request<?, NetListening> netListening();

    Request<?, NetPeerCount> netPeerCount();

    Request<?, McProtocolVersion> mcProtocolVersion();

    Request<?, McCoinbase> mcCoinbase();

    Request<?, McSyncing> mcSyncing();

    Request<?, McMining> mcMining();

    Request<?, McHashrate> mcHashrate();

    Request<?, McGasPrice> mcGasPrice();

    Request<?, McAccounts> mcAccounts();

    Request<?, McBlockNumber> mcBlockNumber();

    Request<?, McGetBalance> mcGetBalance(
            String address, DefaultBlockParameter defaultBlockParameter);

    Request<?, McGetStorageAt> mcGetStorageAt(
            String address, BigInteger position,
            DefaultBlockParameter defaultBlockParameter);

    Request<?, McGetTransactionCount> mcGetTransactionCount(
            String address, DefaultBlockParameter defaultBlockParameter);

    Request<?, McGetBlockTransactionCountByHash> mcGetBlockTransactionCountByHash(
            String blockHash);

    Request<?, McGetBlockTransactionCountByNumber> mcGetBlockTransactionCountByNumber(
            DefaultBlockParameter defaultBlockParameter);

    Request<?, McGetUncleCountByBlockHash> mcGetUncleCountByBlockHash(String blockHash);

    Request<?, McGetUncleCountByBlockNumber> mcGetUncleCountByBlockNumber(
            DefaultBlockParameter defaultBlockParameter);

    Request<?, McGetCode> mcGetCode(String address, DefaultBlockParameter defaultBlockParameter);

    Request<?, McSign> mcSign(String address, String sha3HashOfDataToSign);

    Request<?, org.chain3j.protocol.core.methods.response.McSendTransaction> mcSendTransaction(
            org.chain3j.protocol.core.methods.request.Transaction transaction);

    Request<?, org.chain3j.protocol.core.methods.response.McSendTransaction> mcSendRawTransaction(
            String signedTransactionData);

    Request<?, org.chain3j.protocol.core.methods.response.McCall> mcCall(
            org.chain3j.protocol.core.methods.request.Transaction transaction,
            DefaultBlockParameter defaultBlockParameter);

    Request<?, McEstimateGas> mcEstimateGas(
            org.chain3j.protocol.core.methods.request.Transaction transaction);

    Request<?, McBlock> mcGetBlockByHash(String blockHash, boolean returnFullTransactionObjects);

    Request<?, McBlock> mcGetBlockByNumber(
            DefaultBlockParameter defaultBlockParameter,
            boolean returnFullTransactionObjects);

    Request<?, McTransaction> mcGetTransactionByHash(String transactionHash);

    Request<?, McTransaction> mcGetTransactionByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex);

    Request<?, McTransaction> mcGetTransactionByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex);

    Request<?, McGetTransactionReceipt> mcGetTransactionReceipt(String transactionHash);

    Request<?, McBlock> mcGetUncleByBlockHashAndIndex(
            String blockHash, BigInteger transactionIndex);

    Request<?, McBlock> mcGetUncleByBlockNumberAndIndex(
            DefaultBlockParameter defaultBlockParameter, BigInteger transactionIndex);

    Request<?, McFilter> mcNewFilter(org.chain3j.protocol.core.methods.request.McFilter mcFilter);

    Request<?, McFilter> mcNewBlockFilter();

    Request<?, McFilter> mcNewPendingTransactionFilter();

    Request<?, McUninstallFilter> mcUninstallFilter(BigInteger filterId);

    Request<?, McLog> mcGetFilterChanges(BigInteger filterId);

    Request<?, McLog> mcGetFilterLogs(BigInteger filterId);

    Request<?, McLog> mcGetLogs(org.chain3j.protocol.core.methods.request.McFilter mcFilter);

    Request<?, McGetWork> mcGetWork();

    Request<?, McSubmitWork> mcSubmitWork(String nonce, String headerPowHash, String mixDigest);

    Request<?, McSubmitHashrate> mcSubmitHashrate(String hashrate, String clientId);

    Request<?, VnodeAddress> vnodeAddress();

    Request<?, VnodeIP> vnodeIP();

    Request<?, VnodeShowToPublic> vnodeShowToPublic();

    Request<?, VnodeServiceCfg> vnodeServiceCfg(); //?????

    Request<?, VnodeScsService> vnodeScsService(); //是确定scs连接到vnode吗？

    //scs
    //Request<?, ScsDirectCall> directCall(org.chain3j.protocol.core.methods.request.Transaction transaction);//parameters类型存在疑问

    Request<?, ScsGetBlock> getBlock(String microChainAddress, DefaultBlockParameter defaultBlockParameter);

//    Request<?, ScsGetMicroChainInfo> getmicroChainInfo(String scsAddress);

//    Request<?,ScsGetReceiptByHash> getReceiptByHash(String miccroAddress, String transactionHash);

    Request<?,ScsGetSCSId> getSCSId();

    Request<?, ScsGetDappState> getDappState(String dappAddress);

    Request<?, ScsGetMicroChainInfo> getMicroChainInfo(String dappAddress); //dapp的信息？ 还是

    Request<?, ScsGetMicroChainList> getMicroChainList();

    Request<?, ScsGetBlockNumber> getBlockNumber(String dappAddress);

    Request<?, ScsGetBalance> getBalance(String dappAddress, String account);

    Request<?, ScsGetNonce> getNonce(String dappAddress, String account);

    Request<?, ScsGetTransactionReceipt> getTransactionReceipt(String dappAddress, String txhash);

}
