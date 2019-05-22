package org.chain3j.protocol.core;

import java.math.BigInteger;

import org.chain3j.protocol.core.methods.response.Chain3ClientVersion;
import org.chain3j.protocol.core.methods.response.Chain3Sha3;
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
import org.chain3j.protocol.core.methods.response.McSyncing;
import org.chain3j.protocol.core.methods.response.McTransaction;
import org.chain3j.protocol.core.methods.response.McUninstallFilter;
import org.chain3j.protocol.core.methods.response.NetListening;
import org.chain3j.protocol.core.methods.response.NetPeerCount;
import org.chain3j.protocol.core.methods.response.NetVersion;
import org.chain3j.protocol.core.methods.response.VnodeAddress;
import org.chain3j.protocol.core.methods.response.VnodeIP;
import org.chain3j.protocol.core.methods.response.VnodeScsService;//bool
import org.chain3j.protocol.core.methods.response.VnodeServiceCfg;
import org.chain3j.protocol.core.methods.response.VnodeShowToPublic;//bool
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

    Request<?, VnodeServiceCfg> vnodeServiceCfg();

    Request<?, VnodeScsService> vnodeScsService();

    //scs

    Request<?, ScsDirectCall> scsDirectCall(org.chain3j.protocol.core.methods.request.Transaction transaction);

    Request<?, ScsGetBalance> scsGetBalance(String dappAddress, String account);

    Request<?, ScsGetBlock> scsGetBlock(String microChainAddress, DefaultBlockParameter defaultBlockParameter);

    Request<?, ScsGetBlockList> scsGetBlockList(String address, BigInteger startBlock, BigInteger endBlock);

    Request<?, ScsGetBlockNumber> scsGetBlockNumber(String dappAddress);

    Request<?, ScsGetDappState> scsGetDappState(String dappAddress);

    Request<?, ScsGetMicroChainList> scsGetMicroChainList();

    Request<?, ScsGetMicroChainInfo> scsGetMicroChainInfo(String dappAddress);

    Request<?, ScsGetNonce> scsGetNonce(String dappAddress, String account);

    Request<?,ScsGetSCSId> scsGetSCSId();

    Request<?,ScsGetReceiptByHash> scsGetReceiptByHash(String microAddress, String transactionHash);

    Request<?, ScsGetReceiptByNonce> scsGetReceiptByNonce(String microAddress, String checkedAddress, Integer nonce);

    Request<?, ScsGetTransactionByHash> scsGetTransactionByHash(String address, String txhash);

    Request<?, ScsGetTransactionByNonce> scsGetTransactionByNonce(String address, String checkedAddress, Integer nonce);

    Request<?, ScsGetExchangeByAddress> scsGetExchangeByAddress(String address, String checkAddress, BigInteger depositRecords,
                                                             BigInteger depositRecordsNumber, BigInteger depositingRecords,
                                                             BigInteger depositingRecordsNumber, BigInteger withdrawRecords,
                                                             BigInteger withdrawRecordsNumber, BigInteger withdrawingRecords, BigInteger withdrawingRecordsNumber);

    Request<?, ScsGetExchangeInfo> scsGetExchangeInfo(String address, BigInteger depositingRecords,
                                                   BigInteger depositingRecordsNumber, BigInteger withdrawingRecords, BigInteger withdrawingRecordsNumber);

    Request<?, ScsGetTransactionReceipt> scsGetTransactionReceipt(String dappAddress, String txhash);

    Request<?,ScsGetTxpool>scsGetTxpool(String address); //

}
