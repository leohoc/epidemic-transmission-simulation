package com.leohoc.ets.util;

import java.util.Random;

public class RandomUtil {

    private static final int HUNDRED_PERCENT = 100;
    private static final Random random = new Random();

    public static int generatePercent() {
        return random.nextInt(HUNDRED_PERCENT);
    }

    public static int generateIntLessThan(final int endLimit) {
        return random.nextInt(endLimit);
    }

    public static int generateIntBetween(final int startLimit, final int endLimit) {
        final int bound = endLimit - startLimit;
        return random.nextInt(bound) + startLimit;
    }
}
