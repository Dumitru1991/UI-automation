
package com.dumi.qa;
import com.dumi.qa.config.ConfigReader;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.OutputType;
import io.qameta.allure.Allure;
import java.io.ByteArrayInputStream;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.AfterMethod;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import java.util.Map;
import java.util.HashMap;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
public void setUp() {

    ChromeOptions options = new ChromeOptions();

    // Disable Chrome password manager and password leak warnings
    Map<String, Object> prefs = new HashMap<>();

    prefs.put("credentials_enable_service", false);
    prefs.put("profile.password_manager_enabled", false);
    prefs.put("profile.password_manager_leak_detection", false);

    options.setExperimentalOption("prefs", prefs);
    options.addArguments("--disable-features=PasswordLeakDetection");

    // Headless mode from config.properties
    if (ConfigReader.getBoolean("headless")) {
        options.addArguments("--headless=new");
        options.addArguments("--window-size=1920,1080");
    }

    // Start Chrome with our options
    driver = new ChromeDriver(options);

    driver.manage().window().maximize();

    // Open URL from config.properties
    driver.get(ConfigReader.get("base.url"));
}

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {

        try {
            if (result.getStatus() == ITestResult.FAILURE
                    && driver != null) {

                File screenshot = ((TakesScreenshot) driver)
                    .getScreenshotAs(OutputType.FILE);

                Path directory =
                    Path.of("target", "screenshots");

                Files.createDirectories(directory);

                String fileName =
                    result.getMethod().getMethodName()
                    + "-" + System.currentTimeMillis()
                    + ".png";

                Files.copy(
                    screenshot.toPath(),
                    directory.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING
                );

                System.out.println(
                    "Screenshot saved: "
                    + directory.resolve(fileName)
                );
            }
        } catch (IOException | RuntimeException e) {
            System.err.println(
                "Screenshot failed: " + e.getMessage()
            );
        } finally {
            if (driver != null) {
            byte[] screenshot = ((TakesScreenshot) driver)
            .getScreenshotAs(OutputType.BYTES);

            Allure.attachment(
            "Failure Screenshot",
            new ByteArrayInputStream(screenshot)
                );} {
                driver.quit();
                driver = null;
            }
        }
    }
}
