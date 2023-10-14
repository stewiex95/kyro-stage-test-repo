package pages;

import components.SideNavigationPanel;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.TestUtilities;

import java.time.Duration;
/**
 * Author - Harish, Purpose - Class contains objects and methods for the home page which is the landing page for all users. It also displays the project high level details if any for the user.
 * It also covers new user experience
 */
public class HomePage {

    public WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
    }

    String pageTitle = "KYRO - Home";
    String getStartedHeader = "Get started with a New Project";
    String createProjectButtonLabel = "Create Project";
    String homePageSidePanelText = "Home";
    String descriptionHeader = "Description";
    String projectsHeader = "Projects";
    String pageLayoutCommonXpath = "//main[contains(@class,'layout-page-content')]";
    String descriptionSectionCommonXpath = "//h4[text()='" + descriptionHeader + "']";

    public WebElement selectedProjectInHeaderDropdown() {
        return driver.findElement(By.xpath(pageLayoutCommonXpath + "//input[@id='project-list-id']"));
    }

    String startByCreatingNewProjectXpath = pageLayoutCommonXpath + "//h1[normalize-space()='" + getStartedHeader + "']";

    public WebElement startByCreatingNewProjectHeader() {
        return driver.findElement(By.xpath(startByCreatingNewProjectXpath));
    }

    public WebElement createProjectButtonInPage() {
        return driver.findElement(By.xpath(pageLayoutCommonXpath + "//span[text()='" + createProjectButtonLabel + "']//parent::button"));
    }

    public WebElement expandDescriptionIcon() {
        return driver.findElement(By.xpath(descriptionSectionCommonXpath + "//following-sibling::button[@id='show-description-icon']//iconify-icon[contains(@icon,'down-drop')]"));
    }

    public WebElement collapseDescriptionIcon() {
        return driver.findElement(By.xpath(descriptionSectionCommonXpath + "//following-sibling::button[@id='show-description-icon']//iconify-icon[contains(@icon,'up-drop')]"));
    }

    public WebElement descriptionTextArea() {
        return driver.findElement(By.xpath(descriptionSectionCommonXpath + "//ancestor::div[contains(@class,'MuiPaper-root') and contains(@class,'Mui-expanded')]//textarea[@placeholder='Add a description...']"));
    }

    public WebElement projectsHeaderSection() {
        return driver.findElement(By.xpath(pageLayoutCommonXpath + "//span[text()='" + projectsHeader + "']//ancestor::div[contains(@class,'MuiGrid-container')]"));
    }

    public void waitForPageLoad() {
        TestUtilities.fluentlyWaitForElementToAppear(driver, By.xpath(pageLayoutCommonXpath), 30);
        String currentTitle = driver.getTitle();
        Assert.assertEquals(pageTitle, currentTitle, "Page title is incorrect. Not on the Home page.");
        SideNavigationPanel sidePanel = new SideNavigationPanel(driver);
        sidePanel.waitForComponentToLoad();
        Assert.assertTrue(sidePanel.currentlyActiveNavigationLink(homePageSidePanelText).isDisplayed(), "Home - is not selected in side menu navigation panel.");
    }

    public String getDescriptionByExpandingSection() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(expandDescriptionIcon()));
        expandDescriptionIcon().click();
        wait.until(ExpectedConditions.visibilityOf(collapseDescriptionIcon()));
        wait.until(ExpectedConditions.visibilityOf(descriptionTextArea()));
        return descriptionTextArea().getText().trim();
    }

    public void openCreateProjectModalFromPage() {
        TestUtilities.fluentlyWaitForElementToAppear(driver, By.xpath(startByCreatingNewProjectXpath));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(createProjectButtonInPage()));
        createProjectButtonInPage().click();
    }

    public void verifyHomePageOpensWithAssignedProject(String projectTitle) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOf(projectsHeaderSection()));
        verifyCurrentlySelectedProjectInHeaderSection(projectTitle);
    }

    public void verifyCurrentlySelectedProjectInHeaderSection(String projectTitle) {
        String selectedProject = selectedProjectInHeaderDropdown().getAttribute("value").trim();
        Assert.assertEquals(selectedProject, projectTitle, "Selected project in the header section is incorrect.");
    }
}
