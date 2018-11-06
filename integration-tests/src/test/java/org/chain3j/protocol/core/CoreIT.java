package org.chain3j.protocol.core;

import java.math.BigInteger;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.core.methods.response.Chain3ClientVersion;
import org.chain3j.protocol.core.methods.response.Chain3Sha3;
import org.chain3j.protocol.core.methods.response.McAccounts;
import org.chain3j.protocol.core.methods.response.McBlock;
import org.chain3j.protocol.core.methods.response.McBlockNumber;
import org.chain3j.protocol.core.methods.response.McCall;
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
import org.chain3j.protocol.core.methods.response.McHashrate;
import org.chain3j.protocol.core.methods.response.McLog;
import org.chain3j.protocol.core.methods.response.McMining;
import org.chain3j.protocol.core.methods.response.McProtocolVersion;
import org.chain3j.protocol.core.methods.response.McSendTransaction;
import org.chain3j.protocol.core.methods.response.McSyncing;
import org.chain3j.protocol.core.methods.response.McTransaction;
import org.chain3j.protocol.core.methods.response.McUninstallFilter;
import org.chain3j.protocol.core.methods.response.NetListening;
import org.chain3j.protocol.core.methods.response.NetPeerCount;
import org.chain3j.protocol.core.methods.response.NetVersion;
import org.chain3j.protocol.core.methods.response.Transaction;
import org.chain3j.protocol.core.methods.response.TransactionReceipt;
import org.chain3j.protocol.http.HttpService;

import static junit.framework.TestCase.assertFalse;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * JSON-RPC 2.0 Integration Tests.
 */
public class CoreIT {

    private Chain3j chain3j;

    private IntegrationTestConfig config = new TestnetConfig();

    public CoreIT() { }

    @Before
    public void setUp() {
        this.chain3j = Chain3j.build(new HttpService());
    }

    @Test
    public void testChain3ClientVersion() throws Exception {
        Chain3ClientVersion chain3ClientVersion = chain3j.chain3ClientVersion().send();
        String clientVersion = chain3ClientVersion.getChain3ClientVersion();
        System.out.println("MOAC client version: " + clientVersion);
        assertFalse(clientVersion.isEmpty());
    }

    @Test
    public void testChain3Sha3() throws Exception {
        Chain3Sha3 chain3Sha3 = chain3j.chain3Sha3("0x68656c6c6f20776f726c64").send();
        assertThat(chain3Sha3.getResult(),
                is("0x47173285a8d7341e5e972fc677286384f802f8ef42a5ec5f03bbfa254cb01fad"));
    }

    @Test
    public void testNetVersion() throws Exception {
        NetVersion netVersion = chain3j.netVersion().send();
        assertFalse(netVersion.getNetVersion().isEmpty());
    }

    @Test
    public void testNetListening() throws Exception {
        NetListening netListening = chain3j.netListening().send();
        assertTrue(netListening.isListening());
    }

    @Test
    public void testNetPeerCount() throws Exception {
        NetPeerCount netPeerCount = chain3j.netPeerCount().send();
        assertTrue(netPeerCount.getQuantity().signum() == 1);
    }

    @Test
    public void testMcProtocolVersion() throws Exception {
        McProtocolVersion mcProtocolVersion = chain3j.mcProtocolVersion().send();
        assertFalse(mcProtocolVersion.getProtocolVersion().isEmpty());
    }

    @Test
    public void testMcSyncing() throws Exception {
        McSyncing mcSyncing = chain3j.mcSyncing().send();
        assertNotNull(mcSyncing.getResult());
    }

    @Test
    public void testMcCoinbase() throws Exception {
        McCoinbase mcCoinbase = chain3j.mcCoinbase().send();
        assertNotNull(mcCoinbase.getAddress());
    }

    @Test
    public void testMcMining() throws Exception {
        McMining mcMining = chain3j.mcMining().send();
        assertNotNull(mcMining.getResult());
    }

