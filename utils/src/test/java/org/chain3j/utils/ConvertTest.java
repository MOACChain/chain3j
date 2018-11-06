package org.chain3j.utils;

import java.math.BigDecimal;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;


public class ConvertTest {

    @Test
    public void testFromSha() {
        assertThat(Convert.fromSha("21000000000000", Convert.Unit.SHA),
                is(new BigDecimal("21000000000000")));
        assertThat(Convert.fromSha("21000000000000", Convert.Unit.KSHA),
                is(new BigDecimal("21000000000")));
        assertThat(Convert.fromSha("21000000000000", Convert.Unit.MSHA),
                is(new BigDecimal("21000000")));
        assertThat(Convert.fromSha("21000000000000", Convert.Unit.GSHA),
                is(new BigDecimal("21000")));
        assertThat(Convert.fromSha("21000000000000", Convert.Unit.SAND),
                is(new BigDecimal("21")));
        assertThat(Convert.fromSha("21000000000000", Convert.Unit.MILLI),
                is(new BigDecimal("0.021")));
        assertThat(Convert.fromSha("21000000000000", Convert.Unit.MC),
                is(new BigDecimal("0.000021")));
        assertThat(Convert.fromSha("21000000000000", Convert.Unit.KMC),
                is(new BigDecimal("0.000000021")));
        assertThat(Convert.fromSha("21000000000000", Convert.Unit.MMC),
                is(new BigDecimal("0.000000000021")));
        assertThat(Convert.fromSha("21000000000000", Convert.Unit.GMC),
                is(new BigDecimal("0.000000000000021")));
    }

    @Test
    public void testToSha() {
        assertThat(Convert.toSha("21", Convert.Unit.SHA), is(new BigDecimal("21")));
        assertThat(Convert.toSha("21", Convert.Unit.KSHA), is(new BigDecimal("21000")));
        assertThat(Convert.toSha("21", Convert.Unit.MSHA), is(new BigDecimal("21000000")));
        assertThat(Convert.toSha("21", Convert.Unit.GSHA), is(new BigDecimal("21000000000")));
        assertThat(Convert.toSha("21", Convert.Unit.SAND), is(new BigDecimal("21000000000000")));
        assertThat(Convert.toSha("21", Convert.Unit.MILLI),
                is(new BigDecimal("21000000000000000")));
        assertThat(Convert.toSha("21", Convert.Unit.MC),
                is(new BigDecimal("21000000000000000000")));
        assertThat(Convert.toSha("21", Convert.Unit.KMC),
                is(new BigDecimal("21000000000000000000000")));
        assertThat(Convert.toSha("21", Convert.Unit.MMC),
                is(new BigDecimal("21000000000000000000000000")));
        assertThat(Convert.toSha("21", Convert.Unit.GMC),
                is(new BigDecimal("21000000000000000000000000000")));
    }

    @Test
    public void testUnit() {
        assertThat(Convert.Unit.fromString("mc"), is(Convert.Unit.MC));
        assertThat(Convert.Unit.fromString("MC"), is(Convert.Unit.MC));
        assertThat(Convert.Unit.fromString("sha"), is(Convert.Unit.SHA));
    }
}
