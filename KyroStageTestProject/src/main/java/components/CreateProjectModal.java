package components;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import utils.TestUtilities;

import java.time.Duration;

/**
 * Author - Harish, Purpose - Class contains objects and methods for create project modal
 */
public class CreateProjectModal {

    public WebDriver driver;

    public CreateProjectModal(WebDriver driver) {
        this.driver = driver;
    }

    String createProjectTitle = "Create Project";
    String projectConfigurationTitle = "Project Configuration";
    String projectDetailsTitle = "Project Details";
    String projectAccessGridTitle = "Project Access";
    String billingTypeGridTitle = "Billing Type";
    String tasksGridTitle = "Tasks";
    String markAsPublicLabel = "Mark as Public";
    String markAsNonBillableLabel = "Mark as Non Billable";
    String enableTasksLabel = "Enable Tasks";
    String nextButtonLabel = "Next";
    String submitButtonLabel = "Submit";
    String commonModalXpath = "//h3[text()='" + createProjectTitle + "']//ancestor::div[@role='dialog']";
    String projectAccessGridXpath = "//h4[text()='" + projectAccessGridTitle + "']//parent::div[contains(@class,'grid')]";
    String TasksGridXpath = "//h4[text()='" + tasksGridTitle + "']//parent::div[contains(@class,'grid')]";
    String billingTypeGridXpath = "//h4[text()='" + billingTypeGridTitle + "']//parent::div[contains(@class,'grid')]";

    public WebElement createProjectModalHeader() {
        return driver.findElement(By.xpath(commonModalXpath));
    }

    public WebElement markProjectAccessAsPublicCheckbox() {
        return driver.findElement(By.xpath(projectAccessGridXpath + "//span[text()='" + markAsPublicLabel + "']//parent::label"));
    }

    public WebElement markAsNonBillableCheckbox() {
        return driver.findElement(By.xpath(billingTypeGridXpath + "//span[text()='" + markAsNonBillableLabel + "']//parent::label"));
    }

    public WebElement enableTasksCheckbox() {
        return driver.findElement(By.xpath(TasksGridXpath + "//span[text()='" + enableTasksLabel + "']//parent::label"));
    }

    String clientsDropdownInputBoxXpath = commonModalXpath + "//input[@id='client']";

    public WebElement clientsDropdownInputBox() {
        return driver.findElement(By.xpath(clientsDropdownInputBoxXpath));
    }

    public String clientsOptionsInDropdownXpath(String option) {
        return "//ul[@id='client-listbox']//h6[normalize-space()='" + option + "']//ancestor::li";
    }

    public WebElement clientsOptionsInDropdown(String option) {
        return driver.findElement(By.xpath(clientsOptionsInDropdownXpath(option)));
    }

    String projectTypeDropdownInputBoxXpath = commonModalXpath + "//input[@id='project-type']";

    public WebElement projectTypeDropdownInputBox() {
        return driver.findElement(By.xpath(projectTypeDropdownInputBoxXpath));
    }

    public String projectTypeOptionsInDropdownXpath(String option) {
        return "//ul[@id='project-type-listbox']//li[normalize-space()='" + option + "']";
    }

    public WebElement projectTypeOptionsInDropdown(String option) {
        return driver.findElement(By.xpath(projectTypeOptionsInDropdownXpath(option)));
    }

    String nextButtonXpath =commonModalXpath + "//button[text()='" + nextButtonLabel + "']";
    public WebElement nextButton() {
        return driver.findElement(By.xpath(nextButtonXpath));
    }
    String submitButtonXpath =commonModalXpath + "//button[text()='" + submitButtonLabel + "']";
    public WebElement submitButton() {
        return driver.findElement(By.xpath(submitButtonXpath));
    }

    public WebElement projectTitleInputBox() {
        return driver.findElement(By.xpath(commonModalXpath + "//input[@name='project_name']"));
    }

    String projectManagerDropdownInputBoxXpath = commonModalXpath + "//input[@id='project-manager']";

    public WebElement projectManagerDropdownInputBox() {
        return driver.findElement(By.xpath(projectManagerDropdownInputBoxXpath));
    }

    public String projectManagerOptionsInDropdownXpath(String projectManagerName) {
        return "//ul[@id='project-manager-listbox']//h6[normalize-space()='" + projectManagerName + "']//ancestor::li";
    }

    public WebElement projectManagerOptionsInDropdown(String projectManagerName) {
        return driver.findElement(By.xpath(projectManagerOptionsInDropdownXpath(projectManagerName)));
    }

    public WebElement billingCodeInputBox() {
        return driver.findElement(By.xpath(commonModalXpath + "//input[@name='project_number']"));
    }

    public WebElement projectDescriptionTextArea() {
        return driver.findElement(By.xpath(commonModalXpath + "//textarea[@name='description']"));
    }

