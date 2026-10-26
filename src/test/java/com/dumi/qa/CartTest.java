
package com.dumi.qa;

import com.dumi.qa.config.ConfigReader;
import com.dumi.qa.pages.LoginPage;
import com.dumi.qa.pages.ProductsPage;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("SauceDemo E-commerce")
@Feature("Shopping Cart")
public class CartTest extends BaseTest {

    @Test(
        testName = "Add backpack to cart",
        description = "Verify that a user can add the Sauce Labs Backpack to the cart.",
        groups = {"smoke", "regression"}
    )
    @Story("Adding products to cart")
    @Severity(SeverityLevel.CRITICAL)
    @Description("After adding the Sauce Labs Backpack, the cart badge should display one item.")
    public void shouldAddBackpackToCart() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.login(
            ConfigReader.get("username"),
            ConfigReader.get("password")
        );

        ProductsPage productsPage = new ProductsPage(driver);

        Assert.assertEquals(
            productsPage.getPageTitle(),
            "Products",
            "The user should reach the Products page after login."
        );

        productsPage.addBackpackToCart();

        Assert.assertEquals(
            productsPage.getCartCount(),
            "1",
            "The cart badge should display one product after adding the backpack."
        );
    }
}
