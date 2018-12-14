package org.chain3j.protocol.core;

import java.math.BigInteger;
import java.util.Arrays;

import org.junit.Test;

import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.RequestTester;
import org.chain3j.protocol.core.methods.request.McFilter;
import org.chain3j.protocol.core.methods.request.Transaction;
import org.chain3j.protocol.http.HttpService;
import org.chain3j.utils.Numeric;

public class RequestTest extends RequestTester {

    private Chain3j chain3j;

    @Override
    protected void initChain3Client(HttpService httpService) {
        chain3j = Chain3j.build(httpService);
    }

    @Test
    public void testChain3ClientVersion() throws Exception {
        chain3j.chain3ClientVersion().send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"chain3_clientVersion\",\"params\":[],\"id\":100}");
    }

    @Test
    public void testChain3Sha3() throws Exception {
        chain3j.chain3Sha3("0x68656c6c6f20776f726c64").send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"chain3_sha3\","
                        + "\"params\":[\"0x68656c6c6f20776f726c64\"],\"id\":1}");
    }

    @Test
    public void testNetVersion() throws Exception {
        chain3j.netVersion().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"net_version\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testNetListening() throws Exception {
        chain3j.netListening().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"net_listening\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testNetPeerCount() throws Exception {
        chain3j.netPeerCount().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"net_peerCount\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testMcProtocolVersion() throws Exception {
        chain3j.mcProtocolVersion().send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"mc_protocolVersion\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testMcSyncing() throws Exception {
        chain3j.mcSyncing().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_syncing\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testMcCoinbase() throws Exception {
        chain3j.mcCoinbase().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_coinbase\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testMcMining() throws Exception {
        chain3j.mcMining().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_mining\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testMcHashrate() throws Exception {
        chain3j.mcHashrate().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_hashrate\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testMcGasPrice() throws Exception {
        chain3j.mcGasPrice().send();
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_gasPrice\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testMcAccounts() throws Exception {
        chain3j.mcAccounts().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_accounts\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testMcBlockNumber() throws Exception {
        chain3j.mcBlockNumber().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_blockNumber\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testMcGetBalance() throws Exception {
        chain3j.mcGetBalance("0x407d73d8a49eeb85d32cf465507dd71d507100c1",
                DefaultBlockParameterName.LATEST).send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"mc_getBalance\","
                        + "\"params\":[\"0x407d73d8a49eeb85d32cf465507dd71d507100c1\",\"latest\"],"
                        + "\"id\":1}");
    }

    @Test
    public void testMcGetStorageAt() throws Exception {
        chain3j.mcGetStorageAt("0x295a70b2de5e3953354a6a8344e616ed314d7251", BigInteger.ZERO,
                DefaultBlockParameterName.LATEST).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_getStorageAt\","
                + "\"params\":[\"0x295a70b2de5e3953354a6a8344e616ed314d7251\",\"0x0\",\"latest\"],"
                + "\"id\":1}");
    }

    @Test
    public void testMcGetTransactionCount() throws Exception {
        chain3j.mcGetTransactionCount("0x407d73d8a49eeb85d32cf465507dd71d507100c1",
                DefaultBlockParameterName.LATEST).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_getTransactionCount\","
                + "\"params\":[\"0x407d73d8a49eeb85d32cf465507dd71d507100c1\",\"latest\"],"
                + "\"id\":1}");
    }

    @Test
    public void testMcGetBlockTransactionCountByHash() throws Exception {
        chain3j.mcGetBlockTransactionCountByHash(
                "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238").send();

        //CHECKSTYLE:OFF
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_getBlockTransactionCountByHash\",\"params\":[\"0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238\"],\"id\":1}");
        //CHECKSTYLE:ON
    }

    @Test
    public void testMcGetBlockTransactionCountByNumber() throws Exception {
        chain3j.mcGetBlockTransactionCountByNumber(
                DefaultBlockParameter.valueOf(Numeric.toBigInt("0xe8"))).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_getBlockTransactionCountByNumber\","
                + "\"params\":[\"0xe8\"],\"id\":1}");
    }

    @Test
    public void testMcGetUncleCountByBlockHash() throws Exception {
        chain3j.mcGetUncleCountByBlockHash(
                "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238").send();

        //CHECKSTYLE:OFF
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_getUncleCountByBlockHash\",\"params\":[\"0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238\"],\"id\":1}");
        //CHECKSTYLE:ON
    }

    @Test
    public void testMcGetUncleCountByBlockNumber() throws Exception {
        chain3j.mcGetUncleCountByBlockNumber(
                DefaultBlockParameter.valueOf(Numeric.toBigInt("0xe8"))).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_getUncleCountByBlockNumber\","
                + "\"params\":[\"0xe8\"],\"id\":1}");
    }

    @Test
    public void testMcGetCode() throws Exception {
        chain3j.mcGetCode("0xa94f5374fce5edbc8e2a8697c15331677e6ebf0b",
                DefaultBlockParameter.valueOf(Numeric.toBigInt("0x2"))).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_getCode\","
                + "\"params\":[\"0xa94f5374fce5edbc8e2a8697c15331677e6ebf0b\",\"0x2\"],\"id\":1}");
    }

    @Test
    public void testMcSign() throws Exception {
        chain3j.mcSign("0x8a3106a3e50576d4b6794a0e74d3bb5f8c9acaab",
                "0xc5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a470").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_sign\","
                + "\"params\":[\"0x8a3106a3e50576d4b6794a0e74d3bb5f8c9acaab\","
                + "\"0xc5d2460186f7233c927e7db2dcc703c0e500b653ca82273b7bfad8045d85a470\"],"
                + "\"id\":1}");
    }

    @Test
    public void testMcSendTransaction() throws Exception {
        chain3j.mcSendTransaction(new Transaction(
                "0xb60e8dd61c5d32be8058bb8eb970870f07233155",
                BigInteger.ONE,
                Numeric.toBigInt("0x9184e72a000"),
                Numeric.toBigInt("0x76c0"),
                "0xb60e8dd61c5d32be8058bb8eb970870f07233155",
                Numeric.toBigInt("0x9184e72a"),
                "0xd46e8dd67c5d32be8d46e8dd67c5d32be8058bb8eb"
                        + "970870f072445675058bb8eb970870f072445675")).send();

        //CHECKSTYLE:OFF
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_sendTransaction\","
        + "\"params\":[{\"from\":\"0xb60e8dd61c5d32be8058bb8eb970870f07233155\","
        + "\"to\":\"0xb60e8dd61c5d32be8058bb8eb970870f07233155\","
        + "\"gas\":\"0x76c0\",\"gasPrice\":\"0x9184e72a000\",\"value\":\"0x9184e72a\","
        + "\"data\":\"0xd46e8dd67c5d32be8d46e8dd67c5d32be8058bb8eb970870f072445675058bb8eb970870f072445675\","
        + "\"nonce\":\"0x1\",\"shardingFlag\":0,\"systemFlag\":0,"
        + "\"via\":\"0x0000000000000000000000000000000000000000\"}],\"id\":<generatedValue>}");
        //CHECKSTYLE:ON
    }

    @Test
    public void testMcSendRawTransaction() throws Exception {
        chain3j.mcSendRawTransaction(
                "0xd46e8dd67c5d32be8d46e8dd67c5d32be8058bb8eb970870f"
                        + "072445675058bb8eb970870f072445675").send();

        //CHECKSTYLE:OFF
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_sendRawTransaction\",\"params\":[\"0xd46e8dd67c5d32be8d46e8dd67c5d32be8058bb8eb970870f072445675058bb8eb970870f072445675\"],\"id\":1}");
        //CHECKSTYLE:ON
    }


    @Test
    public void testMcCall() throws Exception {
        chain3j.mcCall(Transaction.createMcCallTransaction(
                "0xa70e8dd61c5d32be8058bb8eb970870f07233155",
                "0xb60e8dd61c5d32be8058bb8eb970870f07233155",
                        "0x0"),
                DefaultBlockParameter.valueOf("latest")).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_call\","
                + "\"params\":[{\"from\":\"0xa70e8dd61c5d32be8058bb8eb970870f07233155\","
                + "\"to\":\"0xb60e8dd61c5d32be8058bb8eb970870f07233155\",\"data\":\"0x0\","
                + "\"shardingFlag\":0,\"systemFlag\":0,"
                + "\"via\":\"0x0000000000000000000000000000000000000000\"},"
                + "\"latest\"],\"id\":<generatedValue>}");
    }

    @Test
    public void testMcEstimateGas() throws Exception {
        chain3j.mcEstimateGas(
                Transaction.createMcCallTransaction(
                        "0xa70e8dd61c5d32be8058bb8eb970870f07233155",
                        "0x52b93c80364dc2dd4444c146d73b9836bbbb2b3f", "0x0")).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_estimateGas\","
                + "\"params\":[{\"from\":\"0xa70e8dd61c5d32be8058bb8eb970870f07233155\","
                + "\"to\":\"0x52b93c80364dc2dd4444c146d73b9836bbbb2b3f\","
                + "\"data\":\"0x0\",\"shardingFlag\":0,\"systemFlag\":0,"
                + "\"via\":\"0x0000000000000000000000000000000000000000\"}],\"id\":<generatedValue>}");
    }

    @Test
    public void testMcEstimateGasContractCreation() throws Exception {
        chain3j.mcEstimateGas(
                Transaction.createContractTransaction(
                        "0x52b93c80364dc2dd4444c146d73b9836bbbb2b3f", BigInteger.ONE,
                        BigInteger.TEN, "")).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_estimateGas\","
                + "\"params\":[{\"from\":\"0x52b93c80364dc2dd4444c146d73b9836bbbb2b3f\","
                + "\"gasPrice\":\"0xa\",\"data\":\"0x\",\"nonce\":\"0x1\","
                + "\"shardingFlag\":0,\"systemFlag\":0,"
                + "\"via\":\"0x0000000000000000000000000000000000000000\"}],\"id\":1}");
    }

    @Test
    public void testMcGetBlockByHash() throws Exception {
        chain3j.mcGetBlockByHash(
                "0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331", true).send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"mc_getBlockByHash\",\"params\":["
                        + "\"0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331\""
                        + ",true],\"id\":1}");
    }

    @Test
    public void testMcGetBlockByNumber() throws Exception {
        chain3j.mcGetBlockByNumber(
                DefaultBlockParameter.valueOf(Numeric.toBigInt("0x1b4")), true).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_getBlockByNumber\","
                + "\"params\":[\"0x1b4\",true],\"id\":1}");
    }

    @Test
    public void testMcGetTransactionByHash() throws Exception {
        chain3j.mcGetTransactionByHash(
                "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_getTransactionByHash\",\"params\":["
                + "\"0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238\"],"
                + "\"id\":1}");
    }

    @Test
    public void testMcGetTransactionByBlockHashAndIndex() throws Exception {
        chain3j.mcGetTransactionByBlockHashAndIndex(
                "0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331",
                BigInteger.ZERO).send();

        //CHECKSTYLE:OFF
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_getTransactionByBlockHashAndIndex\",\"params\":[\"0xe670ec64341771606e55d6b4ca35a1a6b75ee3d5145a99d05921026d1527331\",\"0x0\"],\"id\":1}");
        //CHECKSTYLE:ON
    }

    @Test
    public void testMcGetTransactionByBlockNumberAndIndex() throws Exception {
        chain3j.mcGetTransactionByBlockNumberAndIndex(
                DefaultBlockParameter.valueOf(Numeric.toBigInt("0x29c")), BigInteger.ZERO).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_getTransactionByBlockNumberAndIndex\","
                + "\"params\":[\"0x29c\",\"0x0\"],\"id\":1}");
    }

    @Test
    public void testMcGetTransactionReceipt() throws Exception {
        chain3j.mcGetTransactionReceipt(
                "0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_getTransactionReceipt\",\"params\":["
                + "\"0xb903239f8543d04b5dc1ba6579132b143087c68db1b2168786408fcbce568238\"],"
                + "\"id\":1}");
    }

    @Test
    public void testMcGetUncleByBlockHashAndIndex() throws Exception {
        chain3j.mcGetUncleByBlockHashAndIndex(
                "0xc6ef2fc5426d6ad6fd9e2a26abeab0aa2411b7ab17f30a99d3cb96aed1d1055b",
                BigInteger.ZERO).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_getUncleByBlockHashAndIndex\","
                + "\"params\":["
                + "\"0xc6ef2fc5426d6ad6fd9e2a26abeab0aa2411b7ab17f30a99d3cb96aed1d1055b\",\"0x0\"],"
                + "\"id\":1}");
    }

    @Test
    public void testMcGetUncleByBlockNumberAndIndex() throws Exception {
        chain3j.mcGetUncleByBlockNumberAndIndex(
                DefaultBlockParameter.valueOf(Numeric.toBigInt("0x29c")), BigInteger.ZERO).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_getUncleByBlockNumberAndIndex\","
                + "\"params\":[\"0x29c\",\"0x0\"],\"id\":1}");
    }

    //     @Test
    //     public void testMcGetCompilers() throws Exception {
    //         chain3j.mcGetCompilers().send();

    //         verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_getCompilers\","
    //                 + "\"params\":[],\"id\":1}");
    //     }

    //     @Test
    //     public void testMcCompileSolidity() throws Exception {
    //         chain3j.ethCompileSolidity(
    //                 "contract test { function multiply(uint a) returns(uint d) {   return a * 7;   } }")
    //                 .send();

    //         verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_compileSolidity\","
    //                 + "\"params\":[\"contract test { function multiply(uint a) returns(uint d) {"
    //                 + "   return a * 7;   } }\"],\"id\":1}");
    //     }

    //     @Test
    //     public void testMcCompileLLL() throws Exception {
    //         chain3j.ethCompileLLL("(returnlll (suicide (caller)))").send();

    //         verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_compileLLL\","
    //                 + "\"params\":[\"(returnlll (suicide (caller)))\"],\"id\":1}");
    //     }

    //     @Test
    //     public void testMcCompileSerpent() throws Exception {
    //         chain3j.ethCompileSerpent("/* some serpent */").send();

    //         verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_compileSerpent\","
    //                 + "\"params\":[\"/* some serpent */\"],\"id\":1}");
    //     }

    @Test
    public void testMcNewFilter() throws Exception {
        McFilter ethFilter = new McFilter()
                .addSingleTopic("0x12341234");

        chain3j.mcNewFilter(ethFilter).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_newFilter\","
                + "\"params\":[{\"topics\":[\"0x12341234\"]}],\"id\":1}");
    }

    @Test
    public void testMcNewBlockFilter() throws Exception {
        chain3j.mcNewBlockFilter().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_newBlockFilter\","
                + "\"params\":[],\"id\":1}");
    }

    @Test
    public void testMcNewPendingTransactionFilter() throws Exception {
        chain3j.mcNewPendingTransactionFilter().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_newPendingTransactionFilter\","
                + "\"params\":[],\"id\":1}");
    }

    @Test
    public void testMcUninstallFilter() throws Exception {
        chain3j.mcUninstallFilter(Numeric.toBigInt("0xb")).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_uninstallFilter\","
                + "\"params\":[\"0x0b\"],\"id\":1}");
    }

    @Test
    public void testMcGetFilterChanges() throws Exception {
        chain3j.mcGetFilterChanges(Numeric.toBigInt("0x16")).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_getFilterChanges\","
                + "\"params\":[\"0x16\"],\"id\":1}");
    }

    @Test
    public void testMcGetFilterLogs() throws Exception {
        chain3j.mcGetFilterLogs(Numeric.toBigInt("0x16")).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_getFilterLogs\","
                + "\"params\":[\"0x16\"],\"id\":1}");
    }

    @Test
    public void testMcGetLogs() throws Exception {
        chain3j.mcGetLogs(new McFilter().addSingleTopic(
                "0x000000000000000000000000a94f5374fce5edbc8e2a8697c15331677e6ebf0b"))
                .send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_getLogs\","
                + "\"params\":[{\"topics\":["
                + "\"0x000000000000000000000000a94f5374fce5edbc8e2a8697c15331677e6ebf0b\"]}],"
                + "\"id\":1}");
    }

    @Test
    public void testMcGetLogsWithNumericBlockRange() throws Exception {
        chain3j.mcGetLogs(new McFilter(
                DefaultBlockParameter.valueOf(Numeric.toBigInt("0xe8")),
                DefaultBlockParameter.valueOf("latest"), ""))
                .send();

        verifyResult(
                "{\"jsonrpc\":\"2.0\",\"method\":\"mc_getLogs\","
                        + "\"params\":[{\"topics\":[],\"fromBlock\":\"0xe8\","
                        + "\"toBlock\":\"latest\",\"address\":[\"\"]}],\"id\":1}");
    }

    @Test
    public void testMcGetWork() throws Exception {
        chain3j.mcGetWork().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_getWork\",\"params\":[],\"id\":1}");
    }

    @Test
    public void testMcSubmitWork() throws Exception {
        chain3j.mcSubmitWork("0x0000000000000001",
                "0x1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef",
                "0xD1FE5700000000000000000000000000D1FE5700000000000000000000000000").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_submitWork\","
                + "\"params\":[\"0x0000000000000001\","
                + "\"0x1234567890abcdef1234567890abcdef1234567890abcdef1234567890abcdef\","
                + "\"0xD1FE5700000000000000000000000000D1FE5700000000000000000000000000\"],"
                + "\"id\":1}");
    }

    @Test
    public void testMcSubmitHashRate() throws Exception {
        chain3j.mcSubmitHashrate(
                "0x0000000000000000000000000000000000000000000000000000000000500000",
                "0x59daa26581d0acd1fce254fb7e85952f4c09d0915afd33d3886cd914bc7d283c").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"mc_submitHashrate\","
                + "\"params\":["
                + "\"0x0000000000000000000000000000000000000000000000000000000000500000\","
                + "\"0x59daa26581d0acd1fce254fb7e85952f4c09d0915afd33d3886cd914bc7d283c\"],"
                + "\"id\":1}");
    }

    //     @Test
    //     public void testDbPutString() throws Exception {
    //         chain3j.dbPutString("testDB", "myKey", "myString").send();

    //         verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"db_putString\","
    //                 + "\"params\":[\"testDB\",\"myKey\",\"myString\"],\"id\":1}");
    //     }

    //     @Test
    //     public void testDbGetString() throws Exception {
    //         chain3j.dbGetString("testDB", "myKey").send();

    //         verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"db_getString\","
    //                 + "\"params\":[\"testDB\",\"myKey\"],\"id\":1}");
    //     }

    //     @Test
    //     public void testDbPutHex() throws Exception {
    //         chain3j.dbPutHex("testDB", "myKey", "0x68656c6c6f20776f726c64").send();

    //         verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"db_putHex\","
    //                 + "\"params\":[\"testDB\",\"myKey\",\"0x68656c6c6f20776f726c64\"],\"id\":1}");
    //     }

    //     @Test
    //     public void testDbGetHex() throws Exception {
    //         chain3j.dbGetHex("testDB", "myKey").send();

    //         verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"db_getHex\","
    //                 + "\"params\":[\"testDB\",\"myKey\"],\"id\":1}");
    //     }

    //     @Test
    //     public void testShhVersion() throws Exception {
    //         chain3j.shhVersion().send();

    //         verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"shh_version\","
    //                 + "\"params\":[],\"id\":1}");
    //     }

    //     @Test
    //     public void testShhPost() throws Exception {
    //         //CHECKSTYLE:OFF
    //         chain3j.shhPost(new ShhPost(
    //                 "0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1",
    //                 "0x3e245533f97284d442460f2998cd41858798ddf04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a0d4d661997d3940272b717b1",
    //                 Arrays.asList("0x776869737065722d636861742d636c69656e74", "0x4d5a695276454c39425154466b61693532"),
    //                 "0x7b2274797065223a226d6",
    //                 Numeric.toBigInt("0x64"),
    //                 Numeric.toBigInt("0x64"))).send();

    //         verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"shh_post\",\"params\":[{\"from\":\"0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1\",\"to\":\"0x3e245533f97284d442460f2998cd41858798ddf04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a0d4d661997d3940272b717b1\",\"topics\":[\"0x776869737065722d636861742d636c69656e74\",\"0x4d5a695276454c39425154466b61693532\"],\"payload\":\"0x7b2274797065223a226d6\",\"priority\":\"0x64\",\"ttl\":\"0x64\"}],\"id\":1}");
    //         //CHECKSTYLE:ON
    //     }

    //     @Test
    //     public void testShhNewIdentity() throws Exception {
    //         chain3j.shhNewIdentity().send();

    //         verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"shh_newIdentity\",\"params\":[],\"id\":1}");
    //     }

    //     @Test
    //     public void testShhHasIdentity() throws Exception {
    //         //CHECKSTYLE:OFF
    //         chain3j.shhHasIdentity("0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1").send();

    //         verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"shh_hasIdentity\",\"params\":[\"0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1\"],\"id\":1}");
    //         //CHECKSTYLE:ON
    //     }

    //     @Test
    //     public void testShhNewGroup() throws Exception {
    //         chain3j.shhNewGroup().send();

    //         verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"shh_newGroup\",\"params\":[],\"id\":1}");
    //     }

    //     @Test
    //     public void testShhAddToGroup() throws Exception {
    //         //CHECKSTYLE:OFF
    //         chain3j.shhAddToGroup("0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1").send();

    //         verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"shh_addToGroup\",\"params\":[\"0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1\"],\"id\":1}");
    //         //CHECKSTYLE:ON
    //     }

    //     @Test
    //     public void testShhNewFilter() throws Exception {
    //         //CHECKSTYLE:OFF
    //         chain3j.shhNewFilter(
    //                 new ShhFilter("0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1")
    //                         .addSingleTopic("0x12341234bf4b564f")).send();

    //         verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"shh_newFilter\",\"params\":[{\"topics\":[\"0x12341234bf4b564f\"],\"to\":\"0x04f96a5e25610293e42a73908e93ccc8c4d4dc0edcfa9fa872f50cb214e08ebf61a03e245533f97284d442460f2998cd41858798ddfd4d661997d3940272b717b1\"}],\"id\":1}");
    //         //CHECKSTYLE:ON
    //     }

    //     @Test
    //     public void testShhUninstallFilter() throws Exception {
    //         chain3j.shhUninstallFilter(Numeric.toBigInt("0x7")).send();

    //         verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"shh_uninstallFilter\","
    //                 + "\"params\":[\"0x07\"],\"id\":1}");
    //     }

    //     @Test
    //     public void testShhGetFilterChanges() throws Exception {
    //         chain3j.shhGetFilterChanges(Numeric.toBigInt("0x7")).send();

    //         verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"shh_getFilterChanges\","
    //                 + "\"params\":[\"0x07\"],\"id\":1}");
    //     }

    //     @Test
    //     public void testShhGetMessages() throws Exception {
    //         chain3j.shhGetMessages(Numeric.toBigInt("0x7")).send();

    //         verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"shh_getMessages\","
    //                 + "\"params\":[\"0x07\"],\"id\":1}");
    //     }
}
