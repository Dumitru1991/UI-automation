
package com.dumi.qa.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class CheckoutPage {

    private final WebDriver driver;
    private final WebDriverWait wait;

    private final By firstNameField = By.id("first-name");
    private final By lastNameField = By.id("last-name");
    private final By postalCodeField = By.id("postal-code");
    private final By continueButton = By.id("continue");
    private final By finishButton = By.id("finish");
    private final By errorMessage =
            By.cssSelector("[data-test='error']");
    private final By confirmationMessage =
            By.className("complete-header");
    private final By overviewItemName =
            By.className("inventory_item_name");
    private final By overviewItemPrice =
            By.className("inventory_item_price");

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
        wait.until(
                ExpectedConditions.visibilityOfElementLocated(firstNameField)
        ).sendKeys(firstName);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(lastNameField)
        ).sendKeys(lastName);

        wait.until(
                ExpectedConditions.visibilityOfElementLocated(postalCodeField)
        ).sendKeys(postalCode);
    }

    public void clickContinue() {
        wait.until(
                ExpectedConditions.elementToBeClickable(continueButton)
        ).click();
    }

    public String getErrorMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(errorMessage)
        ).getText();
    }

    public void finishCheckout() {
        wait.until(
                ExpectedConditions.elementToBeClickable(finishButton)
        ).click();
    }

    public String getConfirmationMessage() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        confirmationMessage
                )
        ).getText();
    }

    public String getOverviewItemName() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        overviewItemName
                )
        ).getText();
    }

    public String getOverviewItemPrice() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                        overviewItemPrice
                )
        ).getText();
    }
}
