package com.uhuiapp.harness.utils;

import io.appium.java_client.android.AndroidDriver;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.concurrent.TimeUnit;

/**
 * Created by ZhangZhaoxiong on 2016/2/29.
 */
public class AppiumDriverFactory {
    private static final Logger log = LogManager.getLogger(AppiumDriverFactory.class);

    private WebDriver driver;
    private DesiredCapabilities capability;
    private static AppiumDriverFactory uniqueInstance;

    public static AppiumDriverFactory getUniqueInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new AppiumDriverFactory();
        }
        return uniqueInstance;
    }

    public WebDriver getAppiumDriver(String driverType) throws MalformedURLException {
        if(driverType.toLowerCase().equals("android")){
            createAndroidDriver();
        }else if(driverType.toLowerCase().equals("ios")){
            createIOSDriver();
        }else if(driverType.toLowerCase().equals("windows")){
            createWindowsDriver();
        }else if(driverType.toLowerCase().equals("selenium")){
            createSeleniumDriver("local","firefox");
        }
        return driver;
    }

    private void createWindowsDriver() {

    }

    private void createIOSDriver() throws MalformedURLException{
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "iOS");
        desiredCapabilities.setCapability("platformVersion", QAContext.qAconfig.getIOSOSVersion());
        desiredCapabilities.setCapability("deviceName", QAContext.qAconfig.getIOSDeviceName());
        desiredCapabilities.setCapability("app", QAContext.qAconfig.getIOSAppPath());
        desiredCapabilities.setCapability("automationName", "XCUITest");

        URL remoteUrl = new URL(QAContext.qAconfig.getAppiumServerUrl());

        driver = new AndroidDriver(remoteUrl, desiredCapabilities);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        log.info("Initial driver successfully!");
    }

    private void createAndroidDriver()  throws MalformedURLException {
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "Android");
        desiredCapabilities.setCapability("platformVersion", QAContext.qAconfig.getAndroidOSVersion());
        desiredCapabilities.setCapability("deviceName", QAContext.qAconfig.getAndroidDeviceName());
        desiredCapabilities.setCapability("app", QAContext.qAconfig.getAndroidAppPath());
        desiredCapabilities.setCapability("automationName", "UiAutomator2");

        URL remoteUrl = new URL(QAContext.qAconfig.getAppiumServerUrl());

        driver = new AndroidDriver(remoteUrl, desiredCapabilities);
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        log.info("Initial driver successfully!");
    }

    private void createSeleniumDriver(String driverType, String browserType) {
        if (driverType.toLowerCase().equals("local")) {
            createLocalWebDriver(browserType);
        } else if (driverType.toLowerCase().equals("remote")) {
            try {
                createRemoteWebDriver(browserType);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("The driverType [" + driverType + "] doesn't be supproted. It can only be local or remote");
            System.exit(1);
        }
    }

    private void createRemoteWebDriver(String browserType) throws Exception {
        URL remoteSeleniumServer = new URL(QAContext.qAconfig.getSeleniumHub());
        if (browserType.equalsIgnoreCase("ie")) {
            capability = DesiredCapabilities.internetExplorer();
        } else if (browserType.equalsIgnoreCase("firefox")) {
            capability = DesiredCapabilities.firefox();
        } else if (browserType.equalsIgnoreCase("chrome")) {
            capability = DesiredCapabilities.chrome();
        } else if (browserType.equalsIgnoreCase("safari")) {
            capability = DesiredCapabilities.safari();
        } else if (browserType.equalsIgnoreCase("htmlunit")) {
            capability = DesiredCapabilities.htmlUnit();
        } else {
            System.out.println("The browserType [" + browserType + "] doesn't be supproted. It can only be ie|firefox|chrome|safari|htmlunit");
            System.exit(1);
        }
        driver = new RemoteWebDriver(remoteSeleniumServer, capability);
    }

    private void createLocalWebDriver(String browserType) {
        if (browserType.equalsIgnoreCase("ie")) {
            driver = new InternetExplorerDriver();
        } else if (browserType.equalsIgnoreCase("firefox")) {
            driver = new FirefoxDriver();
        } else if (browserType.equalsIgnoreCase("chrome")) {
            driver = new ChromeDriver();
        } else if (browserType.equalsIgnoreCase("safari")) {
            driver = new SafariDriver();
        } else if (browserType.equalsIgnoreCase("htmlunit")) {
            driver = new HtmlUnitDriver(true);
        } else {
            System.out.println("The browserType [" + browserType + "] doesn't be supproted. It can only be ie|firefox|chrome|safari|htmlunit");
            System.exit(1);
        }
    }
}
