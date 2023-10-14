package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.TestUtilities;

import java.time.Duration;
import java.util.List;
/**
 * Author - Harish, Purpose - Class contains objects and methods for notifications container
 */
public class NotificationsContainer {
    public WebDriver driver;

    public NotificationsContainer(WebDriver driver) {
        this.driver = driver;
    }

    String commonModalXpath = "//div[@data-test-id='notifications-header-title']//ancestor::div[@data-test-id='layout-wrapper']";
    String notificationsCommonXpath = "//div[@data-test-id='notifications-scroll-area']//div[@data-test-id='notification-list-item']";

    public WebElement notificationsFloaterModal() {
        return driver.findElement(By.xpath(commonModalXpath));
    }

    public WebElement specificNotificationTitle(String projectAuthor, String projectName) {
        return driver.findElement(By.xpath("//div[@data-test-id='notification-content'][normalize-space()='" + projectAuthor + " assigned you a project - " + projectName + "']"));
    }
    public List<WebElement> notificationsInModal() {
        return driver.findElements(By.xpath(notificationsCommonXpath));
    }

    public void waitForComponentToLoad(int timeOutInSecs) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSecs));
        wait.until(ExpectedConditions.visibilityOf(notificationsFloaterModal()));
    }

    public int getNumberOfNotificationsForUser() {
        TestUtilities.fluentlyWaitForElementToAppear(driver, By.xpath(commonModalXpath));
        return notificationsInModal().size();
    }

    public void clickOnTheLatestNotification() {
        TestUtilities.fluentlyWaitForElementToAppear(driver, By.xpath(notificationsCommonXpath + "[1]"), 15);
        driver.findElement(By.xpath(notificationsCommonXpath + "[1]")).click();
    }

    public void closeNotificationsFloaterModal() {
        new StickyHeader(driver).userNameInWelcomeHeader().click();
        TestUtilities.fluentlyWaitForElementToDisappear(driver, By.xpath(commonModalXpath), 15);
    }

    public void verifyAssignedToYouAProjectNotification(String projectAuthor, String projectName) {
        TestUtilities.fluentlyWaitForElementToAppear(driver, By.xpath(notificationsCommonXpath + "[1]"), 15);
        Assert.assertTrue(specificNotificationTitle(projectAuthor, projectName).isDisplayed(), "Project assigned notification is not displayed as expected.");
    }

}
