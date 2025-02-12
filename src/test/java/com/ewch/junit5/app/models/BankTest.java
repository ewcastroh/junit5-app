package com.ewch.junit5.app.models;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class BankTest {

    @Test
    void should_transfer_money_from_an_account_to_another_account() {
        Bank bank = new Bank("Bank Test");
        Account account1 = new Account("account1", new BigDecimal("100.00"));
        Account account2 = new Account("account2", new BigDecimal("50.00"));

        BigDecimal expectedBalanceAccount1 = new BigDecimal("20.00");
        BigDecimal expectedBalanceAccount2 = new BigDecimal("130.00");

        bank.transferMoney(account1, account2, new BigDecimal("80.00"));
        assertEquals(expectedBalanceAccount1, account1.getBalance());
        assertEquals(expectedBalanceAccount2, account2.getBalance());
    }

    @Test
    void should_test_bank_accounts_relationship() {
        Bank bank = new Bank("Bank Test");
        Account account1 = new Account("account1", new BigDecimal("100.00"));
        Account account2 = new Account("account2", new BigDecimal("50.00"));
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
}