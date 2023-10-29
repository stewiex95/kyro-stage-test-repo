package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.NoSuchElementException;

public abstract class TestUtilities {
    public static void fluentlyWaitForElementToAppear(WebDriver driver, By by) {
        Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }
    public static void fluentlyWaitForElementToAppear(WebDriver driver, By by, int timeoutInSecs) {
        Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(timeoutInSecs)).pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static void fluentlyWaitForElementToDisappear(WebDriver driver, By by) {
        Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(10)).pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }
    public static void fluentlyWaitForElementToDisappear(WebDriver driver, By by, int timeoutInSecs) {
        Wait<WebDriver> wait = new FluentWait<>(driver).withTimeout(Duration.ofSeconds(timeoutInSecs)).pollingEvery(Duration.ofSeconds(5)).ignoring(NoSuchElementException.class);
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public static String captureExtentReportsFailure(WebDriver driver) throws IOException {
        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);
        File destination = new File("./src/test/reports/ExtentReports/FailureScreenshots/failure_ss.png");
        FileUtils.copyFile(source, destination);
        return destination.getAbsolutePath();
    }
}
