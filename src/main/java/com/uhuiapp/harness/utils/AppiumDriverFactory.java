package com.uhuiapp.harness.utils;

import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.ios.IOSDriver;
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

    public WebDriver getAppiumDriver(String driverType) throws Exception {
        if(driverType.toLowerCase().equals("android")){
            createAndroidDriver();
        }else if(driverType.toLowerCase().equals("ios")){
            createIOSDriver();
        }else if(driverType.toLowerCase().equals("windows")){
            createWindowsDriver();
        }else if(driverType.toLowerCase().equals("selenium")){
            createSeleniumDriver(QAContext.qAconfig.getSeleniumDriverType(),QAContext.qAconfig.getSeleniumDriverBrowser());
        }else{
            log.error("不支持的应用类型，请指定正确的应用类型。本工具仅支持： android|ios|windows|web");
            System.exit(1);
        }
        return driver;
    }

    private void createWindowsDriver() {
        //TODO
    }

    private void createIOSDriver() throws MalformedURLException{
        DesiredCapabilities desiredCapabilities = new DesiredCapabilities();
        desiredCapabilities.setCapability("platformName", "iOS");
        desiredCapabilities.setCapability("platformVersion", QAContext.qAconfig.getIOSOSVersion());
        desiredCapabilities.setCapability("deviceName", QAContext.qAconfig.getIOSDeviceName());
        desiredCapabilities.setCapability("app", QAContext.qAconfig.getIOSAppPath());
        desiredCapabilities.setCapability("automationName", "XCUITest");

        URL remoteUrl = new URL(QAContext.qAconfig.getAppiumServerUrl());

        driver = new IOSDriver(remoteUrl, desiredCapabilities);
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
            setInternetExplorerOptions();
            driver = new InternetExplorerDriver();
        } else if (browserType.equalsIgnoreCase("firefox")) {
            setFirefoxOptions();
            driver = new FirefoxDriver();
        } else if (browserType.equalsIgnoreCase("chrome")) {
            setChromeOptions();
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

    private void setInternetExplorerOptions() {
        System.setProperty("webdriver.ie.driver", "drivers/IEDriverServer.exe");
    }

    private void setChromeOptions() {
        if(System.getProperty("os.name").indexOf("Mac") != -1){
            System.setProperty("webdriver.chrome.driver", "drivers/mac/chromedriver");
        }else if(System.getProperty("os.name").indexOf("Windows") != -1){
            System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        }
    }

    private void setFirefoxOptions() {
        System.setProperty("webdriver.firefox.logfile", "logs/firefox_output.log");
        System.setProperty("webdriver.firefox.profile", "SeleniumTest");
        //webdriver.firefox.marionette default is true, it need geckodriver. But, the geckodriver is not yet feature complete currently. This means that it does not yet offer full conformance with the WebDriver.
        //So we set marionette to false.
        System.setProperty("webdriver.firefox.marionette", "false");
        if(System.getProperty("os.name").indexOf("Mac") != -1){
            System.setProperty("webdriver.gecko.driver", "drivers/mac/geckodriver");
            //System.setProperty("webdriver.firefox.bin", "/Applications/Firefox.app/Contents/MacOS/firefox-bin");
        }else if(System.getProperty("os.name").indexOf("Windows") != -1){
            System.setProperty("webdriver.gecko.driver", "drivers/geckodriver.exe");
            //System.setProperty("webdriver.firefox.bin", "%PROGRAMFILES%\\Mozilla Firefox\\firefox.exe");
        }
    }
}
