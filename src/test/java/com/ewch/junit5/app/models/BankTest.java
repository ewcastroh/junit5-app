package com.ewch.junit5.app.models;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeout;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Tag("bank")
class BankTest {

    private Bank bank;
    private Account account1;
    private Account account2;

    @BeforeEach
    void setUp() {
        Bank bank = new Bank("Bank Test");
        Account account1 = new Account("account1", new BigDecimal("100.00"));
        Account account2 = new Account("account2", new BigDecimal("50.00"));
    }

    @Test
    @DisplayName("Testing transfer from bank account to another one")
    void should_transfer_money_from_an_account_to_another_account() {

        BigDecimal expectedBalanceAccount1 = new BigDecimal("20.00");
        BigDecimal expectedBalanceAccount2 = new BigDecimal("130.00");

        bank.transferMoney(account1, account2, new BigDecimal("80.00"));
        assertEquals(expectedBalanceAccount1, account1.getBalance());
        assertEquals(expectedBalanceAccount2, account2.getBalance());
    }

    @Test
    @DisplayName("Testing bank-accounts relationships using assertAll")
        //@Disabled("Disabling test simulating a fail")
    void should_test_bank_accounts_relationship() {
        //fail();
        bank.addAccount(account1);
        bank.addAccount(account2);

        BigDecimal expectedBalanceAccount1 = new BigDecimal("20.00");
        BigDecimal expectedBalanceAccount2 = new BigDecimal("130.00");
        int expectedBalanceAccountsCount = 2;
        String expectedBankName = "Bank Test";
        String expectedAccountName = "account1";

        bank.transferMoney(account1, account2, new BigDecimal("80.00"));
        int bankAccountsCount = bank.getAccounts().size();
        String username = bank.getAccounts()
                .stream()
                .filter(account -> account.getUsername().equals(expectedAccountName))
                .findFirst()
                .get().getUsername();

        assertAll(
                () -> assertEquals(expectedBalanceAccount1, account1.getBalance()),
                () -> assertEquals(expectedBalanceAccount2, account2.getBalance()),

                () -> assertEquals(expectedBalanceAccountsCount, bankAccountsCount),
                () -> assertEquals(expectedBankName, bank.getName()),
                () -> assertEquals(expectedBankName, account1.getBank().getName()),
                () -> assertEquals(expectedAccountName, username),

                () -> assertTrue(bank.getAccounts()
                        .stream()
                        .anyMatch(account -> account.getUsername().equals(expectedAccountName)))
        );
    }

    @Tag("timeout")
    @Nested
    class BankAccountTimeoutTest {

        @Test
        @Timeout(1)
        void timeoutTest() throws InterruptedException {
            TimeUnit.SECONDS.sleep(1);
        }

        @Test
        @Timeout(value = 500, unit = TimeUnit.MILLISECONDS)
        void timeoutTest2() throws InterruptedException {
            TimeUnit.MILLISECONDS.sleep(499);
        }

        @Test
        void timeoutTest3() throws InterruptedException {
            assertTimeout(Duration.ofSeconds(3), () -> {
                TimeUnit.SECONDS.sleep(2);
            });
        }
    }
}
