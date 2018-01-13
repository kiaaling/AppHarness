package com.uhuiapp.harness;

import com.uhuiapp.harness.testng.ExtentTestNGIReporterListener;
import com.uhuiapp.harness.utils.QAContext;
import org.testng.TestNG;

public class qatool {
    public static void main(String[] args) {
        System.setProperty("webdriver.ie.driver", "drivers/IEDriverServer.exe");
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        TestNG testNG = new TestNG();
        testNG.setVerbose(-1);
        testNG.setTestSuites(QAContext.qAconfig.getTestSuites());
        testNG.setOutputDirectory(QAContext.qAconfig.getTestNGOutputDirectory());
        //testNG.addListener(new ExtentTestNGIReporterListener());
        testNG.run();
        System.exit(testNG.getStatus());
    }
}
