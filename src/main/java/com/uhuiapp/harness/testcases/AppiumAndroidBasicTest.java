package com.uhuiapp.harness.testcases;

import io.appium.java_client.android.AndroidDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhaoxiong on 2018/1/15.
 */
public class AppiumAndroidBasicTest {
    protected AndroidDriver driver;

    @BeforeSuite
    public void setUp() throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("platformVersion", "7.1");
        desiredCapabilities.setCapability("deviceName", "Android Emulator");
        desiredCapabilities.setCapability("app", "/Users/zhaoxiong/temp/Talent-V-1-0-0-T-2018-01-11-15-17-34-ceshi.apk");
        desiredCapabilities.setCapability("automationName", "UiAutomator2");

        URL remoteUrl = new URL("http://localhost:4723/wd/hub");

        driver = new AndroidDriver(remoteUrl, desiredCapabilities);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
    }


    @AfterSuite
    public void tearDown() {
        driver.quit();
    }
}
