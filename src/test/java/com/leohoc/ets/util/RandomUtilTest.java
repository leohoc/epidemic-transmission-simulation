package com.leohoc.ets.util;

import org.junit.jupiter.api.Test;

import java.security.SecureRandom;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class RandomUtilTest {

    private final SecureRandom secureRandom = mock(SecureRandom.class);

    @Test
    void testGeneratePercentWithTwoDigitsScale() {
        // Given
        RandomUtil randomUtil = new RandomUtil(secureRandom);
        final int generatedRandom = 4567;
        final double expectedGeneratedPercent = 45.67;

        // When
        when(secureRandom.nextInt(anyInt())).thenReturn(generatedRandom);
        final double actualGeneratedPercent = randomUtil.generatePercentWithTwoDigitsScale();

        // Then
        assertEquals(expectedGeneratedPercent, actualGeneratedPercent);
    }

    @Test
    void testGenerateIntLessThan() {
        // Given
        RandomUtil randomUtil = new RandomUtil(secureRandom);
        final int bound = 100;
        final int generatedRandom = 57;
        final int expectedGeneratedInt = 57;

        // When
        when(secureRandom.nextInt(eq(bound))).thenReturn(generatedRandom);
        final int actualGeneratedInt = randomUtil.generateIntLessThan(bound);

        // Then
        assertEquals(expectedGeneratedInt, actualGeneratedInt);
    }

    @Test
    void testGenerateIntBetween() {
        // Given
        RandomUtil randomUtil = new RandomUtil(secureRandom);
        final int startLimit = 10;
        final int endLimit = 20;
        final int generatedRandom = 7;
        final int expectedGeneratedInt = 17;

        // When
        final int bound = endLimit - startLimit;
        when(secureRandom.nextInt(eq(bound))).thenReturn(generatedRandom);
        final int actualGeneratedInt = randomUtil.generateIntBetween(startLimit, endLimit);

        // Then
        assertEquals(expectedGeneratedInt, actualGeneratedInt);
    }

}