    @Test
    public void testMcHashrate() throws Exception {
        McHashrate mcHashrate = chain3j.mcHashrate().send();
        assertThat(mcHashrate.getHashrate(), is(BigInteger.ZERO));
    }

    @Test
    public void testMcGasPrice() throws Exception {
        McGasPrice mcGasPrice = chain3j.mcGasPrice().send();
        assertTrue(mcGasPrice.getGasPrice().signum() == 1);
    }

    @Test
    public void testMcAccounts() throws Exception {
        McAccounts mcAccounts = chain3j.mcAccounts().send();
        assertNotNull(mcAccounts.getAccounts());
    }

    @Test
    public void testMcBlockNumber() throws Exception {
        McBlockNumber mcBlockNumber = chain3j.mcBlockNumber().send();
        assertTrue(mcBlockNumber.getBlockNumber().signum() == 1);
    }

    @Test
    public void testMcGetBalance() throws Exception {
        McGetBalance mcGetBalance = chain3j.mcGetBalance(
                config.validAccount(), DefaultBlockParameter.valueOf("latest")).send();
        assertTrue(mcGetBalance.getBalance().signum() == 1);
    }

    @Test
    public void testMcGetStorageAt() throws Exception {
        McGetStorageAt mcGetStorageAt = chain3j.mcGetStorageAt(
                config.validContractAddress(),
                BigInteger.valueOf(0),
                DefaultBlockParameter.valueOf("latest")).send();
        assertThat(mcGetStorageAt.getData(), is(config.validContractAddressPositionZero()));
    }

    @Test
    public void testMcGetTransactionCount() throws Exception {
        McGetTransactionCount mcGetTransactionCount = chain3j.mcGetTransactionCount(
                config.validAccount(),
                DefaultBlockParameter.valueOf("latest")).send();
        assertTrue(mcGetTransactionCount.getTransactionCount().signum() == 1);
    }

    @Test
    public void testMcGetBlockTransactionCountByHash() throws Exception {
        McGetBlockTransactionCountByHash mcGetBlockTransactionCountByHash =
                chain3j.mcGetBlockTransactionCountByHash(
                        config.validBlockHash()).send();
        assertThat(mcGetBlockTransactionCountByHash.getTransactionCount(),
                equalTo(config.validBlockTransactionCount()));
    }

    @Test
    public void testMcGetBlockTransactionCountByNumber() throws Exception {
        McGetBlockTransactionCountByNumber mcGetBlockTransactionCountByNumber =
                chain3j.mcGetBlockTransactionCountByNumber(
                        DefaultBlockParameter.valueOf(config.validBlock())).send();
        assertThat(mcGetBlockTransactionCountByNumber.getTransactionCount(),
                equalTo(config.validBlockTransactionCount()));
    }

    @Test
    public void testMcGetUncleCountByBlockHash() throws Exception {
        McGetUncleCountByBlockHash mcGetUncleCountByBlockHash =
                chain3j.mcGetUncleCountByBlockHash(config.validBlockHash()).send();
        assertThat(mcGetUncleCountByBlockHash.getUncleCount(),
                equalTo(config.validBlockUncleCount()));
    }

    @Test
    public void testMcGetUncleCountByBlockNumber() throws Exception {
        McGetUncleCountByBlockNumber mcGetUncleCountByBlockNumber =
                chain3j.mcGetUncleCountByBlockNumber(
                        DefaultBlockParameter.valueOf("latest")).send();
        assertThat(mcGetUncleCountByBlockNumber.getUncleCount(),
                equalTo(config.validBlockUncleCount()));
    }

    @Test
    public void testMcGetCode() throws Exception {
        McGetCode mcGetCode = chain3j.mcGetCode(config.validContractAddress(),
                DefaultBlockParameter.valueOf(config.validBlock())).send();
        assertThat(mcGetCode.getCode(), is(config.validContractCode()));
    }

    @Ignore  // TODO: Once account unlock functionality is available
    @Test
    public void testMcSign() throws Exception {
        // McSign mcSign = chain3j.mcSign();
    }

