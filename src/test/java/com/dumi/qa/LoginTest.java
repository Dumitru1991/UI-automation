
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

    @Test
    public void invalidLoginTest() {

        WebDriver driver = new ChromeDriver();

        try {
            driver.get("https://www.saucedemo.com/");
            driver.findElement(By.id("user-name")).sendKeys("invalid_user");
            driver.findElement(By.id("password")).sendKeys("wrong_password");
            driver.findElement(By.id("login-button")).click();

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
