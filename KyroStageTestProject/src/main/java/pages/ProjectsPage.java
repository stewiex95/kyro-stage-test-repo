package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import components.SideNavigationPanel;
import utils.TestUtilities;

import java.time.Duration;
/**
 * Author - Harish, Purpose - Class contains objects and methods for the projects page where projects for the user are displayed. It also covers project details page for specific project.
 */
public class ProjectsPage {

    public WebDriver driver;

    public ProjectsPage(WebDriver driver) {
        this.driver = driver;
    }

    String projectsPageTitle = "KYRO - Project Home";
    String backToProjectsLinkTitle = "< Back to Projects";
    String projectsSidePanelText = "Projects";
    String nonBillableText = "Non-Billable";
    String billableText = "Billable";
    String privateText = "Private";
    String tasksText = "Tasks";
    String notesText = "Notes";
    String addTasksText = "Add Task";
    String addNoteText = "Add Note";
    String projectManagerFieldLabel = "Project Manager";
    String billingCodeFieldLabel = "Billing Code";
    String statusLabel = "Status";
    String tasksSectionDefaultPlaceholderText = "Tasks Matters. Create One!";
    String pageLayoutCommonXpath = "//main[contains(@class,'layout-page-content')]";
    String headerSectionCommonXpath = "//div[@class='MuiBox-root css-75vnm1']";
    String projectsTabsSectionXpath = "//div[@class='MuiTabs-scroller MuiTabs-fixed css-18jpbi7']//ancestor::div[contains(@class,'MuiPaper-elevation')]";
    String tasksSectionDefaultPlaceholderXpath = projectsTabsSectionXpath + "//h5[text()='" + tasksSectionDefaultPlaceholderText + "']";
    String currentlySelectedTabXpath = projectsTabsSectionXpath + "//div[@role='tablist']//button[@aria-selected='true']";
    String descriptionHeader = "Description";
    String descriptionSectionCommonXpath = "//h4[text()='" + descriptionHeader + "']";

