package org.chain3j.crypto;

import org.junit.Test;

import static org.chain3j.crypto.SecureRandomUtils.isAndroidRuntime;
import static org.chain3j.crypto.SecureRandomUtils.secureRandom;
import static org.junit.Assert.assertFalse;

public class SecureRandomUtilsTest {

    @Test
    public void testSecureRandom() {
        secureRandom().nextInt();
    }

    @Test
    public void testIsNotAndroidRuntime() {
        assertFalse(isAndroidRuntime());
    }
}
