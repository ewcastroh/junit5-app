package com.ewch.junit5.app.models;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class AccountTest {

    @Test
    void testAccountName() {
        Account account = new Account("username", new BigDecimal("1000.123456"));
        String expected = "username";

        String actual = account.getUsername();

        assertEquals(expected, actual);
        assertTrue(actual.equals(expected));
    }

    @Test
    void testAccountBalance() {
        Account account = new Account("username", new BigDecimal("1000.123456"));
        BigDecimal expected = new BigDecimal("1000.123456");

        BigDecimal actual = account.getBalance();

        assertEquals(expected, actual);
        assertTrue(actual.compareTo(BigDecimal.ZERO) > 0);
        assertFalse(actual.compareTo(BigDecimal.ZERO) < 0);
    }
}