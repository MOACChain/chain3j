package org.chain3j.ens;

import java.io.IOException;
import java.math.BigInteger;

import org.junit.Before;
import org.junit.Test;

import org.chain3j.abi.TypeDecoder;
import org.chain3j.abi.TypeEncoder;
import org.chain3j.abi.datatypes.Utf8String;
import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.Chain3jService;
import org.chain3j.protocol.core.JsonRpc2_0Chain3j;
import org.chain3j.protocol.core.Request;
import org.chain3j.protocol.core.methods.response.McBlock;
import org.chain3j.protocol.core.methods.response.McCall;
import org.chain3j.protocol.core.methods.response.McSyncing;
import org.chain3j.protocol.core.methods.response.NetVersion;
import org.chain3j.protocol.http.HttpService;
import org.chain3j.tx.ChainId;
import org.chain3j.utils.Numeric;

import static org.chain3j.ens.EnsResolver.DEFAULT_SYNC_THRESHOLD;
import static org.chain3j.ens.EnsResolver.isValidEnsName;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EnsResolverTest {

    private Chain3j chain3j;
    private Chain3jService chain3jService;
    private EnsResolver ensResolver;

    @Before
    public void setUp() {
        chain3jService = mock(Chain3jService.class);
        chain3j = Chain3j.build(chain3jService);
        ensResolver = new EnsResolver(chain3j);
    }

    @Test
    public void testResolve() throws Exception {
        configureSyncing(false);
        configureLatestBlock(System.currentTimeMillis() / 1000);  // block timestamp is in seconds

        NetVersion netVersion = new NetVersion();
        netVersion.setResult(Byte.toString(ChainId.MAINNET));

        String resolverAddress =
                "0x0000000000000000000000004c641fb9bad9b60ef180c31f56051ce826d21a9a";
        String contractAddress =
                "0x00000000000000000000000019e03255f667bdfd50a32722df860b1eeaf4d635";

        McCall resolverAddressResponse = new McCall();
        resolverAddressResponse.setResult(resolverAddress);

        McCall contractAddressResponse = new McCall();
        contractAddressResponse.setResult(contractAddress);

        when(chain3jService.send(any(Request.class), eq(NetVersion.class)))
                .thenReturn(netVersion);
        when(chain3jService.send(any(Request.class), eq(McCall.class)))
                .thenReturn(resolverAddressResponse);
        when(chain3jService.send(any(Request.class), eq(McCall.class)))
                .thenReturn(contractAddressResponse);

        assertThat(ensResolver.resolve("chain3j.eth"),
                is("0x19e03255f667bdfd50a32722df860b1eeaf4d635"));
    }

    @Test
    public void testReverseResolve() throws Exception {
        configureSyncing(false);
        configureLatestBlock(System.currentTimeMillis() / 1000);  // block timestamp is in seconds

        NetVersion netVersion = new NetVersion();
        netVersion.setResult(Byte.toString(ChainId.MAINNET));

        String resolverAddress =
                "0x0000000000000000000000004c641fb9bad9b60ef180c31f56051ce826d21a9a";
        String contractName =
                "0x0000000000000000000000000000000000000000000000000000000000000020"
                + TypeEncoder.encode(new Utf8String("chain3j.eth"));
        System.err.println(contractName);

        McCall resolverAddressResponse = new McCall();
        resolverAddressResponse.setResult(resolverAddress);

        McCall contractNameResponse = new McCall();
        contractNameResponse.setResult(contractName);

        when(chain3jService.send(any(Request.class), eq(NetVersion.class)))
                .thenReturn(netVersion);
        when(chain3jService.send(any(Request.class), eq(McCall.class)))
                .thenReturn(resolverAddressResponse);
        when(chain3jService.send(any(Request.class), eq(McCall.class)))
                .thenReturn(contractNameResponse);

        assertThat(ensResolver.reverseResolve("0x19e03255f667bdfd50a32722df860b1eeaf4d635"),
                is("chain3j.eth"));
    }

    @Test
    public void testIsSyncedSyncing() throws Exception {
        configureSyncing(true);

        assertFalse(ensResolver.isSynced());
    }

    @Test
    public void testIsSyncedFullySynced() throws Exception {
        configureSyncing(false);
        configureLatestBlock(System.currentTimeMillis() / 1000);  // block timestamp is in seconds

        assertTrue(ensResolver.isSynced());
    }

    @Test
    public void testIsSyncedBelowThreshold() throws Exception {
        configureSyncing(false);
        configureLatestBlock((System.currentTimeMillis() / 1000) - DEFAULT_SYNC_THRESHOLD);

        assertFalse(ensResolver.isSynced());
    }

    private void configureSyncing(boolean isSyncing) throws IOException {
        McSyncing ethSyncing = new McSyncing();
        McSyncing.Result result = new McSyncing.Result();
        result.setSyncing(isSyncing);
        ethSyncing.setResult(result);

        when(chain3jService.send(any(Request.class), eq(McSyncing.class)))
                .thenReturn(ethSyncing);
    }

    private void configureLatestBlock(long timestamp) throws IOException {
        McBlock.Block block = new McBlock.Block();
        block.setTimestamp(Numeric.encodeQuantity(BigInteger.valueOf(timestamp)));
        McBlock ethBlock = new McBlock();
        ethBlock.setResult(block);

        when(chain3jService.send(any(Request.class), eq(McBlock.class)))
                .thenReturn(ethBlock);
    }

    @Test
    public void testIsEnsName() {
        assertTrue(isValidEnsName("eth"));
        assertTrue(isValidEnsName("web3.eth"));
        assertTrue(isValidEnsName("0x19e03255f667bdfd50a32722df860b1eeaf4d635.eth"));

        assertFalse(isValidEnsName("0x19e03255f667bdfd50a32722df860b1eeaf4d635"));
        assertFalse(isValidEnsName("19e03255f667bdfd50a32722df860b1eeaf4d635"));

        assertTrue(isValidEnsName(""));
    }
}