    public WebElement backToProjectsLink() {
        return driver.findElement(By.xpath("//nav[@aria-label='breadcrumb']//div[normalize-space()='" + backToProjectsLinkTitle + "']"));
    }
    public WebElement projectsHeaderSection(String projectTitle) {
        return driver.findElement(By.xpath(pageLayoutCommonXpath + "//h3[text()='" + projectTitle + "']//ancestor::div[@class='MuiBox-root css-75vnm1']"));
    }
    public WebElement statusInHeaderSection(String statusTitle) {
        return driver.findElement(By.xpath(headerSectionCommonXpath + "//span[@class='MuiChip-label MuiChip-labelSmall css-tavflp'][text()='" + statusTitle + "']"));
    }
    public WebElement nonBillableTextInHeader() {
        return driver.findElement(By.xpath(headerSectionCommonXpath + "//iconify-icon[@icon='arcticons:moneybuster']//following-sibling::span[text()='" + nonBillableText + "']"));
    }
    public WebElement billableTextInHeader() {
        return driver.findElement(By.xpath(headerSectionCommonXpath + "//iconify-icon[@icon='arcticons:moneytracker']//following-sibling::span[text()='" + billableText + "']"));
    }
    public WebElement privateTextInHeader() {
        return driver.findElement(By.xpath(headerSectionCommonXpath + "//iconify-icon[@icon='material-symbols:lock']//following-sibling::span[text()='" + privateText + "']"));
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
    public WebElement currentlySelectedTab() {
        return driver.findElement(By.xpath(currentlySelectedTabXpath));
    }
    public WebElement tasksSectionPlaceholder() {
        return driver.findElement(By.xpath(tasksSectionDefaultPlaceholderXpath));
    }
    public WebElement notesSectionTextArea() {
        return driver.findElement(By.xpath(projectsTabsSectionXpath + "//textarea[@name='comment']"));
    }
    public WebElement addNoteButtonInTabsSection() {
        return driver.findElement(By.xpath(projectsTabsSectionXpath + "//button[normalize-space()='" + addNoteText + "']"));
    }
    public WebElement addTaskButtonInTabsSection() {
        return driver.findElement(By.xpath(projectsTabsSectionXpath + "//button[normalize-space()='" + addTasksText + "']"));
    }
    public String projectManagerAssignedToProjectXpath = "//p[text()='" + projectManagerFieldLabel + "']//ancestor::div[contains(@class,'MuiGrid-container')]//input[@type='text']";
    public WebElement projectManagerAssignedToProject() {
        return driver.findElement(By.xpath(projectManagerAssignedToProjectXpath));
    }
    public String billingCodeAssignedToProjectXpath = "//p[text()='" + billingCodeFieldLabel + "']//ancestor::div[contains(@class,'MuiGrid-container')]//input[@type='text']";

    public WebElement billingCodeAssignedToProject() {
        return driver.findElement(By.xpath(billingCodeAssignedToProjectXpath));
    }
    public String projectStatusInSideGridDropdownXpath = "//p[text()='" + statusLabel + "']//ancestor::div[contains(@class,'MuiGrid-container')]//div[@aria-haspopup='listbox']";

    public WebElement projectStatusInSideGridDropdown() {
        return driver.findElement(By.xpath(projectStatusInSideGridDropdownXpath));
    }
    public void waitForPageLoad() {
        TestUtilities.fluentlyWaitForElementToAppear(driver, By.xpath(pageLayoutCommonXpath));
        String currentTitle = driver.getTitle();
        Assert.assertEquals(projectsPageTitle, currentTitle, "Page title is incorrect. Not on the Projects page.");
        SideNavigationPanel sidePanel = new SideNavigationPanel(driver);
        sidePanel.waitForComponentToLoad();
        Assert.assertTrue(sidePanel.currentlyActiveNavigationLink(projectsSidePanelText).isDisplayed(), "Home - is not selected in side menu navigation panel.");
    }
    public boolean isBackToProjectsNavigationLinkPresent() {
        return backToProjectsLink().isDisplayed();
    }

    public void verifySpecificProjectDetailsPageIsOpen(String projectTitle) {
        Assert.assertTrue(projectsHeaderSection(projectTitle).isDisplayed(), "Project title is incorrect in the header section.");

    }

    public void verifyProjectStatusInHeaderSection(String statusLabel) {
        Assert.assertTrue(statusInHeaderSection(statusLabel).isDisplayed(), "Status of project is not - " + statusLabel + " in header section");
    }

    public boolean isProjectBillable() {
        boolean billable = billableTextInHeader().isDisplayed();
        return billable || !nonBillableTextInHeader().isDisplayed();
    }

    public boolean isProjectPrivate() {
        return privateTextInHeader().isDisplayed();
    }

    public String getDescriptionByExpandingSection() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(3));
        wait.until(ExpectedConditions.elementToBeClickable(expandDescriptionIcon()));
        expandDescriptionIcon().click();
        wait.until(ExpectedConditions.visibilityOf(collapseDescriptionIcon()));
        wait.until(ExpectedConditions.visibilityOf(descriptionTextArea()));
        return descriptionTextArea().getText().trim();
    }

    public void verifyTasksTabIsOpenByDefaultForProject() {
        String currentlySelectedTabTitle = currentlySelectedTab().getText().trim();
        Assert.assertEquals(currentlySelectedTabTitle, tasksText, "Tasks tab is not currently selected");
        Assert.assertTrue(tasksSectionPlaceholder().isDisplayed(), "Tasks section does not have the default placeholder.");
        Assert.assertTrue(addTaskButtonInTabsSection().isDisplayed(), "Tasks section does not have the 'Add tasks' button");
    }

    public void verifyProjectManagerAssignedToProject(String expectedProjectManagerName) {
        TestUtilities.fluentlyWaitForElementToAppear(driver, By.xpath(projectManagerAssignedToProjectXpath));
        String managerName = projectManagerAssignedToProject().getAttribute("value").trim();
        Assert.assertEquals(managerName, expectedProjectManagerName, "Project Manager assigned to project is incorrect.");
    }

    public void verifyBillingCodeAssignedToProject(String expectedBillingCode) {
        TestUtilities.fluentlyWaitForElementToAppear(driver, By.xpath(billingCodeAssignedToProjectXpath));
        String code = billingCodeAssignedToProject().getAttribute("value").trim();
        Assert.assertEquals(code, expectedBillingCode, "Billing Code assigned to project is incorrect.");
    }

    public void verifyStatusOfTheProjectInTheSideGridDropdown(String expectedStatus) {
        TestUtilities.fluentlyWaitForElementToAppear(driver, By.xpath(projectStatusInSideGridDropdownXpath));
        String statusName = projectStatusInSideGridDropdown().getText().trim();
        Assert.assertEquals(statusName, expectedStatus, "Status of the project in the side grid dropdown is incorrect.");
    }

    public void verifyNotesTabIsOpenByDefaultForProject() {
        String currentlySelectedTabTitle = currentlySelectedTab().getText().trim();
        Assert.assertEquals(currentlySelectedTabTitle, notesText, "Notes tab is not currently selected");
        Assert.assertTrue(notesSectionTextArea().isDisplayed(), "Notes section does not have the textarea.");
        Assert.assertTrue(addNoteButtonInTabsSection().isDisplayed(), "Notes section does not have the 'Add Note' button");
    }
}
