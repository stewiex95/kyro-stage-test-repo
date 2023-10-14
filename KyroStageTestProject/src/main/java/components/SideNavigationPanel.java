package components;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.TestUtilities;

import java.time.Duration;
/**
 * Author - Harish, Purpose - Class contains objects and methods for Side Navigation Panel for navigating to Home, Projects, Feed etc..
 */
public class SideNavigationPanel {

    public WebDriver driver;

    public SideNavigationPanel(WebDriver driver) {
        this.driver = driver;
    }

    String verticalSideNavigationCommonPath = "//div[contains(@class,'layout-vertical-nav')]";

    public WebElement navigationLinkPath(String pageLinkTitle) {
        return driver.findElement(By.xpath(verticalSideNavigationCommonPath + "//span[text()='" + pageLinkTitle + "']//ancestor::a[contains(@class,'ItemButton')]"));
    }

    public WebElement currentlyActiveNavigationLink(String pageLinkTitle) {
        return driver.findElement(By.xpath(verticalSideNavigationCommonPath + "//a[contains(@class,'active')]//span[text()='" + pageLinkTitle + "']"));
    }

    public void waitForComponentToLoad() {
        TestUtilities.fluentlyWaitForElementToAppear(driver, By.xpath(verticalSideNavigationCommonPath));
    }

    public void navigateToLinkFromSidePanel(String pageLinkTitle) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        WebElement navigationLinkPath = navigationLinkPath(pageLinkTitle);
        wait.until(ExpectedConditions.elementToBeClickable(navigationLinkPath));
        navigationLinkPath.click();
    }

}
