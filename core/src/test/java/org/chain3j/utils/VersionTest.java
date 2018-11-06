package org.chain3j.utils;

import java.io.IOException;

import org.junit.Test;

import static org.chain3j.utils.Version.DEFAULT;
import static org.chain3j.utils.Version.getTimestamp;
import static org.chain3j.utils.Version.getVersion;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class VersionTest {

    @Test
    public void testGetVersion() throws IOException {
        assertThat(getVersion(), is(DEFAULT));
    }

    @Test
    public void testGetTimestamp() throws IOException {
        assertThat(getTimestamp(), is("2018-10-20 01:21:09.843 UTC"));
    }
}