    @Ignore  // TODO: Once account unlock functionality is available
    @Test
    public void testMcSendTransaction() throws Exception {
        McSendTransaction mcSendTransaction = chain3j.mcSendTransaction(
                config.buildTransaction()).send();
        assertFalse(mcSendTransaction.getTransactionHash().isEmpty());
    }

    @Ignore  // TODO: Once account unlock functionality is available
    @Test
    public void testMcSendRawTransaction() throws Exception {

    }

    @Test
    public void testMcCall() throws Exception {
        McCall mcCall = chain3j.mcCall(config.buildTransaction(),
                DefaultBlockParameter.valueOf("latest")).send();

        assertThat(DefaultBlockParameterName.LATEST.getValue(), is("latest"));
        assertThat(mcCall.getValue(), is("0x"));
    }

    @Test
    public void testMcEstimateGas() throws Exception {
        McEstimateGas mcEstimateGas = chain3j.mcEstimateGas(config.buildTransaction())
                .send();
        assertTrue(mcEstimateGas.getAmountUsed().signum() == 1);
    }

    @Test
    public void testMcGetBlockByHashReturnHashObjects() throws Exception {
        McBlock mcBlock = chain3j.mcGetBlockByHash(config.validBlockHash(), false)
                .send();

        McBlock.Block block = mcBlock.getBlock();
        assertNotNull(mcBlock.getBlock());
        assertThat(block.getNumber(), equalTo(config.validBlock()));
        assertThat(block.getTransactions().size(),
                is(config.validBlockTransactionCount().intValue()));
    }

    @Test
    public void testMcGetBlockByHashReturnFullTransactionObjects() throws Exception {
        McBlock mcBlock = chain3j.mcGetBlockByHash(config.validBlockHash(), true)
                .send();

        McBlock.Block block = mcBlock.getBlock();
        assertNotNull(mcBlock.getBlock());
        assertThat(block.getNumber(), equalTo(config.validBlock()));
        assertThat(block.getTransactions().size(),
                equalTo(config.validBlockTransactionCount().intValue()));
    }

    @Test
    public void testMcGetBlockByNumberReturnHashObjects() throws Exception {
        McBlock mcBlock = chain3j.mcGetBlockByNumber(
                DefaultBlockParameter.valueOf(config.validBlock()), false).send();

        McBlock.Block block = mcBlock.getBlock();
        assertNotNull(mcBlock.getBlock());
        assertThat(block.getNumber(), equalTo(config.validBlock()));
        assertThat(block.getTransactions().size(),
                equalTo(config.validBlockTransactionCount().intValue()));
    }

    @Test
    public void testMcGetBlockByNumberReturnTransactionObjects() throws Exception {
        McBlock mcBlock = chain3j.mcGetBlockByNumber(
                DefaultBlockParameter.valueOf(config.validBlock()), true).send();

        McBlock.Block block = mcBlock.getBlock();
        assertNotNull(mcBlock.getBlock());
        assertThat(block.getNumber(), equalTo(config.validBlock()));
        assertThat(block.getTransactions().size(),
                equalTo(config.validBlockTransactionCount().intValue()));
    }

    @Test
    public void testMcGetTransactionByHash() throws Exception {
        McTransaction mcTransaction = chain3j.mcGetTransactionByHash(
                config.validTransactionHash()).send();
        assertTrue(mcTransaction.getTransaction().isPresent());
        Transaction transaction = mcTransaction.getTransaction().get();
        assertThat(transaction.getBlockHash(), is(config.validBlockHash()));
    }

    @Test
    public void testMcGetTransactionByBlockHashAndIndex() throws Exception {
        BigInteger index = BigInteger.ONE;

        McTransaction mcTransaction = chain3j.mcGetTransactionByBlockHashAndIndex(
                config.validBlockHash(), index).send();
        assertTrue(mcTransaction.getTransaction().isPresent());
        Transaction transaction = mcTransaction.getTransaction().get();
        assertThat(transaction.getBlockHash(), is(config.validBlockHash()));
        assertThat(transaction.getTransactionIndex(), equalTo(index));
    }

