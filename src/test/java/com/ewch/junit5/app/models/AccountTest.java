package com.ewch.junit5.app.models;

import com.ewch.junit5.app.exceptions.NotEnoughBalance;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AccountTest {

    @Test
    @DisplayName("Testing account name")
    void testAccountName() {
        Account account = new Account("username", new BigDecimal("1000.123456"));
        String expected = "username";

        String actual = account.getUsername();

        assertNotNull(expected, () -> "Expected " + expected + " but got " + actual);
        assertEquals(expected, actual, () -> "Expected " + expected + " but got " + actual);
        assertTrue(actual.equals(expected), () -> "Expected " + expected + " but got " + actual);
    }

    @Test
    @DisplayName("Testing account balance")
    void testAccountBalance() {
        Account account = new Account("username", new BigDecimal("1000.123456"));
        BigDecimal expected = new BigDecimal("1000.123456");

        BigDecimal actual = account.getBalance();

        assertNotNull(expected);
        assertEquals(expected, actual);
        assertTrue(actual.compareTo(BigDecimal.ZERO) > 0);
        assertFalse(actual.compareTo(BigDecimal.ZERO) < 0);
    }

    @Test
    @DisplayName("Testing account references")
    void testAccountReference() {
        Account account1 = new Account("username", new BigDecimal("1000.123456"));
        Account account2 = new Account("username", new BigDecimal("1000.123456"));

        //assertNotEquals(account1, account2);
        assertEquals(account1, account2);
    }

    @Test
    @DisplayName("Testing account debit")
    void testAccountDebit() {
        Account account1 = new Account("username", new BigDecimal("1000.123456"));
        BigDecimal amount = new BigDecimal("100");
        BigDecimal expected = account1.getBalance().subtract(amount);

        account1.debit(amount);

        assertNotNull(amount);
        assertEquals(expected, account1.getBalance());
    }

    @Test
    @DisplayName("Testing account credit")
    void testAccountCredit() {
        Account account1 = new Account("username", new BigDecimal("1000.123456"));
        BigDecimal amount = new BigDecimal("100");
        BigDecimal expected = account1.getBalance().add(amount);

        account1.credit(amount);

        assertNotNull(amount);
        assertEquals(expected, account1.getBalance());
    }

    @Test
    @DisplayName("Testing NotEnoughBalanceException")
    void testNotEnoughBalanceException() {
        Account account = new Account("username", new BigDecimal("1000.123456"));
        NotEnoughBalance notEnoughBalance = assertThrows(NotEnoughBalance.class, () -> {
            account.debit(new BigDecimal("1500"));
        });
    }
}