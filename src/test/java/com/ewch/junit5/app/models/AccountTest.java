package com.ewch.junit5.app.models;

import com.ewch.junit5.app.exceptions.NotEnoughBalanceException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import org.junit.jupiter.api.TestReporter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

// Allows creating a single instance to execute all tests methods
//@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Tag("account")
class AccountTest {

    Account account;

    private TestInfo testInfo;
    private TestReporter reporter;

    @BeforeEach
    void setUp(TestInfo testInfo, TestReporter reporter) {
        System.out.println("Before each test");
        this.testInfo = testInfo;
        this.reporter = reporter;

        this.account = new Account("username", new BigDecimal("1000.123456"));
        reporter.publishEntry("testInfo.getDisplayName(): " + testInfo.getDisplayName());
        reporter.publishEntry("testInfo.getTestClass(): " + testInfo.getTestClass().get().getCanonicalName());
        reporter.publishEntry("testInfo.getTestMethod(): " + testInfo.getTestMethod().get().getName());
        reporter.publishEntry("testInfo.getTags()" + testInfo.getTags());
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
        System.out.println("Testing account name");
        reporter.publishEntry("using TestInfo in method: " + testInfo.getTestClass().get().descriptorString());
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

    @Nested
    class AccountOperationsTest {
        @Test
        @DisplayName("Testing account references")
        void testAccountReference() {
            System.out.println("Testing account references");
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

        @RepeatedTest(value = 5, name = "{displayName} - Current repetition {currentRepetition} of {totalRepetitions}")
        @DisplayName("Testing account debit repetition")
        void testAccountDebitRepetition(RepetitionInfo repetitionInfo) {
            if (repetitionInfo.getCurrentRepetition() == 1) {
                System.out.println("Repetition " + repetitionInfo.getCurrentRepetition());
            }
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

        @Tag("parameterized")
        @Nested
        class ParameterizedTests {
            @ParameterizedTest(name = "Test {index} running with value {0} - {argumentsWithNames}")
            @ValueSource(strings = { "100", "200", "500", "1000.123456" })
            @DisplayName("Parameterized Test account debit using ValueSource")
            void parameterizedTestAccountDebitValueSource(String amount) {
                System.out.println("Testing account debit value source");
                account.debit(new BigDecimal(amount));

                assertNotNull(account.getBalance());
                assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
            }

            @ParameterizedTest(name = "Test {index} running with value {0} - {argumentsWithNames}")
            @CsvSource({ "1,100", "2,200", "3,500", "4,1000.123456" })
            @DisplayName("Parameterized Test account debit using CsvSource")
            void parameterizedTestAccountDebitCsvSource(String index, String amount) {
                System.out.println("Testing account debit csv source");
                System.out.println("Index: " + index);
                System.out.println("Amount: " + amount);

                account.debit(new BigDecimal(amount));

                assertNotNull(account.getBalance());
                assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
            }

            @ParameterizedTest(name = "Test {index} running with value {0} - {argumentsWithNames}")
            @CsvSource({ "200,100,Eimer,Smoke", "250,200,Alejandra,Alejandra", "510,500,Mona,Mona", "1000.123456,1000.123456,Tigro,Dohko" })
            @DisplayName("Parameterized Test account debit using CsvSource")
            void parameterizedTestAccountDebitCsvSource2(String balance, String amount, String expectedName, String actualName) {
                System.out.println("Testing account debit csv source");
                System.out.println("Balance: " + balance);
                System.out.println("Amount: " + amount);

                account.setBalance(new BigDecimal(balance));
                account.setUsername(actualName);
                account.debit(new BigDecimal(amount));

                assertNotNull(account.getBalance());
                assertNotNull(account.getUsername());
                assertEquals(expectedName, account.getUsername());
                assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
            }

            @ParameterizedTest(name = "Test {index} running with value {0} - {argumentsWithNames}")
            @CsvFileSource(resources = "/data.csv")
            @DisplayName("Parameterized Test account debit using CsvFileSource")
            void parameterizedTestAccountDebitCsvFileSource(String amount) {
                System.out.println("Testing account debit csv file source");
                System.out.println("Amount: " + amount);

                account.debit(new BigDecimal(amount));

                assertNotNull(account.getBalance());
                assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
            }

            @ParameterizedTest(name = "Test {index} running with value {0} - {argumentsWithNames}")
            @CsvFileSource(resources = "/data2.csv")
            @DisplayName("Parameterized Test account debit using CsvFileSource")
            void parameterizedTestAccountDebitCsvFileSource2(String balance, String amount, String expectedName, String actualName) {
                System.out.println("Testing account debit csv file source");
                System.out.println("Balance: " + balance);
                System.out.println("Amount: " + amount);

                account.setBalance(new BigDecimal(balance));
                account.setUsername(actualName);
                account.debit(new BigDecimal(amount));

                assertNotNull(account.getBalance());
                assertNotNull(account.getUsername());
                assertEquals(expectedName, account.getUsername());
                assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
            }

            @ParameterizedTest(name = "Test {index} running with value {0} - {argumentsWithNames}")
            @MethodSource("amountList")
            @DisplayName("Parameterized Test account debit using MethodSource")
            void parameterizedTestAccountDebitMethodSource(String amount) {
                System.out.println("Testing account debit csv file source");
                System.out.println("Amount: " + amount);

                account.debit(new BigDecimal(amount));

                assertNotNull(account.getBalance());
                assertTrue(account.getBalance().compareTo(BigDecimal.ZERO) > 0);
            }

            private static List<String> amountList() {
                return Arrays.asList("100", "200", "500", "1000.123456");
            }
        }
    }
}