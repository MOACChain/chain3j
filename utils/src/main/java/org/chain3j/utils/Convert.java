package org.chain3j.utils;

import java.math.BigDecimal;

/**
 * Moac unit conversion functions.
 */
public final class Convert {
    private Convert() { }

    public static BigDecimal fromSha(String number, Unit unit) {
        return fromSha(new BigDecimal(number), unit);
    }

    public static BigDecimal fromSha(BigDecimal number, Unit unit) {
        return number.divide(unit.getShaFactor());
    }

    public static BigDecimal toSha(String number, Unit unit) {
        return toSha(new BigDecimal(number), unit);
    }

    public static BigDecimal toSha(BigDecimal number, Unit unit) {
        return number.multiply(unit.getShaFactor());
    }

    public enum Unit {
        SHA("sha", 0),
        KSHA("ksha", 3),
        MSHA("msha", 6),
        GSHA("gsha", 9),
        SAND("sand", 12),
        MILLI("milli", 15),
        MC("mc", 18),
        KMC("kmc", 21),
        MMC("mmc", 24),
        GMC("gmc", 27);

        private String name;
        private BigDecimal shaFactor;

        Unit(String name, int factor) {
            this.name = name;
            this.shaFactor = BigDecimal.TEN.pow(factor);
        }

        public BigDecimal getShaFactor() {
            return shaFactor;
        }

        @Override
        public String toString() {
            return name;
        }

        public static Unit fromString(String name) {
            if (name != null) {
                for (Unit unit : Unit.values()) {
                    if (name.equalsIgnoreCase(unit.name)) {
                        return unit;
                    }
                }
            }
            return Unit.valueOf(name);
        }
    }
}
