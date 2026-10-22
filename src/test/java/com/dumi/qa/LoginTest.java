
package com.dumi.qa;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.time.Duration;

public class LoginTest {

    // TEST 1: Valid login
    @Test
    public void validLoginTest() {

        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://www.saucedemo.com/");

            driver.findElement(By.id("user-name"))
                  .sendKeys("standard_user");

            driver.findElement(By.id("password"))
                  .sendKeys("secret_sauce");

            driver.findElement(By.id("login-button"))
                  .click();

            WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(10)
            );

            WebElement productsTitle = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.className("title")
                )
            );

            Assert.assertEquals(
                productsTitle.getText(),
                "Products",
                "Products page was not displayed!"
            );

            Assert.assertTrue(
                driver.getCurrentUrl().contains("inventory.html"),
                "User was not redirected to inventory page!"
            );

        } finally {
            driver.quit();
        }
    }

    // TEST 2: Invalid login
    @Test
    public void invalidLoginTest() {

        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://www.saucedemo.com/");

            driver.findElement(By.id("user-name"))
                  .sendKeys("invalid_user");

            driver.findElement(By.id("password"))
                  .sendKeys("wrong_password");

            driver.findElement(By.id("login-button"))
                  .click();

            WebDriverWait wait = new WebDriverWait(
                driver, Duration.ofSeconds(10)
            );

            WebElement error = wait.until(
                ExpectedConditions.visibilityOfElementLocated(
                    By.cssSelector("[data-test='error']")
                )
            );

            Assert.assertTrue(
                error.isDisplayed(),
                "Expected login error was not displayed!"
            );

        } finally {
            driver.quit();
        }
    }
}
