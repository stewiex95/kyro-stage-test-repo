package web_app_tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import components.CreateProjectModal;
import components.NotificationsContainer;
import components.StickyHeader;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.*;
import pages.HomePage;
import pages.LoginPage;
import pages.ProjectsPage;
import utils.TestUtilities;

import java.io.IOException;

/**
 * Author - Harish, Purpose - Test class which contains test methods to test the notification experience of the project manager after a newly assigned project
 */
public class KyroTestCaseClass {

    private WebDriver driver;
    private ExtentReports extent;
    private ExtentSparkReporter spark;
    private ExtentTest extentTest;

    @BeforeSuite
    public void setUpExtentHtmlReporter() {
        extent = new ExtentReports();
        spark = new ExtentSparkReporter("src/test/reports/ExtentReports/extentTestReport.html");
        extent.attachReporter(spark);
        extent.setSystemInfo("OS", System.getProperty("os.name"));
        extent.setSystemInfo("Java Version", System.getProperty("java.version"));
    }

    @BeforeClass
    public void setUpDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeMethod
    public void initializeDriverAndFetchUrl(ITestContext context) {
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://stage.kyro.ai");
    }

    /**
     * Author - Harish, Purpose - Login method which handles logging in and landing on home page. Verifies the welcome message.
     */
    private void login(String email, String password, String expectedUserName) {
        LoginPage loginPage = new LoginPage(driver);
        loginPage.waitForPageLoad();
        loginPage.enterDetailsAndSignIn(email, password);
        HomePage homePage = new HomePage(driver);
        homePage.waitForPageLoad();
        StickyHeader stickyHeader = new StickyHeader(driver);
        String userNameInWelcomeHeader = stickyHeader.getUserNameFromWelcomeHeader();
        Assert.assertEquals(userNameInWelcomeHeader, expectedUserName, "Logged in user name is incorrect in the sticky header. Expected - " + expectedUserName + ", Actual - " + userNameInWelcomeHeader);
    }

    /**
     * Author - Harish, Purpose - Logout method which logs user out of the app from the profile dropdown options
     */
    private void logout() {
        StickyHeader stickyHeader = new StickyHeader(driver);
        stickyHeader.logoutOfApp();
    }

    @DataProvider(name = "TC01_Data")
    public Object[][] getTC01_Data() {
        return new Object[][]{
                {"harishs98+user@gmail.com", "Monkey@1!", "Harold Graves"}
        };
    }

    /**
     * Author: Harish
     * Purpose: Test Case 1 - User1 - Create Project and Assign to User2
     * Steps:
     * 1. Sign in as User1.
     * 2. Create a new project.
     * 3. Assign the project to User2.
     * 4. Log out.
     */

    @Test(description = "Create a new project and assign it to an added member as Project Manager", priority = 1, dataProvider = "TC01_Data")
    public void userCreateProjectAndAssignToProjectManager(String loginUserEmail, String loginUserPassword, String userName) throws InterruptedException {
        extentTest = extent.createTest("KyroTestCaseClass.userCreateProjectAndAssignToProjectManager", "Create a new project and assign it to an added member as Project Manager");
        // Test data
        String projectManagerUserName = "Brit Baker";
        String projectTitle = "Focus on systematic analysis";
        String projectBillingCode = "C123#";
        String projectDescription = "Finland leads in the IPRI (8.1), LP (8.8), and PPR (8.4) scores...";
        extentTest.info("projectManagerUserName - " + projectManagerUserName + ", projectTitle - " + projectTitle + ", projectBillingCode -" + projectBillingCode + ", projectDescription - " + projectDescription);

        // Login
        extentTest.info("loginUserEmail - " + loginUserEmail + ", loginUserPassword - " + loginUserPassword + ", userName - " + userName);
        login(loginUserEmail, loginUserPassword, userName);
        extentTest.info("Login successful.");
        // Create a new project
        HomePage homePage = new HomePage(driver);
        homePage.openCreateProjectModalFromPage();
        CreateProjectModal projectModal = new CreateProjectModal(driver);
        projectModal.waitForComponentToLoad(10);
        extentTest.info("Project Configuration -> Client - Internal, Project Type - default, Mark as Private, Billable, Tasks Enabled");
        projectModal.completeProjectConfigurationForm("Internal", "default", false, false, true);
        projectModal.completeProjectDetailsForm(projectTitle, projectManagerUserName, projectBillingCode, projectDescription);
        extentTest.info("Project created.");
        // Logout
        logout();
        extentTest.pass("KyroTestCaseClass.userCreateProjectAndAssignToProjectManager - Test passed.");
    }

    @DataProvider(name = "TC02_Data")
    public Object[][] getTC02_Data() {
        return new Object[][]{
                {"harishs98+projm@gmail.com", "Monkey@1!", "Brit Baker"}
        };
    }

    /**
     * Author: Harish
     * Purpose: Test Case 2 - User2 - Validate Notification
     * Steps:
     * 1. Sign in as User2.
     * 2. Check if User2 received a notification for the assigned project.
     * 3. Log out.
     */

