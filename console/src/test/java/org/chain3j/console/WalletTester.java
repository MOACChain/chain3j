package org.chain3j.console;

import org.junit.Before;

import org.chain3j.TempFileProvider;

import static org.chain3j.crypto.SampleKeys.PASSWORD;
import static org.mockito.Mockito.mock;

public abstract class WalletTester extends TempFileProvider {

    static final char[] WALLET_PASSWORD = PASSWORD.toCharArray();

    IODevice console;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        console = mock(IODevice.class);
    }
}
