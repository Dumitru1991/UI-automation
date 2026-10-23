
package com.dumi.qa;

import com.dumi.qa.config.ConfigReader;
import com.dumi.qa.pages.LoginPage;
import com.dumi.qa.pages.ProductsPage;

import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {

    @Test
    public void addBackpackToCartTest() {

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

        productsPage.addBackpackToCart();

        Assert.assertEquals(
            productsPage.getCartCount(),
            "1",
            "Cart should contain one product!"
        );
    }
}
