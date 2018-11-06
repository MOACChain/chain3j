package org.chain3j.ens;

import org.junit.Test;

import org.chain3j.protocol.Chain3j;
import org.chain3j.protocol.http.HttpService;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class EnsIT {

    @Test
    public void testEns() throws Exception {

        Chain3j chain3j = Chain3j.build(new HttpService());
        EnsResolver ensResolver = new EnsResolver(chain3j);

        assertThat(ensResolver.resolve("chain3j.test"),
                is("0x19e03255f667bdfd50a32722df860b1eeaf4d635"));
    }
}
