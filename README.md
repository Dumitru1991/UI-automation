# UI Automation — SauceDemo

A UI test automation framework built with Java, Selenium WebDriver, TestNG, Maven, and Allure. The project automates key user journeys on the [SauceDemo demo website](https://www.saucedemo.com/).

## Tech Stack

- **Java 21**
- **Selenium WebDriver 4.35.0**
- **TestNG 7.11.0**
- **Maven** — build and dependency management
- **Allure** — test reporting
- **Page Object Model (POM)** — maintainable page classes and reusable methods

## Test Coverage

### Login
- Successful login with valid credentials
- Rejection of invalid credentials
- Validation for missing username or password
- Locked-out user validation

### Shopping Cart
- Add a product to the cart
- Verify cart item count

### Checkout
- Verify cart contents
- Remove a product from the cart
- Validate required customer details
- Complete a successful checkout
- Verify the product and price in the checkout overview

## Project Structure

```text
dumi-project/
├── src/
│   ├── main/java/com/dumi/qa/pages/
│   │   ├── LoginPage.java
│   │   ├── ProductsPage.java
│   │   ├── CartPage.java
│   │   └── CheckoutPage.java
│   └── test/
│       ├── java/com/dumi/qa/
│       │   ├── BaseTest.java
│       │   ├── LoginTest.java
│       │   ├── CartTest.java
│       │   ├── CheckoutTest.java
│       │   ├── data/LoginDataProvider.java
│       │   └── config/ConfigReader.java
│       └── resources/
│           ├── config.properties
│           └── allure.properties
├── testng.xml
├── pom.xml
└── README.md
```

## Prerequisites

Install the following before running the tests:

- JDK 21
- Maven
- Google Chrome
- Allure command-line tool, if you want to view reports using the Maven `allure:serve` goal

Check Java and Maven installations:

```bash
java -version
mvn -version
```

## Configuration

Test settings are stored in `src/test/resources/config.properties`.

Example:

```properties
base.url=https://www.saucedemo.com/
browser=chrome
headless=false
username=standard_user
password=secret_sauce
timeout=10
```

These are public demo credentials for SauceDemo, not personal account credentials.

## Run the Tests

Run the complete test suite:

```bash
mvn clean test
```

Run only the login tests:

```bash
mvn -Dtest=LoginTest test
```

Run only the cart tests:

```bash
mvn -Dtest=CartTest test
```

Run only the checkout tests:

```bash
mvn -Dtest=CheckoutTest test
```

## Allure Test Report

Generate and open the Allure report:

```bash
mvn allure:serve
```

The report includes test results and the test metadata defined in the framework.

## Latest Test Results

The latest full clean test run completed successfully:

- **Tests run:** 17
- **Failures:** 0
- **Errors:** 0
- **Skipped:** 0
- **Build status:** SUCCESS

## Project Goals

This project demonstrates UI automation fundamentals, including the Page Object Model, reusable page methods, explicit waits, data-driven testing, independent test setup, and test reporting.

## Author

Created as a QA automation portfolio project.