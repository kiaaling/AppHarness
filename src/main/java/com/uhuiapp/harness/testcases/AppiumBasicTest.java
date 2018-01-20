package com.uhuiapp.harness.testcases;

import com.uhuiapp.harness.utils.AppiumDriverFactory;
import com.uhuiapp.harness.utils.QAContext;
import io.appium.java_client.AppiumDriver;
import io.appium.java_client.TouchAction;
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
 * Created by ZhangZhaoxiong on 2018/1/15.
 * This is the Test Class parent.
 */
public class AppiumBasicTest {
    private static final Logger log = LogManager.getLogger(AppiumBasicTest.class);

    private AppiumDriver driver;
    private WebDriver selenium;
    private String appType;

    public AppiumBasicTest(){
        this.appType = QAContext.qAconfig.getAppType();
    }

    @BeforeSuite
    public void setUp() throws Exception {
        log.info("Start initial test Env");
        initialAppuimDriver();
        initialTestApplication();
    }

    @AfterSuite
    public void tearDown() {
        if("web".equalsIgnoreCase(appType)){
            selenium.close();
            selenium.quit();
        }else {
            driver.closeApp();
            driver.quit();
        }
        log.info("Close app and quit driver.");
    }

    protected boolean isMobileApp(){
        return "ios".equalsIgnoreCase(appType)||"android".equalsIgnoreCase(appType);
    }

    protected void touchActionScrollScreen(int startX, int startY, int endX, int endY){
        if(driver == null){
            log.error("PC Web端应用不能调用TouchAction.");
            return;
        }
        TouchAction scrollUp =new TouchAction(driver);
        scrollUp.press(startX,startY).moveTo(endX,endY).release().perform();
    }

    protected WebElement findElementById(String id){
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

    protected WebElement findElementByXPath(String xpath){
        if("web".equals(appType)){
            return selenium.findElement(By.xpath(xpath));
        }else{
            return  driver.findElementByXPath(xpath);
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
        if("android".equalsIgnoreCase(appType)){
            driver = (AndroidDriver) AppiumDriverFactory.getUniqueInstance().getAppiumDriver(appType);
        }else if("ios".equalsIgnoreCase(appType)){
            driver = (IOSDriver)AppiumDriverFactory.getUniqueInstance().getAppiumDriver(appType);
        }else if("windows".equalsIgnoreCase(appType)){
            driver = (WindowsDriver)AppiumDriverFactory.getUniqueInstance().getAppiumDriver(appType);
        }else if("web".equalsIgnoreCase(appType)){
            selenium = AppiumDriverFactory.getUniqueInstance().getAppiumDriver("selenium");
        }else {
            log.error("不支持的应用类型，请指定正确的应用类型。本工具仅支持： android|ios|windows|web");
            System.exit(1);
        }
    }

    private void initialTestApplication() {
        if(appType.equals("ios")){
            WebElement el1 = findElementById("buttonGotoPersonLogin");
            el1.click();
        }else if("android".equals(appType)){
            WebElement selectServer = findElementById("btIp3");
            selectServer.click();
        }else if("web".equals(appType)){
            selenium.get(QAContext.qAconfig.getBaseUrl());
        }
    }
}
