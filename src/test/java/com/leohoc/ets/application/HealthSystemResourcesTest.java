package com.leohoc.ets.application;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HealthSystemResourcesTest {

    @Test
    public void testHasAvailableICUBeds() {
        // Given
        final int availableICUBeds = 1;
        HealthSystemResources healthSystemResource = new HealthSystemResources(availableICUBeds);

        // When
        final boolean hasAvailableICU = healthSystemResource.hasAvailableICUBed();

        // Then
        assertTrue(hasAvailableICU);
    }

    @Test
    public void testHasNotAvailableICUBeds() {
        // Given
        final int availableICUBeds = 0;
        HealthSystemResources healthSystemResource = new HealthSystemResources(availableICUBeds);

        // When
        final boolean hasAvailableICU = healthSystemResource.hasAvailableICUBed();

        // Then
        assertFalse(hasAvailableICU);
    }

    @Test
    public void testFillICUBed() {
        // Given
        final int availableICUBeds = 5;
        HealthSystemResources healthSystemResource = new HealthSystemResources(availableICUBeds);

        // When
        healthSystemResource.fillICUBed();

        // Then
        final int expectedAvailableICUBeds = 4;
        assertEquals(expectedAvailableICUBeds, healthSystemResource.getAvailableICUBeds());
    }

    @Test
    public void testReleaseICUBed() {
        // Given
        final int availableICUBeds = 5;
        HealthSystemResources healthSystemResource = new HealthSystemResources(availableICUBeds);

        // When
        healthSystemResource.releaseICUBed();

        // Then
        final int expectedAvailableICUBeds = 6;
        assertEquals(expectedAvailableICUBeds, healthSystemResource.getAvailableICUBeds());
    }
}