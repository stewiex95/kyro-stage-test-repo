package utils;

import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.SkipException;

/**
 * Author - Harish, Purpose - Class contains objects and methods using testng execution listener which can help stop cases on failure or perform certain action during run time of suite
 */
public class TestngListenerClass implements IInvokedMethodListener {
    private boolean hasFailures = false;

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        synchronized (this) {
            if (hasFailures) {
                throw new SkipException("Skipping this test");
            }
        }
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        if (method.isTestMethod() && !testResult.isSuccess()) {
            synchronized (this) {
                hasFailures = true;
            }
        }
    }
}
