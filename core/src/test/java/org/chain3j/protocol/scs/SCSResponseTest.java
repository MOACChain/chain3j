package org.chain3j.protocol.scs;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import org.chain3j.protocol.scs.methods.response.*;
import org.junit.Test;

import org.chain3j.protocol.ResponseTester;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * MOAC SCS Protocol Response tests.
 * getScsId
 * getMicroChainList
 * getNonce
 * getBlockNumber
 * getBlock
 * getBalance
 * setDappAbi
 */
public class SCSResponseTest extends ResponseTester {
    @Test
    public void testGetExchangeInfo(){
        buildResponse("{\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"id\": 100,\n" +
                "  \"result\": {\n" +
                "    \"DepositingRecordCount\": 0,\n" +
                "    \"DepositingRecords\": null,\n" +
                "    \"WithdrawingRecordCount\": 0,\n" +
                "    \"WithdrawingRecords\": null,\n" +
                "    \"microchain\": \"0x2e4694875de2a7da9c3186a176c52760d58694e4\",\n" +
                "    \"scsid\": \"0x50c15fafb95968132d1a6ee3617e99cca1fcf059\"\n" +
                "  }\n" +
                "}");
        ScsGetExchangeInfo exchangeInfo = deserialiseResponse(ScsGetExchangeInfo.class);
//        assertThat(exchangeInfo.getExchange().getDepositingRecords(),equalTo(null));
        assertThat(exchangeInfo.getExchange().getDepositingRecordCount(),equalTo(0));
    }

    @Test
    public void testGetReceiptByHash(){
        buildResponse("{\n" +
                "  \"id\": 101,\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"result\": {\n" +
                "    \"contractAddress\": \"0x0a674edac2ccd47ae2a3197ea3163aa81087fbd1\",\n" +
                "    \"failed\": false,\n" +
                "    \"logs\": [\n" +
                "      {\n" +
                "        \"address\": \"0x2328537bc943ab1a89fe94a4b562ee7a7b013634\",\n" +
                "        \"topics\": [\n" +
                "          \"0xddf252ad1be2c89b69c2b068fc378daa952ba7f163c4a11628f55a4df523b3ef\",\n" +
                "          \"0x000000000000000000000000a8863fc8ce3816411378685223c03daae9770ebb\",\n" +
                "          \"0x0000000000000000000000007312f4b8a4457a36827f185325fd6b66a3f8bb8b\"\n" +
                "        ],\n" +
                "        \"data\": \"AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAGQ=\",\n" +
                "        \"blockNumber\": 0,\n" +
                "        \"transactionHash\": \"0x67bfaa5a704e77a31d5e7eb866f8c662fa8313a7882d13d0d23e377cd66d2a69\",\n" +
                "        \"transactionIndex\": 0,\n" +
                "        \"blockHash\": \"0x78f092ca81a891ad6c467caa2881d00d8e19c8925ddfd71d793294fbfc5f15fe\",\n" +
                "        \"logIndex\": 0,\n" +
                "        \"removed\": false\n" +
                "      }\n" +
                "    ],\n" +
                "    \"logsBloom\": \"0x00000000000000000000000000000000080000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000008000000000008000000000000000000000010000000000000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000000000000000400000000000000000000000000000800000000000080000000000000000000000000002000000000000000000000000000000000000080100002000000000000000000000000000000000000000000000000000000000000000000000000000\",\n" +
                "    \"status\": \"0x1\",\n" +
                "    \"transactionHash\": \"0x67bfaa5a704e77a31d5e7eb866f8c662fa8313a7882d13d0d23e377cd66d2a69\"\n" +
                "  }\n" +
                "}");
        ScsGetReceiptByHash receiptByHash = deserialiseResponse(ScsGetReceiptByHash.class);
        assertThat(receiptByHash.getReceiptByHash().getContractAddress(),equalTo("0x0a674edac2ccd47ae2a3197ea3163aa81087fbd1"));
    }

    @Test
    public void testGetSCSId(){
        buildResponse("{\n" +
                "  \"id\":101,\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"result\": \"0x9d711986ccc8c89db2dfaf0894acadeb5a383ee8\"\n" +
                "}");
        ScsGetSCSId scsId = deserialiseResponse(ScsGetSCSId.class);
        assertThat(scsId.getSCSId(),equalTo("0x9d711986ccc8c89db2dfaf0894acadeb5a383ee8"));
    }

