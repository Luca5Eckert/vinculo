package com.vinculo.integration;

import com.vinculo.module.connection.domain.model.TypeConnection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration tests for the Connection module domain models.
 * Tests the connection type weights and enum behavior.
 */
@DisplayName("Connection Module Integration Tests")
class ConnectionModuleIntegrationTest {

    @Test
    @DisplayName("Should correctly assign connection weights for tier 1 relationships")
    void shouldAssignCorrectWeightsForTier1() {
        assertEquals(1, TypeConnection.PARTNER.getWeight());
        assertEquals(1, TypeConnection.FAMILY.getWeight());
    }

    @Test
    @DisplayName("Should correctly assign connection weights for tier 2 relationships")
    void shouldAssignCorrectWeightsForTier2() {
        assertEquals(2, TypeConnection.FRIEND.getWeight());
        assertEquals(2, TypeConnection.BUSINESS_PARTNER.getWeight());
    }

    @Test
    @DisplayName("Should correctly assign connection weights for tier 3 relationships")
    void shouldAssignCorrectWeightsForTier3() {
        assertEquals(3, TypeConnection.MENTOR.getWeight());
        assertEquals(3, TypeConnection.REFERRAL.getWeight());
    }

    @Test
    @DisplayName("Should correctly assign connection weights for tier 4 relationships")
    void shouldAssignCorrectWeightsForTier4() {
        assertEquals(4, TypeConnection.COLLEAGUE.getWeight());
        assertEquals(4, TypeConnection.BUDDY.getWeight());
    }

    @Test
    @DisplayName("Should correctly assign connection weights for tier 5 relationships")
    void shouldAssignCorrectWeightsForTier5() {
        assertEquals(5, TypeConnection.ACQUAINTANCE.getWeight());
    }

    @Test
    @DisplayName("Should have all expected connection types available")
    void shouldHaveAllExpectedConnectionTypes() {
        // Verify all 9 connection types exist
        TypeConnection[] types = TypeConnection.values();
        assertEquals(9, types.length);
    }
}
