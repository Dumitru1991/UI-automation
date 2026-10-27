
package com.dumi.qa;

import com.dumi.qa.config.ConfigReader;
import com.dumi.qa.pages.LoginPage;
import com.dumi.qa.pages.ProductsPage;
import com.dumi.qa.pages.CartPage;
import com.dumi.qa.pages.CheckoutPage;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;

import org.testng.Assert;
import org.testng.annotations.Test;

@Epic("SauceDemo E-commerce")
@Feature("Checkout")
public class CheckoutTest extends BaseTest {

    private CartPage openCartWithBackpack() {

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
        productsPage.openCart();

        return new CartPage(driver);
    }

    @Test(
        testName = "Backpack appears in cart",
        description = "Verify the selected backpack appears in the shopping cart.",
        groups = {"smoke", "regression"}
    )
    @Story("Cart contents")
    @Severity(SeverityLevel.CRITICAL)
    @Description("The cart should display the Sauce Labs Backpack after it is added.")
    public void shouldDisplayBackpackInCart() {

        CartPage cartPage = openCartWithBackpack();

        Assert.assertEquals(
            cartPage.getCartItemName(),
            "Sauce Labs Backpack",
            "The cart should contain the Sauce Labs Backpack."
        );
    }

    @Test(
        testName = "Remove backpack from cart",
        description = "Verify the backpack can be removed from the shopping cart.",
        groups = {"regression"}
    )
    @Story("Cart contents")
    @Severity(SeverityLevel.NORMAL)
    @Description("The cart should be empty after the backpack is removed.")
    public void shouldRemoveBackpackFromCart() {

        CartPage cartPage = openCartWithBackpack();

        cartPage.removeBackpack();

        Assert.assertTrue(
            cartPage.isCartEmpty(),
            "The cart should be empty after removing the backpack."
        );
    }

    @Test(
        testName = "First name is required",
        description = "Verify checkout validation when the first name is missing.",
        groups = {"regression"}
    )
    @Story("Required checkout fields")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Checkout should display an error when the first name is missing.")
    public void shouldRequireFirstNameDuringCheckout() {

        CartPage cartPage = openCartWithBackpack();
        cartPage.clickCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);

        checkoutPage.clickContinue();

        Assert.assertEquals(
            checkoutPage.getErrorMessage(),
            "Error: First Name is required",
            "An error should appear when the first name is missing."
        );
    }

    @Test(
        testName = "Successful checkout",
        description = "Verify an order can be completed using valid customer details.",
        groups = {"smoke", "regression"}
    )
    @Story("Order completion")
    @Severity(SeverityLevel.BLOCKER)
    @Description("A successful checkout should display the order confirmation message.")
    public void shouldCompleteCheckoutSuccessfully() {

        CartPage cartPage = openCartWithBackpack();
        cartPage.clickCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);

        checkoutPage.enterCustomerDetails(
            "Dumi",
            "Test",
            "550001"
        );

        checkoutPage.clickContinue();
        checkoutPage.finishCheckout();

        Assert.assertEquals(
            checkoutPage.getConfirmationMessage(),
            "Thank you for your order!",
            "The order confirmation message should be displayed."
        );
    }
    
    @Test(
        testName = "Last name is required",
        description = "Verify checkout rejects a missing last name",
        groups = {"regression"}
    )
    @Story("Required checkout fields")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Checkout should display an error when the last name is missing.")
    public void shouldRequireLastNameDuringCheckout() {
        CartPage cartPage = openCartWithBackpack();
        cartPage.clickCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterCustomerDetails("Dumi", "", "550001");
        checkoutPage.clickContinue();

        Assert.assertEquals(
            checkoutPage.getErrorMessage(),
            "Error: Last Name is required",
            "An error should be displayed when the last name is missing."
        );
    }

    @Test(
        testName = "Postal code is required",
        description = "Verify checkout rejects a missing postal code",
        groups = {"regression"}
    )
    @Story("Required checkout fields")
    @Severity(SeverityLevel.CRITICAL)
    @Description("Checkout should display an error when the postal code is missing.")
    public void shouldRequirePostalCodeDuringCheckout() {
        CartPage cartPage = openCartWithBackpack();
        cartPage.clickCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterCustomerDetails("Dumi", "Test", "");
        checkoutPage.clickContinue();

        Assert.assertEquals(
            checkoutPage.getErrorMessage(),
            "Error: Postal Code is required",
            "An error should be displayed when the postal code is missing."
        );
    }


    @Test(
        testName = "Verify backpack in checkout overview",
        description = "Verify the correct product and price appear in the checkout overview",
        groups = {"smoke", "regression"}
    )
    @Story("Order overview")
    @Severity(SeverityLevel.CRITICAL)
    @Description("The checkout overview should display the Sauce Labs Backpack at $29.99.")
    public void shouldVerifyBackpackInCheckoutOverview() {
        CartPage cartPage = openCartWithBackpack();
        cartPage.clickCheckout();

        CheckoutPage checkoutPage = new CheckoutPage(driver);
        checkoutPage.enterCustomerDetails("Dumi", "Test", "550001");
        checkoutPage.clickContinue();

        Assert.assertEquals(
            checkoutPage.getOverviewItemName(),
            "Sauce Labs Backpack",
            "The checkout overview should display the correct product."
        );

        Assert.assertEquals(
            checkoutPage.getOverviewItemPrice(),
            "$29.99",
            "The checkout overview should display the correct product price."
        );
    }


}