    @Test
    public void testMcGetTransactionByBlockNumberAndIndex() throws Exception {
        BigInteger index = BigInteger.ONE;

        McTransaction mcTransaction = chain3j.mcGetTransactionByBlockNumberAndIndex(
                DefaultBlockParameter.valueOf(config.validBlock()), index).send();
        assertTrue(mcTransaction.getTransaction().isPresent());
        Transaction transaction = mcTransaction.getTransaction().get();
        assertThat(transaction.getBlockHash(), is(config.validBlockHash()));
        assertThat(transaction.getTransactionIndex(), equalTo(index));
    }

    @Test
    public void testMcGetTransactionReceipt() throws Exception {
        McGetTransactionReceipt mcGetTransactionReceipt = chain3j.mcGetTransactionReceipt(
                config.validTransactionHash()).send();
        assertTrue(mcGetTransactionReceipt.getTransactionReceipt().isPresent());
        TransactionReceipt transactionReceipt =
                mcGetTransactionReceipt.getTransactionReceipt().get();
        assertThat(transactionReceipt.getTransactionHash(), is(config.validTransactionHash()));
    }

    @Test
    public void testMcGetUncleByBlockHashAndIndex() throws Exception {
        McBlock mcBlock = chain3j.mcGetUncleByBlockHashAndIndex(
                config.validUncleBlockHash(), BigInteger.ZERO).send();
        assertNotNull(mcBlock.getBlock());
    }

    @Test
    public void testMcGetUncleByBlockNumberAndIndex() throws Exception {
        McBlock mcBlock = chain3j.mcGetUncleByBlockNumberAndIndex(
                DefaultBlockParameter.valueOf(config.validUncleBlock()), BigInteger.ZERO)
                .send();
        assertNotNull(mcBlock.getBlock());
    }

    @Test
    public void testMcGetCompilers() throws Exception {
        McGetCompilers mcGetCompilers = chain3j.mcGetCompilers().send();
        assertNotNull(mcGetCompilers.getCompilers());
    }

    // @Ignore  // The mmcod mc_compileLLL does not exist/is not available
    // @Test
    // public void testMcCompileLLL() throws Exception {
    //     McCompileLLL mcCompileLLL = chain3j.mcCompileLLL(
    //             "(returnlll (suicide (caller)))").send();
    //     assertFalse(mcCompileLLL.getCompiledSourceCode().isEmpty());
    // }

    // @Test
    // public void testMcCompileSolidity() throws Exception {
    //     String sourceCode = "pragma solidity ^0.4.0;"
    //             + "\ncontract test { function multiply(uint a) returns(uint d) {"
    //             + "   return a * 7;   } }"
    //             + "\ncontract test2 { function multiply2(uint a) returns(uint d) {"
    //             + "   return a * 7;   } }";
    //     McCompileSolidity mcCompileSolidity = chain3j.mcCompileSolidity(sourceCode)
    //             .send();
    //     assertNotNull(mcCompileSolidity.getCompiledSolidity());
    //     assertThat(
    //             mcCompileSolidity.getCompiledSolidity().get("test2").getInfo().getSource(),
    //             is(sourceCode));
    // }

    // @Ignore  // The mmcod mc_compileSerpent does not exist/is not available
    // @Test
    // public void testMcCompileSerpent() throws Exception {
    //     McCompileSerpent mcCompileSerpent = chain3j.mcCompileSerpent(
    //             "/* some serpent */").send();
    //     assertFalse(mcCompileSerpent.getCompiledSourceCode().isEmpty());
    // }

