package org.chain3j.protocol.admin;

import java.math.BigInteger;

import org.junit.Test;

import org.chain3j.protocol.RequestTester;
import org.chain3j.protocol.core.methods.request.Transaction;
import org.chain3j.protocol.http.HttpService;

public class RequestTest extends RequestTester {
    
    private Admin chain3j;

    @Override
    protected void initChain3Client(HttpService httpService) {
        chain3j = Admin.build(httpService);
    }
    
    @Test
    public void testPersonalListAccounts() throws Exception {
        chain3j.personalListAccounts().send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"personal_listAccounts\","
                + "\"params\":[],\"id\":1}");
    }

    @Test
    public void testPersonalNewAccount() throws Exception {
        chain3j.personalNewAccount("password").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"personal_newAccount\","
                + "\"params\":[\"password\"],\"id\":1}");
    } 

    @Test
    public void testPersonalSendTransaction() throws Exception {
        chain3j.personalSendTransaction(
                new Transaction(
                        "FROM",
                        BigInteger.ONE,
                        BigInteger.TEN,
                        BigInteger.ONE,
                        "TO",
                        BigInteger.ZERO,
                        "DATA"
                ),
                "password"
        ).send();

        //CHECKSTYLE:OFF
        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"personal_sendTransaction\","
        +"\"params\":[{\"from\":\"FROM\",\"to\":\"TO\",\"gas\":\"0x1\","
        +"\"gasPrice\":\"0xa\",\"value\":\"0x0\",\"data\":\"0xDATA\",\"nonce\":\"0x1\","
        +"\"shardingFlag\":0,\"systemFlag\":0,\"via\":\"0x0000000000000000000000000000000000000000\"},"
        +"\"password\"],\"id\":<generatedValue>}");
        //CHECKSTYLE:ON
    }   

    @Test
    public void testPersonalUnlockAccount() throws Exception {
        chain3j.personalUnlockAccount(
                "0xfc390d8a8ddb591b010fda52f4db4945742c3809", "hunter2", BigInteger.ONE).send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"personal_unlockAccount\","
                + "\"params\":[\"0xfc390d8a8ddb591b010fda52f4db4945742c3809\",\"hunter2\",1],"
                + "\"id\":1}");
    }

    @Test
    public void testPersonalUnlockAccountNoDuration() throws Exception {
        chain3j.personalUnlockAccount("0xfc390d8a8ddb591b010fda52f4db4945742c3809", "hunter2").send();

        verifyResult("{\"jsonrpc\":\"2.0\",\"method\":\"personal_unlockAccount\","
                + "\"params\":[\"0xfc390d8a8ddb591b010fda52f4db4945742c3809\",\"hunter2\",null],"
                + "\"id\":1}");
    }
}
