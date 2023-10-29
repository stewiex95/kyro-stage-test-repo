package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.TestUtilities;

import java.time.Duration;

/**
 * Author - Harish, Purpose - Class contains objects and methods for the sticky header which has the welcome message, notification bell, create button and profile options
 */
public class StickyHeader {

    public WebDriver driver;

    public StickyHeader(WebDriver driver) {
        this.driver = driver;
    }

    String commonHeaderXpath = "//header//div[contains(@class,'navbar-content-container')]";
    String createProjectButtonLabel = "Create Project";
    String bellNotificationIconXpath = commonHeaderXpath + "//span[@aria-controls='customized-menu']";

    String profileIconXpath = commonHeaderXpath + "//div[contains(@class,'MuiAvatar')]";

    public WebElement profileIcon() {
        return driver.findElement(By.xpath(profileIconXpath));
    }

    public WebElement stickyHeader() {
        return driver.findElement(By.xpath(commonHeaderXpath));
    }

    public WebElement userNameInWelcomeHeader() {
        return driver.findElement(By.xpath(commonHeaderXpath + "//div[@class='MuiBox-root css-10gfp3q']//h2[2]"));
    }

    public WebElement createProjectButton() {
        return driver.findElement(By.xpath("//button[contains(text(),'" + createProjectButtonLabel + "')]"));
    }

    public WebElement bellNotification() {
        return driver.findElement(By.xpath(bellNotificationIconXpath));
    }

    public String getUserNameFromWelcomeHeader() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(stickyHeader()));
        return userNameInWelcomeHeader().getText().trim();
    }

    public void waitUntilNotificationsCountIsUpdated(String expectedCount) {
        TestUtilities.fluentlyWaitForElementToAppear(driver, By.xpath(bellNotificationIconXpath + "//span[contains(@class,'MuiBadge-badge')][normalize-space()='" + expectedCount + "']"));
    }

    public void logoutOfApp() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(profileIcon()));
        profileIcon().click();
        new UserProfileMenuDropdown(driver).clickOnLogout();
    }

    public void openNotificationsFloaterModal() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.visibilityOf(bellNotification()));
        bellNotification().click();
        NotificationsContainer notificationsContainer = new NotificationsContainer(driver);
        notificationsContainer.waitForComponentToLoad(3);
    }

}