    public void waitForComponentToLoad(int timeOutInSecs) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeOutInSecs));
        wait.until(ExpectedConditions.visibilityOf(createProjectModalHeader()));
    }

    public void clickCheckboxAndVerifySelected(WebElement checkbox) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(checkbox));
        checkbox.click();
    }

    public void searchAndSelectClientFromDropdown(String clientLabel) {
        TestUtilities.fluentlyWaitForElementToAppear(driver, By.xpath(clientsDropdownInputBoxXpath));
        clientsDropdownInputBox().sendKeys(Keys.chord(Keys.CONTROL, "a"));
        clientsDropdownInputBox().sendKeys(clientLabel);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(clientsOptionsInDropdown(clientLabel)));
        clientsOptionsInDropdown(clientLabel).click();
        TestUtilities.fluentlyWaitForElementToDisappear(driver, By.xpath(clientsOptionsInDropdownXpath(clientLabel)));
        String selectedOptionText = clientsDropdownInputBox().getAttribute("value");
        Assert.assertEquals(selectedOptionText, clientLabel, "Client Dropdown option selected is incorrect.");
    }

    public void searchAndSelectProjectTypeFromDropdown(String typeLabel) {
        TestUtilities.fluentlyWaitForElementToAppear(driver, By.xpath(projectTypeDropdownInputBoxXpath));
        projectTypeDropdownInputBox().sendKeys(Keys.chord(Keys.CONTROL, "a"));
        projectTypeDropdownInputBox().sendKeys(typeLabel);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(projectTypeOptionsInDropdown(typeLabel)));
        projectTypeOptionsInDropdown(typeLabel).click();
        TestUtilities.fluentlyWaitForElementToDisappear(driver, By.xpath(projectTypeOptionsInDropdownXpath(typeLabel)));
        String selectedOptionText = projectTypeDropdownInputBox().getAttribute("value");
        Assert.assertEquals(selectedOptionText, typeLabel, "Project type Dropdown option selected is incorrect.");
    }

    public void searchAndSelectProjectManagerFromDropdown(String managerName) {
        TestUtilities.fluentlyWaitForElementToAppear(driver, By.xpath(projectManagerDropdownInputBoxXpath));
        projectManagerDropdownInputBox().sendKeys(Keys.chord(Keys.CONTROL, "a"));
        projectManagerDropdownInputBox().sendKeys(managerName);
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(projectManagerOptionsInDropdown(managerName)));
        projectManagerOptionsInDropdown(managerName).click();
        TestUtilities.fluentlyWaitForElementToDisappear(driver, By.xpath(projectManagerOptionsInDropdownXpath(managerName)));
        String selectedOptionText = projectManagerDropdownInputBox().getAttribute("value");
        Assert.assertEquals(selectedOptionText, managerName, "Project manager Dropdown option selected is incorrect.");
    }

    public void completeProjectConfigurationForm(String client, String projectType, boolean markAsPublic, boolean markAsNonBillable, boolean enableTasks) throws InterruptedException {
        TestUtilities.fluentlyWaitForElementToAppear(driver, By.xpath(commonModalXpath + "//span[text()='" + projectConfigurationTitle + "'][contains(@class,'Mui-active')]"));
        // Select Client
        searchAndSelectClientFromDropdown(client);
        // Select Project type
        searchAndSelectProjectTypeFromDropdown(projectType);
        // Check / uncheck Public, billable and enable tasks
        if (markAsPublic) {
            clickCheckboxAndVerifySelected(markProjectAccessAsPublicCheckbox());
        } else if (markAsNonBillable) {
            clickCheckboxAndVerifySelected(markAsNonBillableCheckbox());
        } else if (enableTasks) {
            clickCheckboxAndVerifySelected(enableTasksCheckbox());
        }
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.elementToBeClickable(nextButton()));
        nextButton().click();
        TestUtilities.fluentlyWaitForElementToDisappear(driver, By.xpath(nextButtonXpath));
    }

    public void completeProjectDetailsForm(String projectTitle, String projectManager, String billingCode, String description) {
        TestUtilities.fluentlyWaitForElementToAppear(driver, By.xpath(commonModalXpath + "//span[text()='" + projectDetailsTitle + "'][contains(@class,'Mui-active')]"));
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        // Enter Project title
        wait.until(ExpectedConditions.elementToBeClickable(projectTitleInputBox()));
        projectTitleInputBox().sendKeys(projectTitle);
        // Pick project manager
        searchAndSelectProjectManagerFromDropdown(projectManager);
        // Enter billing code
        wait.until(ExpectedConditions.elementToBeClickable(billingCodeInputBox()));
        billingCodeInputBox().sendKeys(billingCode);
        // Enter description
        wait.until(ExpectedConditions.elementToBeClickable(projectDescriptionTextArea()));
        projectDescriptionTextArea().sendKeys(description);
        wait.until(ExpectedConditions.elementToBeClickable(submitButton()));
        submitButton().click();
        wait.until(ExpectedConditions.invisibilityOf(submitButton()));
        ToastMessage toast = new ToastMessage(driver);
        toast.waitForProjectAddedToast(5);
    }

}