    @Test(description = "Log in as Project Manager and verify project notification", priority = 2, dataProvider = "TC02_Data")
    public void logInAsProjectManagerAndVerifyNotifications(String projectManagerLoginEmail, String projectManagerLoginPassword, String projectManagerUserName) {
        extentTest = extent.createTest("KyroTestCaseClass.logInAsProjectManagerAndVerifyNotifications", "Log in as Project Manager and verify project notification");
        // Test data
        String projectAuthorName = "Harold Graves";
        String projectTitle = "Focus on systematic analysis";
        extentTest.info("projectAuthorName - " + projectAuthorName + ", projectTitle - " + projectTitle);

        // Login as Project Manager
        extentTest.info("projectManagerLoginEmail - " + projectManagerLoginEmail + ", projectManagerLoginPassword - " + projectManagerLoginPassword + ", projectManagerUserName - " + projectManagerUserName);
        login(projectManagerLoginEmail, projectManagerLoginPassword, projectManagerUserName);
        extentTest.info("Login successful.");

        // Verify project notification
        StickyHeader stickyHeader = new StickyHeader(driver);
        stickyHeader.waitUntilNotificationsCountIsUpdated("1");
        extentTest.info("Number of notifications for Project manager - 1");
        stickyHeader.openNotificationsFloaterModal();
        NotificationsContainer notificationsContainer = new NotificationsContainer(driver);
        notificationsContainer.verifyAssignedToYouAProjectNotification(projectAuthorName, projectTitle);
        extentTest.info("Notification for Project manager for new project is displayed correctly.");
        notificationsContainer.closeNotificationsFloaterModal();

        // Logout
        logout();
        extentTest.pass("KyroTestCaseClass.logInAsProjectManagerAndVerifyNotifications - Test passed.");
    }

    /**
     * Author: Harish
     * Purpose: Test Case 3 - User2 - Validate Project Details
     * Steps:
     * 1. Sign in as User2.
     * 2. Click on the notification to navigate to the project details.
     * 3. Validate project details.
     * 4. Log out.
     */

    @Test(description = "Verify project notification navigation and validate the details", priority = 3, dataProvider = "TC02_Data")
    public void verifyProjectNotificationNavigation(String projectManagerLoginEmail, String projectManagerLoginPassword, String projectManagerUserName) {
        extentTest = extent.createTest("KyroTestCaseClass.verifyProjectNotificationNavigation", "Verify project notification navigation and validate the details");
        // Test data
        String projectTitle = "Focus on systematic analysis";
        String projectBillingCode = "C123#";
        String projectDescription = "Finland leads in the IPRI (8.1), LP (8.8), and PPR (8.4) scores...";
        extentTest.info("projectTitle - " + projectTitle + ", projectBillingCode -" + projectBillingCode + ", projectDescription - " + projectDescription);

        // Login as Project Manager
        extentTest.info("projectManagerLoginEmail - " + projectManagerLoginEmail + ", projectManagerLoginPassword - " + projectManagerLoginPassword + ", projectManagerUserName - " + projectManagerUserName);
        login(projectManagerLoginEmail, projectManagerLoginPassword, projectManagerUserName);
        extentTest.info("Login successful.");

        // Navigate to the project notification
        StickyHeader stickyHeader = new StickyHeader(driver);
        stickyHeader.openNotificationsFloaterModal();
        NotificationsContainer notificationsContainer = new NotificationsContainer(driver);
        notificationsContainer.clickOnTheLatestNotification();

        // Verify project details
        ProjectsPage projectsPage = new ProjectsPage(driver);
        projectsPage.waitForPageLoad();
        extentTest.info("Navigation to projects page from Notifications is done successfully");
        notificationsContainer.closeNotificationsFloaterModal();
        Assert.assertTrue(projectsPage.isBackToProjectsNavigationLinkPresent(), "Back to projects link is not displayed in the project details page.");
        projectsPage.verifySpecificProjectDetailsPageIsOpen(projectTitle);
        projectsPage.verifyProjectStatusInHeaderSection("Active");
        Assert.assertTrue(projectsPage.isProjectBillable(), "Project billable/non-billable text is incorrect for the project.");
        Assert.assertTrue(projectsPage.isProjectPrivate(), "Project visibility is set incorrectly for the project. Expected - 'Private'.");
        String descriptionInPage = projectsPage.getDescriptionByExpandingSection();
        Assert.assertEquals(descriptionInPage, projectDescription, "Description given for the project is incorrect. Expected - " + projectDescription + ", Actual - " + descriptionInPage);
        projectsPage.verifyTasksTabIsOpenByDefaultForProject(); // Tasks tab should be open since the project was created with "Enable Tasks"
        projectsPage.verifyProjectManagerAssignedToProject(projectManagerUserName);
        projectsPage.verifyBillingCodeAssignedToProject(projectBillingCode);
        projectsPage.verifyStatusOfTheProjectInTheSideGridDropdown("Active");
        extentTest.info("All project details are validated for project manager.");

        // Logout
        logout();
        extentTest.pass("KyroTestCaseClass.verifyProjectNotificationNavigation - Test passed.");
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) throws IOException {
        if(result.getStatus() == ITestResult.FAILURE)
        {
            String path = TestUtilities.captureExtentReportsFailure(driver);
            extentTest.addScreenCaptureFromPath(path);
        }

        if (driver != null) {
            driver.quit();
        }
    }

    @AfterSuite(alwaysRun = true)
    public void generateExtentReports() {
        extent.flush();
    }

}