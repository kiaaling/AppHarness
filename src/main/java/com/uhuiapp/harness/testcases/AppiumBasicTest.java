package com.uhuiapp.harness.testcases;

import com.uhuiapp.harness.utils.AppiumDriverFactory;
import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.net.MalformedURLException;

/**
 * Created by zhaoxiong on 2018/1/15.
 */
public class AppiumBasicTest {
    private static final Logger log = LogManager.getLogger(AppiumBasicTest.class);

    protected WebDriver driver;

    @BeforeSuite
    public void setUp() throws MalformedURLException {
        driver = (AndroidDriver)AppiumDriverFactory.getUniqueInstance().getAppiumDriver("android");
    }


    @AfterSuite
    public void tearDown() {
        driver.quit();
    }
}
