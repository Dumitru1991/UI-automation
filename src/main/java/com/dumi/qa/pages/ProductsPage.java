
package com.dumi.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class ProductsPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By pageTitle = By.className("title");
    private final By backpackButton =
        By.id("add-to-cart-sauce-labs-backpack");
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
            ExpectedConditions.elementToBeClickable(By.id("remove-sauce-labs-backpack"))
    ).click();
}

    public boolean isCartBadgeVisible() {
    return driver.findElements(
        By.className("shopping_cart_badge")
    ).size() > 0;
}

public String getBackpackButtonText() {
    return wait.until(
        ExpectedConditions.visibilityOfElementLocated(backpackButton)
    ).getText();
}

}
