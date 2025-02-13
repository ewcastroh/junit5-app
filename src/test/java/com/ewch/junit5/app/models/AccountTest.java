package com.ewch.junit5.app.models;

import com.ewch.junit5.app.exceptions.NotEnoughBalanceException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Allows creating a single instance to execute all tests methods
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AccountTest {

    Account account;

    @BeforeEach
    void setUp() {
        System.out.println("Before each test");
        this.account = new Account("username", new BigDecimal("1000.123456"));
    }

    @AfterEach
    void tearDown() {
        System.out.println("After each test");
    }

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before all tests");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After all tests");
    }

    @Test
    @DisplayName("Testing account name")
    void testAccountName() {
        String expected = "username";

        String actual = account.getUsername();

        assertNotNull(expected, () -> "Expected " + expected + " but got " + actual);
        assertEquals(expected, actual, () -> "Expected " + expected + " but got " + actual);
        assertTrue(actual.equals(expected), () -> "Expected " + expected + " but got " + actual);
    }

    @Test
    @DisplayName("Testing account balance")
    void testAccountBalance() {
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
        Account account2 = new Account("username", new BigDecimal("1000.123456"));

        //assertNotEquals(account1, account2);
        assertEquals(account, account2);
    }

    @Test
    @DisplayName("Testing account debit")
    void testAccountDebit() {
        BigDecimal amount = new BigDecimal("100");
        BigDecimal expected = account.getBalance().subtract(amount);

        account.debit(amount);

        assertNotNull(amount);
        assertEquals(expected, account.getBalance());
    }

    @Test
    @DisplayName("Testing account credit")
    void testAccountCredit() {
        BigDecimal amount = new BigDecimal("100");
        BigDecimal expected = account.getBalance().add(amount);

        account.credit(amount);

        assertNotNull(amount);
        assertEquals(expected, account.getBalance());
    }

    @Test
    @DisplayName("Testing NotEnoughBalanceException")
    void testNotEnoughBalanceException() {
        NotEnoughBalanceException notEnoughBalanceException = assertThrows(NotEnoughBalanceException.class, () -> {
            account.debit(new BigDecimal("1500"));
        });
    }
}