    @Test
    public void testGetBlockList(){
        buildResponse("{\"jsonrpc\":\"2.0\",\"id\":101,\"result\":{\"blockList\":[{\"extraData\":\"0x\",\"hash\":\"0x56075838e0fffe6576add14783b957239d4f3c57989bc3a7b7728a3b57eb305a\",\"miner\":\"0xecd1e094ee13d0b47b72f5c940c17bd0c7630326\",\"number\":\"0x370\",\"parentHash\":\"0x56352a3a8bd0901608041115817204cbce943606e406d233d7d0359f449bd4c2\",\"receiptsRoot\":\"0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421\",\"stateRoot\":\"0xde741a2f6b4a3c865e8f6fc9ba11eadaa1fa04c61d660bcdf0fa1195029699f6\",\"timestamp\":\"0x5bfb7c1c\",\"transactions\":[],\"transactionsRoot\":\"0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421\"},{\"extraData\":\"0x\",\"hash\":\"0xbc3f5791ec039cba99c37310a4f30a68030dd2ab79efb47d23fd9ac5343f54e5\",\"miner\":\"0xecd1e094ee13d0b47b72f5c940c17bd0c7630326\",\"number\":\"0x371\",\"parentHash\":\"0x56075838e0fffe6576add14783b957239d4f3c57989bc3a7b7728a3b57eb305a\",\"receiptsRoot\":\"0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421\",\"stateRoot\":\"0xde741a2f6b4a3c865e8f6fc9ba11eadaa1fa04c61d660bcdf0fa1195029699f6\",\"timestamp\":\"0x5bfb7c3a\",\"transactions\":[],\"transactionsRoot\":\"0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421\"},{\"extraData\":\"0x\",\"hash\":\"0x601be17c47cb4684053457d1d5f70a6dbeb853b27cda08d160555f857f2da33b\",\"miner\":\"0xecd1e094ee13d0b47b72f5c940c17bd0c7630326\",\"number\":\"0x372\",\"parentHash\":\"0xbc3f5791ec039cba99c37310a4f30a68030dd2ab79efb47d23fd9ac5343f54e5\",\"receiptsRoot\":\"0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421\",\"stateRoot\":\"0xde741a2f6b4a3c865e8f6fc9ba11eadaa1fa04c61d660bcdf0fa1195029699f6\",\"timestamp\":\"0x5bfb7c58\",\"transactions\":[],\"transactionsRoot\":\"0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421\"},{\"extraData\":\"0x\",\"hash\":\"0x8a0bea649bcdbd2b525690ff485e56d5a83443e9013fcdccd1a0adee56ba4092\",\"miner\":\"0xecd1e094ee13d0b47b72f5c940c17bd0c7630326\",\"number\":\"0x373\",\"parentHash\":\"0x601be17c47cb4684053457d1d5f70a6dbeb853b27cda08d160555f857f2da33b\",\"receiptsRoot\":\"0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421\",\"stateRoot\":\"0xde741a2f6b4a3c865e8f6fc9ba11eadaa1fa04c61d660bcdf0fa1195029699f6\",\"timestamp\":\"0x5bfb7c76\",\"transactions\":[],\"transactionsRoot\":\"0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421\"}],\"endBlk\":\"0x373\",\"microchainAddress\":\"0x7D0CbA876cB9Da5fa310A54d29F4687f5dd93fD7\",\"startBlk\":\"0x370\"}}");
        ScsGetBlockList blockList =  deserialiseResponse(ScsGetBlockList.class);
        assertThat(blockList.getBlockList().getMicrochainAddress(),equalTo("0x7D0CbA876cB9Da5fa310A54d29F4687f5dd93fD7"));
        List<ScsGetBlock.Block> blocks = blockList.getBlockList().getBlockList();
        assertThat(blocks.get(0).getExtraDataRaw(),equalTo("0x"));
    }

