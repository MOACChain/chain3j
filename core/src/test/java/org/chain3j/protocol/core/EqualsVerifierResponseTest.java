package org.chain3j.protocol.core;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;
import org.junit.Test;

import org.chain3j.protocol.core.methods.response.AbiDefinition;
import org.chain3j.protocol.core.methods.response.Log;
import org.chain3j.protocol.core.methods.response.McBlock;
import org.chain3j.protocol.core.methods.response.McCompileSolidity;
import org.chain3j.protocol.core.methods.response.McLog;
import org.chain3j.protocol.core.methods.response.McSyncing;
import org.chain3j.protocol.core.methods.response.ShhMessages;
import org.chain3j.protocol.core.methods.response.Transaction;
import org.chain3j.protocol.core.methods.response.TransactionReceipt;

public class EqualsVerifierResponseTest {

    @Test
    public void testBlock() {
        EqualsVerifier.forClass(McBlock.Block.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    @Test
    public void testTransaction() {
        EqualsVerifier.forClass(Transaction.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    @Test
    public void testTransactionReceipt() {
        EqualsVerifier.forClass(TransactionReceipt.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    @Test
    public void testLog() {
        EqualsVerifier.forClass(Log.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    @Test
    public void testSshMessage() {
        EqualsVerifier.forClass(ShhMessages.SshMessage.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    // @Test
    // public void testSolidityInfo() {
    //     EqualsVerifier.forClass(EthCompileSolidity.SolidityInfo.class)
    //             .suppress(Warning.NONFINAL_FIELDS)
    //             .suppress(Warning.STRICT_INHERITANCE)
    //             .verify();
    // }

    @Test
    public void testSyncing() {
        EqualsVerifier.forClass(McSyncing.Syncing.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    @Test
    public void testAbiDefinition() {
        EqualsVerifier.forClass(AbiDefinition.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    @Test
    public void testAbiDefinitionNamedType() {
        EqualsVerifier.forClass(AbiDefinition.NamedType.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    @Test
    public void testHash() {
        EqualsVerifier.forClass(McLog.Hash.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    // @Test
    // public void testCode() {
    //     EqualsVerifier.forClass(EthCompileSolidity.Code.class)
    //             .suppress(Warning.NONFINAL_FIELDS)
    //             .suppress(Warning.STRICT_INHERITANCE)
    //             .verify();
    // }

    @Test
    public void testTransactionHash() {
        EqualsVerifier.forClass(McBlock.TransactionHash.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }

    // @Test
    // public void testCompiledSolidityCode() {
    //     EqualsVerifier.forClass(EthCompileSolidity.Code.class)
    //             .suppress(Warning.NONFINAL_FIELDS)
    //             .suppress(Warning.STRICT_INHERITANCE)
    //             .verify();
    // }

    // @Test
    // public void testDocumentation() {
    //     EqualsVerifier.forClass(EthCompileSolidity.Documentation.class)
    //             .suppress(Warning.NONFINAL_FIELDS)
    //             .suppress(Warning.STRICT_INHERITANCE)
    //             .verify();
    // }

    @Test
    public void testError() {
        EqualsVerifier.forClass(Response.Error.class)
                .suppress(Warning.NONFINAL_FIELDS)
                .suppress(Warning.STRICT_INHERITANCE)
                .verify();
    }
}
