# JUnit 5 App Learning Project

## Overview

This project is a hands-on learning experience with JUnit 5, showcasing best practices and different testing approaches. It includes various examples of JUnit 5 features, such as lifecycle management, parameterized tests, nested tests, and conditional test execution.

## Technologies Used

- **Java** (Latest version supported by JUnit 5)
- **JUnit 5** (Jupiter API)
- **Maven** (Build tool)
- **Maven Surefire Plugin** (For test execution and reporting)

## Project Structure

```
/ src
  / main
    / java
      / com.example.model (Domain models)
  / test
    / java
      / com.example.tests (Unit tests using JUnit 5)
```

## JUnit 5 Features and Best Practices Covered

### 1. Basic Unit Testing

- Writing simple test cases using `@Test`: Marks a method as a test case.
- Adding descriptive names with `@DisplayName`: Provides a custom name for test methods.
- Custom error messages for better debugging: Helps identify failure reasons quickly.

### 2. Assertions and Timeouts

- Using `assertAll` for grouped assertions: Ensures multiple assertions execute in a single test.
- Handling exceptions with assertions: Validates expected exceptions using `assertThrows`.
- Implementing timeouts using `@Timeout`: Fails a test if execution exceeds the specified time.
- Using `assertTimeout`: Ensures a block of code completes within a given time.

### 3. Parameterized Tests

- Using `@ParameterizedTest`: Allows running a test multiple times with different parameters.
- Supplying test data with:
    - `@ValueSource`: Provides a set of literal values.
    - `@CsvSource`: Supplies test data from comma-separated values.
    - `@CsvFileSource`: Reads test data from an external CSV file.
    - `@MethodSource`: Supplies data from a method returning a stream of arguments.

### 4. Lifecycle Management

- Setting up and cleaning resources using:
    - `@BeforeEach`: Executes before each test.
    - `@AfterEach`: Executes after each test.
    - `@BeforeAll`: Runs once before all tests.
    - `@AfterAll`: Runs once after all tests.
- Controlling test instances with `@TestInstance`: Configures test instance lifecycle (per-class or per-method).

### 5. Conditional Test Execution

- Using `@EnabledOnOs` and `@DisabledOnOs`: Runs or skips tests based on the OS.
- Applying `@EnabledOnJre` and `@DisabledOnJre`: Executes tests conditionally based on Java versions.
- Using system properties and environment variables to conditionally execute tests with `@EnabledIfSystemProperty` and `@EnabledIfEnvironmentVariable`.

### 6. Repeated and Nested Tests

- Executing tests multiple times with `@RepeatedTest`: Runs the test a specified number of times.
- Organizing related tests using `@Nested`: Groups inner test classes for better organization.

### 7. Test Metadata and Reporting

- Injecting `TestInfo` and `TestReporter` for metadata logging: Captures test details dynamically.
- Generating structured test reports with Maven Surefire Plugin.

### 8. Selective Test Execution

- Using `@Tag` to categorize tests: Enables selective execution of tests based on tags.
- Running specific tests based on tags using Maven Surefire or IDE filters.

### 9. Handling Exceptions

- Writing unit tests to validate exception handling in methods using `assertThrows`.
- Ensuring correct exception messages and types are asserted properly.

### 10. Test-Driven Development (TDD) Practices

- Implementing `credit` and `debit` methods using TDD.
- Validating account balance logic with incremental test cases.

### 11. Ignoring Tests

- Using `@Disabled`: Skips a test method or class temporarily.

## Running the Tests

To execute the tests, use the following Maven command:

```sh
mvn test
```

For detailed reports:

```sh
mvn surefire-report:report
```
