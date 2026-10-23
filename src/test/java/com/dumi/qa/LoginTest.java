
package com.dumi.qa;
import com.dumi.qa.config.ConfigReader;
import com.dumi.qa.pages.LoginPage;
import com.dumi.qa.pages.ProductsPage;
import com.dumi.qa.data.LoginDataProvider;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void validLoginTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
        ConfigReader.get("username"),
        ConfigReader.get("password")
        );

        ProductsPage productsPage = new ProductsPage(driver);

        Assert.assertEquals(
            productsPage.getPageTitle(),
            "Products"
        );

        Assert.assertTrue(
            driver.getCurrentUrl().contains("inventory.html")
        );
    }

    @Test
    public void invalidLoginTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("invalid_user", "wrong_password");

        Assert.assertTrue(
            loginPage.getErrorMessage().isDisplayed()
        );
    }

    @Test
    public void emptyUsernameTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("", "secret_sauce");

        Assert.assertEquals(
            loginPage.getErrorMessage().getText(),
            "Epic sadface: Username is required"
        );
    }

    @Test
    public void emptyPasswordTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("standard_user", "");

        Assert.assertEquals(
            loginPage.getErrorMessage().getText(),
            "Epic sadface: Password is required"
        );
    }

    @Test
    public void lockedOutUserTest() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login("locked_out_user", "secret_sauce");

        Assert.assertTrue(
            loginPage.getErrorMessage().getText()
                     .contains("locked out")
        );
    }
    
@Test(dataProvider = "invalidLoginData",
      dataProviderClass = LoginDataProvider.class)
public void dataDrivenInvalidLoginTest(
        String username,
        String password,
        String expectedError) {

    LoginPage loginPage = new LoginPage(driver);

    loginPage.login(username, password);

    String actualError =
        loginPage.getErrorMessage().getText();

    Assert.assertTrue(
        actualError.contains(expectedError),
        "Expected error containing: " + expectedError
        + " but got: " + actualError
    );
}

}
