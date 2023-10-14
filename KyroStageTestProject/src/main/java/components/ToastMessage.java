package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.TestUtilities;

import java.time.Duration;
/**
 * Author - Harish, Purpose - Class contains objects and methods for toast message which is thrown for project deletion or creation or other information changes
 */
public class ToastMessage {

    public WebDriver driver;

    public ToastMessage(WebDriver driver) {
        this.driver = driver;
    }

    String projectAddedToast = "project created successfully";
    String projectDeletedToast = "Project Deleted Successfully";
    String toastContainerCommonXpath = "//div[@role='alert']";

    String addedToastXpath = toastContainerCommonXpath + "[normalize-space()='" + projectAddedToast + "']";

    public WebElement addedToast() {
        return driver.findElement(By.xpath(addedToastXpath));
    }

    String deletedToastXpath = toastContainerCommonXpath + "[normalize-space()='" + projectDeletedToast + "']";
    public WebElement deletedToast() {
        return driver.findElement(By.xpath(deletedToastXpath));
    }

    public void waitForProjectAddedToast(int timeOutInSecs) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSecs));
        wait.until(ExpectedConditions.visibilityOf(addedToast()));
    }

    public void verifyProjectDeletedToast(int timeOutInSecs) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSecs));
        wait.until(ExpectedConditions.visibilityOf(deletedToast()));
    }

}
