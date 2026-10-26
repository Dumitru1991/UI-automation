
package com.dumi.qa;

import com.dumi.qa.config.ConfigReader;
import com.dumi.qa.data.LoginDataProvider;
import com.dumi.qa.pages.LoginPage;
import com.dumi.qa.pages.ProductsPage;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("SauceDemo E-commerce")
@Feature("User Authentication")
public class LoginTest extends BaseTest {

    @Test(
        testName = "Successful login",
        description = "Verify login with valid credentials",
        groups = {"smoke", "regression"} )
    @Story("Valid credentials")
    @Severity(SeverityLevel.CRITICAL)
    @Description("A user with valid credentials should reach the Products page.")
    public void shouldLoginSuccessfullyWithValidCredentials() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
            ConfigReader.get("username"),
            ConfigReader.get("password")
        );

        ProductsPage productsPage = new ProductsPage(driver);

        Assert.assertEquals(
            productsPage.getPageTitle(),
            "Products",
            "The user should see the Products page after login."
        );

        Assert.assertTrue(
            driver.getCurrentUrl().contains("inventory.html"),
            "The URL should contain inventory.html after successful login."
        );
    }

    @Test(
        testName = "Invalid credentials",
        description = "Verify login fails with invalid credentials",
        groups = {"regression"}
    )
    @Story("Invalid credentials")
    @Severity(SeverityLevel.NORMAL)
    @Description("Invalid credentials should display an authentication error.")
    public void shouldRejectInvalidCredentials() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("invalid_user", "wrong_password");

        String actualError = loginPage.getErrorMessage().getText();

        Assert.assertTrue(
            actualError.contains("Username and password do not match"),
            "Expected an invalid-credentials error, but received: "
                + actualError
        );
    }

    @Test(
        testName = "Empty username",
        description = "Verify validation when username is empty",
        groups = {"regression"}
    )
    @Story("Required field validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Submitting an empty username should display a validation message.")
    public void shouldRequireUsername() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", ConfigReader.get("password"));

        Assert.assertEquals(
            loginPage.getErrorMessage().getText(),
            "Epic sadface: Username is required",
            "The expected username-required message should be displayed."
        );
    }

    @Test(
        testName = "Empty password",
        description = "Verify validation when password is empty",
        groups = {"regression"}
    )
    @Story("Required field validation")
    @Severity(SeverityLevel.NORMAL)
    @Description("Submitting an empty password should display a validation message.")
    public void shouldRequirePassword() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(ConfigReader.get("username"), "");

        Assert.assertEquals(
            loginPage.getErrorMessage().getText(),
            "Epic sadface: Password is required",
            "The expected password-required message should be displayed."
        );
    }

    @Test(
        testName = "Locked-out user",
        description = "Verify that a locked-out user cannot log in",
        groups = {"regression"}
    )
    @Story("Locked account")
    @Severity(SeverityLevel.CRITICAL)
    @Description("A locked-out user should receive an appropriate error message.")
    public void shouldRejectLockedOutUser() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
            "locked_out_user",
            ConfigReader.get("password")
        );

        String actualError = loginPage.getErrorMessage().getText();

        Assert.assertTrue(
            actualError.contains("locked out"),
            "Expected a locked-out error, but received: " + actualError
        );
    }

    @Test(
        testName = "Data-driven invalid login",
        description = "Verify error handling for multiple invalid login inputs",
        groups = {"regression"},
        dataProvider = "invalidLoginData",
        dataProviderClass = LoginDataProvider.class
    )
    @Story("Invalid login data")
    @Severity(SeverityLevel.NORMAL)
    @Description("Each invalid credential combination should produce its expected error.")
    public void shouldRejectInvalidLoginData(
        String username,
        String password,
        String expectedError
    ) {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(username, password);

        String actualError = loginPage.getErrorMessage().getText();

        Assert.assertTrue(
            actualError.contains(expectedError),
            "Expected error containing: " + expectedError
                + ", but received: " + actualError
        );
    }
}
