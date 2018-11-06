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

    Request<?, McGetCompilers> mcGetCompilers();

    //     Request<?, McCompileLLL> mcCompileLLL(String sourceCode);

    //     Request<?, McCompileSolidity> mcCompileSolidity(String sourceCode);

    //     Request<?, McCompileSerpent> mcCompileSerpent(String sourceCode);

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

    //     Request<?, DbPutString> dbPutString(String databaseName, String keyName, String stringToStore);

    //     Request<?, DbGetString> dbGetString(String databaseName, String keyName);

    //     Request<?, DbPutHex> dbPutHex(String databaseName, String keyName, String dataToStore);

    //     Request<?, DbGetHex> dbGetHex(String databaseName, String keyName);

    //     Request<?, org.chain3j.protocol.core.methods.response.ShhPost> shhPost(
    //             org.chain3j.protocol.core.methods.request.ShhPost shhPost);

    //     Request<?, ShhVersion> shhVersion();

    //     Request<?, ShhNewIdentity> shhNewIdentity();

    //     Request<?, ShhHasIdentity> shhHasIdentity(String identityAddress);

    //     Request<?, ShhNewGroup> shhNewGroup();

    //     Request<?, ShhAddToGroup> shhAddToGroup(String identityAddress);

    //     Request<?, ShhNewFilter> shhNewFilter(ShhFilter shhFilter);

    //     Request<?, ShhUninstallFilter> shhUninstallFilter(BigInteger filterId);

    //     Request<?, ShhMessages> shhGetFilterChanges(BigInteger filterId);

    //     Request<?, ShhMessages> shhGetMessages(BigInteger filterId);
}