    @Test
    public void testFiltersByFilterId() throws Exception {
        org.chain3j.protocol.core.methods.request.McFilter mcFilter =
                new org.chain3j.protocol.core.methods.request.McFilter(
                DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST,
                config.validContractAddress());

        String eventSignature = config.encodedEvent();
        mcFilter.addSingleTopic(eventSignature);

        // mc_newFilter
        McFilter mcNewFilter = chain3j.mcNewFilter(mcFilter).send();
        BigInteger filterId = mcNewFilter.getFilterId();

        // mc_getFilterLogs
        McLog mcFilterLogs = chain3j.mcGetFilterLogs(filterId).send();
        List<McLog.LogResult> filterLogs = mcFilterLogs.getLogs();
        assertFalse(filterLogs.isEmpty());

        // mc_getFilterChanges - nothing will have changed in this interval
        McLog mcLog = chain3j.mcGetFilterChanges(filterId).send();
        assertTrue(mcLog.getLogs().isEmpty());

        // mc_uninstallFilter
        McUninstallFilter mcUninstallFilter = chain3j.mcUninstallFilter(filterId).send();
        assertTrue(mcUninstallFilter.isUninstalled());
    }

    @Test
    public void testMcNewBlockFilter() throws Exception {
        McFilter mcNewBlockFilter = chain3j.mcNewBlockFilter().send();
        assertNotNull(mcNewBlockFilter.getFilterId());
    }

    @Test
    public void testMcNewPendingTransactionFilter() throws Exception {
        McFilter mcNewPendingTransactionFilter =
                chain3j.mcNewPendingTransactionFilter().send();
        assertNotNull(mcNewPendingTransactionFilter.getFilterId());
    }

    @Test
    public void testMcGetLogs() throws Exception {
        org.chain3j.protocol.core.methods.request.McFilter mcFilter =
                new org.chain3j.protocol.core.methods.request.McFilter(
                DefaultBlockParameterName.EARLIEST,
                DefaultBlockParameterName.LATEST,
                config.validContractAddress()
        );

        mcFilter.addSingleTopic(config.encodedEvent());

        McLog mcLog = chain3j.mcGetLogs(mcFilter).send();
        List<McLog.LogResult> logs = mcLog.getLogs();
        assertFalse(logs.isEmpty());
    }

    // @Test
    // public void testMcGetWork() throws Exception {
    //     McGetWork mcGetWork = requestFactory.mcGetWork();
    //     assertNotNull(mcGetWork.getResult());
    // }

    @Test
    public void testMcSubmitWork() throws Exception {

    }

    @Test
    public void testMcSubmitHashrate() throws Exception {
    
    }

    // @Test
    // public void testDbPutString() throws Exception {
    
    // }

    // @Test
    // public void testDbGetString() throws Exception {
    
    // }

    // @Test
    // public void testDbPutHex() throws Exception {
    
    // }

    // @Test
    // public void testDbGetHex() throws Exception {
    
    // }

    // @Test
    // public void testShhPost() throws Exception {
    
    // }

    // @Ignore // The mmcod shh_version does not exist/is not available
    // @Test
    // public void testShhVersion() throws Exception {
    //     ShhVersion shhVersion = chain3j.shhVersion().send();
    //     assertNotNull(shhVersion.getVersion());
    // }

    // @Ignore  // The mmcod shh_newIdentity does not exist/is not available
    // @Test
    // public void testShhNewIdentity() throws Exception {
    //     ShhNewIdentity shhNewIdentity = chain3j.shhNewIdentity().send();
    //     assertNotNull(shhNewIdentity.getAddress());
    // }

    // @Test
    // public void testShhHasIdentity() throws Exception {
    
    // }

    // @Ignore  // The mmcod shh_newIdentity does not exist/is not available
    // @Test
    // public void testShhNewGroup() throws Exception {
    //     ShhNewGroup shhNewGroup = chain3j.shhNewGroup().send();
    //     assertNotNull(shhNewGroup.getAddress());
    // }

    // @Ignore  // The mmcod shh_addToGroup does not exist/is not available
    // @Test
    // public void testShhAddToGroup() throws Exception {

    // }

    // @Test
    // public void testShhNewFilter() throws Exception {
    
    // }

    // @Test
    // public void testShhUninstallFilter() throws Exception {
    
    // }

    // @Test
    // public void testShhGetFilterChanges() throws Exception {
    
    // }

    // @Test
    // public void testShhGetMessages() throws Exception {
    
    // }
}
