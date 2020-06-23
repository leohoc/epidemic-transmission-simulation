package com.leohoc.ets.util;

import java.math.BigDecimal;
import java.security.SecureRandom;

public class RandomUtil {

    private static final int HUNDRED_PERCENT = 100;
    private final SecureRandom secureRandom;

    public RandomUtil(SecureRandom secureRandom) {
        this.secureRandom = secureRandom;
    }

    public double generatePercentWithTwoDigitsScale() {
        final int twoDigitsScaleFactor = 100;
        final int bound = HUNDRED_PERCENT * twoDigitsScaleFactor;
        final BigDecimal generatedPercent = new BigDecimal(secureRandom.nextInt(bound));
        return generatedPercent.divide(new BigDecimal(twoDigitsScaleFactor)).doubleValue();
    }

    public int generateIntLessThan(final int endLimit) {
        return secureRandom.nextInt(endLimit);
    }

    public int generateIntBetween(final int startLimit, final int endLimit) {
        final int bound = endLimit - startLimit;
        return secureRandom.nextInt(bound) + startLimit;
    }
}