    @Test
    public void testGetBlock(){
        buildResponse("{\"jsonrpc\":\"2.0\",\"id\":101,\"result\":{\"extraData\":\"0x\",\"hash\":\"0xc80cbe08bc266b1236f22a8d0b310faae3135961dbef6ad8b6ad4e8cd9537309\",\"number\":\"0x1\",\"parentHash\":\"0x0000000000000000000000000000000000000000000000000000000000000000\",\"receiptsRoot\":\"0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421\",\"stateRoot\":\"0x1a065207da60d8e7a44db2f3b5ed9d3e81052a3059e4108c84701d0bf6a62292\",\"timestamp\":\"0x0\",\"transactions\":[],\"transactionsRoot\":\"0x56e81f171bcc55a6ff8345e692c0f86e5b48e01b996cadc001622fb5e363b421\"}}");
        ScsGetBlock getBlock = deserialiseResponse(ScsGetBlock.class);
        assertThat(getBlock.getBlock().getHash(),equalTo("0xc80cbe08bc266b1236f22a8d0b310faae3135961dbef6ad8b6ad4e8cd9537309"));
        assertThat(getBlock.getBlock().getExtraDataRaw(),equalTo("0x"));
        assertThat(getBlock.getBlock().getHash(),equalTo("0xc80cbe08bc266b1236f22a8d0b310faae3135961dbef6ad8b6ad4e8cd9537309"));
        assertThat(getBlock.getBlock().getTimestamp(),equalTo("0x0"));
        assertThat(getBlock.getBlock().getTransactions().isEmpty(),equalTo(true));
    }

    @Test
    public void testDirectCall(){
        buildResponse("{\n" +
                "  \"id\":101,\n" +
                "  \"jsonrpc\": \"2.0\",\n" +
                "  \"result\": \"0x\"\n" +
                "}");
        ScsDirectCall directCall = deserialiseResponse(ScsDirectCall.class);
        assertThat(directCall.directCall(),equalTo("0x"));
    }

    @Test
    public void testGetDappState() {
        buildResponse("{\n"
                + "    \"jsonrpc\":\"2.0\",\n"
                + "    \"id\":99,\n"
                + "    \"result\":true\n"
                + "}");

        ScsGetDappState dappState =
                deserialiseResponse(ScsGetDappState.class);
        assertTrue(dappState.getResult());
    }

    @Test
    public void testGetMicroChainList() {
        buildResponse("{\n"
                + "    \"jsonrpc\": \"2.0\",\n"
                + "    \"id\": 101,\n"
                + "    \"result\": [\n"
                + "        \"0x7bf87721a96849d168de02fd6ea5986a3a147383\",\n"
                + "        \"0xca807a90fd64deed760fb98bf0869b475c469348\"\n"
                + "    ]\n"
                + "}");

        ScsGetMicroChainList microChainList = deserialiseResponse(ScsGetMicroChainList.class);
        assertThat(microChainList.getResult(),
                equalTo(Arrays.asList(
                        "0x7bf87721a96849d168de02fd6ea5986a3a147383",
                        "0xca807a90fd64deed760fb98bf0869b475c469348"
                )));
    }

    @Test
    public void testScsGetBlockNumber() {
        buildResponse(
                "{\n"
                        + "  \"id\":83,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": \"0x4b7\"\n"
                        + "}"
        );

        ScsGetBlockNumber scsBlockNumber = deserialiseResponse(ScsGetBlockNumber.class);
        assertThat(scsBlockNumber.getBlockNumber(), equalTo(BigInteger.valueOf(1207L)));
    }

    @Test
    public void testScsGetBalance() {
        buildResponse(
                "{\n"
                        + "  \"id\":101,\n"
                        + "  \"jsonrpc\": \"2.0\",\n"
                        + "  \"result\": \"0x234c8a3397aab58\"\n"
                        + "}"
        );
//        buildResponse(
//                "{\n"
//                        + "  \"id\":100,\n"
//                        + "  \"jsonrpc\": \"2.0\",\n"
//                        + "  \"result\": \"0x234c8a3397aab58\"\n"
//                        + "}"
//        );

        ScsGetBalance scsGetBalance = deserialiseResponse(ScsGetBalance.class);
        assertThat(scsGetBalance.getBalance(), equalTo(BigInteger.valueOf(158972490234375000L)));
    }

}
