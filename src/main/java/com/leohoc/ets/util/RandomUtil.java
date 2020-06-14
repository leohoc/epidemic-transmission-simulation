package com.leohoc.ets.util;

import java.math.BigDecimal;
import java.security.SecureRandom;

public class RandomUtil {

    private static final int HUNDRED_PERCENT = 100;
    private static final SecureRandom random = new SecureRandom();

    private RandomUtil() {
        throw new IllegalStateException("Utility class");
    }

    public static double generatePercentWithTwoDigitsScale() {
        final int twoDigitsScaleFactor = 100;
        final int bound = HUNDRED_PERCENT * twoDigitsScaleFactor;
        final BigDecimal generatedPercent = new BigDecimal(random.nextInt(bound));
        return generatedPercent.divide(new BigDecimal(twoDigitsScaleFactor)).doubleValue();
    }

    public static int generateIntLessThan(final int endLimit) {
        return random.nextInt(endLimit);
    }

    public static int generateIntBetween(final int startLimit, final int endLimit) {
        final int bound = endLimit - startLimit;
        return random.nextInt(bound) + startLimit;
    }
}
