package org.chain3j.protocol.scs;

import java.math.BigInteger;
import java.util.Arrays;

import org.junit.Test;

import org.chain3j.protocol.ResponseTester;
import org.chain3j.protocol.scs.methods.response.ScsGetBalance;
import org.chain3j.protocol.scs.methods.response.ScsGetBlockNumber;
import org.chain3j.protocol.scs.methods.response.ScsGetDappState;
import org.chain3j.protocol.scs.methods.response.ScsGetMicroChainInfo;
import org.chain3j.protocol.scs.methods.response.ScsGetMicroChainList;
import org.chain3j.protocol.scs.methods.response.ScsMicroChainInfo;

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
