package org.chain3j.protocol.core;

import org.junit.Test;

import org.chain3j.protocol.ResponseTester;
import org.chain3j.protocol.core.methods.response.VnodeAddress;
import org.chain3j.protocol.core.methods.response.VnodeIP;
import org.chain3j.protocol.core.methods.response.VnodeScsService;//bool
import org.chain3j.protocol.core.methods.response.VnodeServiceCfg;
import org.chain3j.protocol.core.methods.response.VnodeShowToPublic;//bool

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * VNODE Protocol Response tests.
 */
public class VnodeResponseTest extends ResponseTester {

    @Test
    public void testBooleanVnodeShowToPublic() {
        buildResponse("{\n"
                + "    \"jsonrpc\":\"2.0\",\n"
                + "    \"id\":99,\n"
                + "    \"result\":true\n"
                + "}");

        VnodeShowToPublic showToPublic =
                deserialiseResponse(VnodeShowToPublic.class);
        assertTrue(showToPublic.getShowToPublic());
    }

    @Test
    public void testVnodeAddress() {
        buildResponse("{\n"
                + "    \"jsonrpc\": \"2.0\",\n"
                + "    \"id\": 99,\n"
                + "    \"result\": \"0x8f0227d45853a50eefd48dd4fec25d5b3fd2295e\"\n"
                + "}");

        VnodeAddress vnodeAddress = deserialiseResponse(VnodeAddress.class);
        assertThat(vnodeAddress.getAddress(),
                is("0x8f0227d45853a50eefd48dd4fec25d5b3fd2295e"));
    }

    @Test
    public void testVnodeIP() {
        buildResponse("{\n"
                + "    \"jsonrpc\": \"2.0\",\n"
                + "    \"id\": 99,\n"
                + "    \"result\":  \"127.0.0.2\"\n"
                + "}\n");

        VnodeIP vnodip = deserialiseResponse(VnodeIP.class);
        assertThat(vnodip.getIP(),
                is("127.0.0.2"));
    }

    @Test
    public void testVnodeServiceCfg() {
        //CHECKSTYLE:OFF
        buildResponse("{\n"
                + "    \"jsonrpc\": \"2.0\",\n"
                + "    \"id\": 99,\n"
                + "    \"result\": \":50068\"\n"
                + "}");
        //CHECKSTYLE:ON

        VnodeServiceCfg vnodeService = deserialiseResponse(VnodeServiceCfg.class);
        //CHECKSTYLE:OFF
        assertThat(vnodeService.getServiceCfg(),
                is(":50068"));
        //CHECKSTYLE:ON
    }

    @Test
    public void testVnodeScsServicet() {
        buildResponse("{\n"
                + "    \"jsonrpc\":\"2.0\",\n"
                + "    \"id\":99,\n"
                + "    \"result\":true\n"
                + "}");

        VnodeScsService scsService =
                deserialiseResponse(VnodeScsService.class);
        assertTrue(scsService.getScsService());
    }
    
}
