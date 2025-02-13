package com.ewch.junit5.app.models;

import com.ewch.junit5.app.exceptions.NotEnoughBalanceException;

import java.math.BigDecimal;
import java.util.Objects;

public class Account {

    private String username;
    private BigDecimal balance;
    private Bank bank;

    public Account(String username, BigDecimal balance) {
        this.username = username;
        this.balance = balance;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(username, account.username) && Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, balance);
    }

    public void debit(BigDecimal amount) {
        BigDecimal newBalance = balance.subtract(amount);
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new NotEnoughBalanceException("Not enough balance");
        }
        balance = newBalance;
    }

    public void credit(BigDecimal amount) {
        balance = balance.add(amount);
    }
}
