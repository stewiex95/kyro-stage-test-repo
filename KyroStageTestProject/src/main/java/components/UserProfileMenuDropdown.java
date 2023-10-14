package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.TestUtilities;

import java.time.Duration;
/**
 * Author - Harish, Purpose - Class contains objects and methods for user profile options from the profile icon dropdown. This includes logout, profile settings and logged-in username
 */
public class UserProfileMenuDropdown {

    public WebDriver driver;

    public UserProfileMenuDropdown(WebDriver driver) {
        this.driver = driver;
    }

    String menuCommonXpath = "//ul[@role='menu']";
    String profileLabel = "Profile";
    String logoutLabel = "Logout";

    public WebElement menu() {
        return driver.findElement(By.xpath(menuCommonXpath));
    }

    public WebElement loggedInUserNameInMenu() {
        return driver.findElement(By.xpath(menuCommonXpath + "//span[contains(@class,'MuiTypography-h7')]"));
    }

    public WebElement profileOption() {
        return driver.findElement(By.xpath(menuCommonXpath + "//li[@role='menuitem'][normalize-space()='" + profileLabel + "']"));
    }

    String logoutOptionXpath = menuCommonXpath + "//li[@role='menuitem'][normalize-space()='" + logoutLabel + "']";

    public WebElement logoutOption() {
        return driver.findElement(By.xpath(logoutOptionXpath));
    }

    public WebElement loadingProgressBar() {
        return driver.findElement(By.xpath("//span[@role='progressbar']"));
    }

    public void clickOnLogout() {
        TestUtilities.fluentlyWaitForElementToAppear(driver, By.xpath(menuCommonXpath));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(logoutOption()));
        logoutOption().click();
        TestUtilities.fluentlyWaitForElementToDisappear(driver, By.xpath(logoutOptionXpath));
    }

}
