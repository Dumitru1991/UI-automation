
package com.dumi.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class ProductsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By pageTitle = By.className("title");
    private final By backpackButton =
            By.id("add-to-cart-sauce-labs-backpack");
    private final By removeBackpackButton =
            By.id("remove-sauce-labs-backpack");
    private final By cartBadge =
            By.className("shopping_cart_badge");
    private final By cartLink =
            By.className("shopping_cart_link");

    public ProductsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver, Duration.ofSeconds(10)
        );
    }

    public String getPageTitle() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(pageTitle)
        ).getText();
    }

    public void addBackpackToCart() {
        wait.until(
                ExpectedConditions.elementToBeClickable(backpackButton)
        ).click();
    }

    public String getCartCount() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(cartBadge)
        ).getText();
    }

    public void openCart() {
        wait.until(
                ExpectedConditions.elementToBeClickable(cartLink)
        ).click();
    }

    public void removeBackpackFromCart() {
        wait.until(
                ExpectedConditions.elementToBeClickable(removeBackpackButton)
        ).click();
    }

    public boolean isCartBadgeVisible() {
        return !driver.findElements(cartBadge).isEmpty();
    }

    public String getBackpackButtonText() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(backpackButton)
        ).getText();
    }
}
