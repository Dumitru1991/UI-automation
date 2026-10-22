
package com.dumi.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class CheckoutPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    public CheckoutPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
            driver, Duration.ofSeconds(10)
        );
    }

    public void enterCustomerDetails(
        String firstName,
        String lastName,
        String postalCode
    ) {
        driver.findElement(By.id("first-name"))
              .sendKeys(firstName);

        driver.findElement(By.id("last-name"))
              .sendKeys(lastName);

        driver.findElement(By.id("postal-code"))
              .sendKeys(postalCode);
    }

    public void clickContinue() {
        driver.findElement(By.id("continue")).click();
    }

    public String getErrorMessage() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector("[data-test='error']")
            )
        ).getText();
    }

    public void finishCheckout() {
        wait.until(
            ExpectedConditions.elementToBeClickable(
                By.id("finish")
            )
        ).click();
    }

    public String getConfirmationMessage() {
        return wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.className("complete-header")
            )
        ).getText();
    }
}
