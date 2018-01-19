package com.uhuiapp.harness.testcases;

import com.uhuiapp.harness.utils.AppiumDriverFactory;
import com.uhuiapp.harness.utils.QAContext;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.windows.WindowsDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

/**
 * Created by zhaoxiong on 2018/1/15.
 */
public class AppiumBasicTest {
    private static final Logger log = LogManager.getLogger(AppiumBasicTest.class);

    private AppiumDriver driver;
    private WebDriver selenium;

    @BeforeSuite
    public void setUp() throws Exception {
        log.info("Start initial test Env");
        initialAppuimDriver();
        initialTestApplication();
    }

    @AfterSuite
    public void tearDown() {
        if("web".equalsIgnoreCase(QAContext.qAconfig.getAppType())){
            selenium.close();
            selenium.quit();
        }else {
            driver.closeApp();
            driver.quit();
        }
        log.info("Close app and quit driver.");
    }

    protected WebElement findElementById(String id){
        String appType = QAContext.qAconfig.getAppType();
        WebElement element;
        if("android".equals(appType)){
            String elementId = QAContext.qAconfig.getAndroidResourceIdPrefix()+id;
            element = driver.findElementById(elementId);
            return element;
        }else if("ios".equals(appType)){
            element = driver.findElementByAccessibilityId(id);
            return element;
        }else if("web".equals(appType)){
            element = selenium.findElement(By.id(id));
            return element;
        }else {
            element = driver.findElementById(id);
            return element;
        }
    }

    protected void waitSeconds(int seconds) {
        try {
            Thread.sleep(1000*seconds);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void initialAppuimDriver() throws Exception {
        String appType = QAContext.qAconfig.getAppType();
        if("android".equals(appType.toLowerCase())){
            driver = (AndroidDriver) AppiumDriverFactory.getUniqueInstance().getAppiumDriver(appType);
        }else if("ios".equals(appType.toLowerCase())){
            driver = (IOSDriver)AppiumDriverFactory.getUniqueInstance().getAppiumDriver(appType);
        }else if("windows".equals(appType.toLowerCase())){
            driver = (WindowsDriver)AppiumDriverFactory.getUniqueInstance().getAppiumDriver(appType);
        }else if("web".equals(appType.toLowerCase())){
            selenium = AppiumDriverFactory.getUniqueInstance().getAppiumDriver("selenium");
        }else {
            log.error("不支持的应用类型，请指定正确的应用类型。本工具仅支持： android|ios|windows|web");
            System.exit(1);
        }
    }

    private void initialTestApplication() {
        if(QAContext.qAconfig.getAppType().equals("ios")){
            WebElement el1 = findElementById("buttonGotoPersonLogin");
            el1.click();
        }else if("android".equals(QAContext.qAconfig.getAppType())){
            WebElement selectServer = findElementById("btIp3");
            selectServer.click();
        }else if("web".equals(QAContext.qAconfig.getAppType())){
            selenium.get(QAContext.qAconfig.getBaseUrl());
        }
    }
}
