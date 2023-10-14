package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.TestUtilities;

import java.time.Duration;
/**
 * Author - Harish, Purpose - Class contains objects and methods for the login page where user can sign in or sign up
 */
public class LoginPage {

    public WebDriver driver;
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }
    String pageTitle = "Sign In with KYRO";

    String userEmailXpath = "//input[@id='1-email']";
    public WebElement userNameField() {
        return driver.findElement(By.xpath(userEmailXpath));
    }
    public WebElement passwordField() {
        return driver.findElement(By.xpath("//input[@id='1-password']"));
    }
    public WebElement signInBtn() {
        return driver.findElement(By.xpath("//button[@id='1-submit']"));
    }
    public void waitForPageLoad() {
        TestUtilities.fluentlyWaitForElementToAppear(driver, By.xpath(userEmailXpath), 15);
        String currentTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, currentTitle, "Page title is incorrect. Not on the Login page.");
    }
    public void enterDetailsAndSignIn(String userEmail, String password){
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(userNameField()));
        userNameField().sendKeys(userEmail);
        wait.until(ExpectedConditions.elementToBeClickable(passwordField()));
        passwordField().sendKeys(password);
        wait.until(ExpectedConditions.elementToBeClickable(signInBtn()));
        signInBtn().click();
        wait.until(ExpectedConditions.invisibilityOf(userNameField()));
    }

}
