
package com.dumi.qa;

import com.dumi.qa.config.ConfigReader;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.HashMap;
import java.util.Map;

public class BaseTest {

    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();

        // Disable Chrome password manager and password leak warnings.
        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);

        options.setExperimentalOption("prefs", prefs);
        options.addArguments("--disable-features=PasswordLeakDetection");

        // Read headless mode from config.properties.
        if (ConfigReader.getBoolean("headless")) {
            options.addArguments("--headless=new");
            options.addArguments("--window-size=1920,1080");
        }

        // Start Chrome and open the application.
        driver = new ChromeDriver(options);

        if (!ConfigReader.getBoolean("headless")) {
            driver.manage().window().maximize();
        }

        driver.get(ConfigReader.get("base.url"));
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        try {
            // Capture screenshots only when a test fails.
            if (result.getStatus() == ITestResult.FAILURE
                    && driver instanceof TakesScreenshot) {

                byte[] screenshot = ((TakesScreenshot) driver)
                        .getScreenshotAs(OutputType.BYTES);

                // Attach the screenshot to the Allure report.
                Allure.attachment(
                        "Failure Screenshot",
                        new ByteArrayInputStream(screenshot)
                );

                // Also save the screenshot locally.
                Path directory = Path.of("target", "screenshots");
                Files.createDirectories(directory);

                String fileName = result.getMethod().getMethodName()
                        + "-" + System.currentTimeMillis() + ".png";

                Path screenshotPath = directory.resolve(fileName);

                Files.copy(
                        new ByteArrayInputStream(screenshot),
                        screenshotPath,
                        StandardCopyOption.REPLACE_EXISTING
                );

                System.out.println("Screenshot saved: " + screenshotPath);
            }
        } catch (IOException | RuntimeException e) {
            System.err.println("Screenshot handling failed: " + e.getMessage());
        } finally {
            // Always close the browser, even if screenshot handling fails.
            if (driver != null) {
                try {
                    driver.quit();
                } catch (RuntimeException e) {
                    System.err.println("Browser cleanup failed: " + e.getMessage());
                } finally {
                    driver = null;
                }
            }
        }
    }
}
