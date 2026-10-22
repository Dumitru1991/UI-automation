
package com.dumi.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class CartPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By cartItemName =
        By.className("inventory_item_name");

    private final By checkoutButton =
        By.id("checkout");

    private final By removeBackpackButton =
        By.id("remove-sauce-labs-backpack");

    public CartPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );
    }

    public String getCartItemName() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                cartItemName
            )
        ).getText();
    }

    public void removeBackpack() {
        wait.until(
            ExpectedConditions.elementToBeClickable(
                removeBackpackButton
            )
        ).click();
    }

    public boolean isCartEmpty() {
        return driver.findElements(cartItemName).isEmpty();
    }

    public void clickCheckout() {
        wait.until(
            ExpectedConditions.elementToBeClickable(
                checkoutButton
            )
        ).click();
    }
}
