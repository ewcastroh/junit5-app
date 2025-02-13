package com.ewch.junit5.app.models;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeFalse;
import static org.junit.jupiter.api.Assumptions.assumeTrue;
import static org.junit.jupiter.api.Assumptions.assumingThat;

public class AssumptionsTest {

    @Test
    @DisplayName("Testing account balance")
    void testAccountBalanceDev() {
        Account account = new Account("username", new BigDecimal("1000.123456"));
        BigDecimal expected = new BigDecimal("1000.123456");
        BigDecimal actual = account.getBalance();

        boolean isDev = "dev".equals(System.getenv("ENVIRONMENT"));

        assumeTrue(isDev);

        assertNotNull(expected);
        assertEquals(expected, actual);
        assertTrue(actual.compareTo(BigDecimal.ZERO) > 0);
        assertFalse(actual.compareTo(BigDecimal.ZERO) < 0);
    }

    @Test
    @DisplayName("Testing account balance")
    void testAccountBalanceDev2() {
        Account account = new Account("username", new BigDecimal("1000.123456"));
        BigDecimal expected = new BigDecimal("1000.123456");
        BigDecimal actual = account.getBalance();

        boolean isDev = "dev".equals(System.getenv("ENVIRONMENT"));

        assumeFalse(isDev);
        assumingThat(isDev, () -> {
            assertAll(
                    () -> assertNotNull(expected),
                    () -> assertEquals(expected, actual),
                    () -> assertTrue(actual.compareTo(BigDecimal.ZERO) > 0),
                    () -> assertFalse(actual.compareTo(BigDecimal.ZERO) < 0)
            );
        });
    }
}
