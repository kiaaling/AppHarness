package com.uhuiapp.harness.testcases;

import com.uhuiapp.harness.utils.AppiumDriverFactory;
import com.uhuiapp.harness.utils.QAContext;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.windows.WindowsDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

import java.net.MalformedURLException;

/**
 * Created by zhaoxiong on 2018/1/15.
 */
public class AppiumBasicTest {
    private static final Logger log = LogManager.getLogger(AppiumBasicTest.class);

    protected AppiumDriver driver;

    @BeforeSuite
    public void setUp() throws MalformedURLException {
        String appType = QAContext.qAconfig.getAppType();
        if("android".equals(appType.toLowerCase())){
            driver = (AndroidDriver)AppiumDriverFactory.getUniqueInstance().getAppiumDriver(appType);
        }else if("ios".equals(appType.toLowerCase())){
            driver = (IOSDriver)AppiumDriverFactory.getUniqueInstance().getAppiumDriver(appType);
        }else if("windows".equals(appType.toLowerCase())){
            driver = (WindowsDriver)AppiumDriverFactory.getUniqueInstance().getAppiumDriver(appType);
        }else if("web".equals(appType.toLowerCase())){
            driver = (WindowsDriver)AppiumDriverFactory.getUniqueInstance().getAppiumDriver("selenium");
        }else {
            log.error("不支持的应用类型，请指定正确的应用类型。本工具仅支持： android|ios|windows|web");
            System.exit(1);
        }
    }


    @AfterSuite
    public void tearDown() {
        driver.quit();
    }
}
