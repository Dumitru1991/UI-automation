
package com.dumi.qa;

import com.dumi.qa.pages.LoginPage;
import com.dumi.qa.pages.ProductsPage;
import com.dumi.qa.config.ConfigReader;
import com.dumi.qa.pages.CartPage;
import com.dumi.qa.pages.CheckoutPage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutTest extends BaseTest {

    private CartPage openCartWithBackpack() {

        LoginPage loginPage = new LoginPage(driver);
        loginPage.login(
        ConfigReader.get("username"),
        ConfigReader.get("password")
        );

        ProductsPage productsPage = new ProductsPage(driver);
        Assert.assertEquals(
            productsPage.getPageTitle(), "Products"
        );

        productsPage.addBackpackToCart();
        productsPage.openCart();

        return new CartPage(driver);
    }

    @Test
    public void verifyCartProductTest() {

        CartPage cartPage = openCartWithBackpack();

        Assert.assertEquals(
            cartPage.getCartItemName(),
            "Sauce Labs Backpack"
        );
    }

    @Test
    public void removeProductFromCartTest() {

        CartPage cartPage = openCartWithBackpack();

        cartPage.removeBackpack();

        Assert.assertTrue(
            cartPage.isCartEmpty(),
            "Cart should be empty after removal!"
        );
    }

    @Test
    public void checkoutRequiredFieldsTest() {

        CartPage cartPage = openCartWithBackpack();
        cartPage.clickCheckout();

        CheckoutPage checkoutPage =
            new CheckoutPage(driver);

        checkoutPage.clickContinue();

        Assert.assertEquals(
            checkoutPage.getErrorMessage(),
            "Error: First Name is required"
        );
    }

    @Test
    public void successfulCheckoutTest() {

        CartPage cartPage = openCartWithBackpack();
        cartPage.clickCheckout();

        CheckoutPage checkoutPage =
            new CheckoutPage(driver);

        checkoutPage.enterCustomerDetails(
            "Dumi",
            "Test",
            "550001"
        );

        checkoutPage.clickContinue();
        checkoutPage.finishCheckout();

        Assert.assertEquals(
            checkoutPage.getConfirmationMessage(),
            "Thank you for your order!"
        );
    }
}
