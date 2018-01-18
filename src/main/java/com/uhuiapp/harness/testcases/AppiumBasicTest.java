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
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

/**
 * Created by zhaoxiong on 2018/1/15.
 */
public class AppiumBasicTest {
    private static final Logger log = LogManager.getLogger(AppiumBasicTest.class);

    protected AppiumDriver driver;

    @BeforeSuite
    public void setUp() throws Exception {
        log.info("Start initial test Env");
        initialAppuimDriver();
        initialTestApplication();
    }

    @AfterSuite
    public void tearDown() {
        if("web".equalsIgnoreCase(QAContext.qAconfig.getAppType())){
            driver.close();
        }else {
            driver.closeApp();
        }
        driver.quit();
        log.info("Close app and quit driver.");
    }

    public MobileElement findElementById(String id){
        String appType = QAContext.qAconfig.getAppType();
        MobileElement element;
        if("android".equals(appType)){
            String elementId = QAContext.qAconfig.getAndroidResourceIdPrefix()+id;
            element = (MobileElement)driver.findElementById(elementId);
            return element;
        }else if("ios".equals(appType)){
            element = (MobileElement)driver.findElementByAccessibilityId(id);
            return element;
        }else {
            element = (MobileElement)driver.findElementById(id);
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
            driver = (AppiumDriver) AppiumDriverFactory.getUniqueInstance().getAppiumDriver("selenium");
        }else {
            log.error("不支持的应用类型，请指定正确的应用类型。本工具仅支持： android|ios|windows|web");
            System.exit(1);
        }
    }

    private void initialTestApplication() {
        if(QAContext.qAconfig.getAppType().equals("ios")){
            MobileElement el1 = findElementById("buttonGotoPersonLogin");
            el1.click();
        }else if("android".equals(QAContext.qAconfig.getAppType())){
            MobileElement selectServer = findElementById("btIp3");
            selectServer.click();
        }else if("web".equals(QAContext.qAconfig.getAppType())){
            driver.get(QAContext.qAconfig.getBaseUrl());
        }
    }
}
