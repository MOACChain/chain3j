package org.chain3j.ens;

import org.junit.Test;

import org.chain3j.tx.ChainId;

import static org.chain3j.ens.Contracts.MAINNET;
import static org.chain3j.ens.Contracts.TESTNET;
// import static org.chain3j.ens.Contracts.RINKEBY;
// import static org.chain3j.ens.Contracts.ROPSTEN;
import static org.chain3j.ens.Contracts.resolveRegistryContract;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class ContractsTest {

    @Test
    public void testResolveRegistryContract() {
        assertThat(resolveRegistryContract(ChainId.MAINNET + ""), is(MAINNET));
        assertThat(resolveRegistryContract(ChainId.TESTNET + ""), is(TESTNET));
        // assertThat(resolveRegistryContract(ChainId.ROPSTEN + ""), is(ROPSTEN));
        // assertThat(resolveRegistryContract(ChainId.RINKEBY + ""), is(RINKEBY));
    }

    @Test(expected = EnsResolutionException.class)
    public void testResolveRegistryContractInvalid() {
        resolveRegistryContract(ChainId.NONE + "");
    }
}
