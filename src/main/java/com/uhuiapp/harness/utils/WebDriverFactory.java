package com.uhuiapp.harness.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.URL;

/**
 * Created by ZhangZhaoxiong on 2016/2/29.
 */
public class WebDriverFactory {
    private WebDriver driver;
    private DesiredCapabilities capability;
    private static WebDriverFactory uniqueInstance;

    public static WebDriverFactory getUniqueInstance() {
        if (uniqueInstance == null) {
            uniqueInstance = new WebDriverFactory();
        }
        return uniqueInstance;
    }

    public WebDriver getWebDriver(String driverType, String browserType) {
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
        return driver;
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
