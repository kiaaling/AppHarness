package com.uhuiapp.harness.conf;

import com.uhuiapp.harness.utils.TextCrypto;
import org.apache.commons.configuration.Configuration;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.apache.commons.io.FileUtils;
import org.testng.collections.Lists;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

public class QAconfig {
    private Configuration configuration;
    private Configuration jdbcConfiguration;
    private String appType = "";

    public QAconfig() {
        try {
            configuration = new PropertiesConfiguration(getConfigFile("harness"));
            jdbcConfiguration = new PropertiesConfiguration(getConfigFile("jdbc"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<String> getTestSuites() {
        List<String> suites = Lists.newArrayList();
        String suitesRootDir = getSuitesRootDir();
        setSuitesByDirectory(suites, suitesRootDir);
        return suites;
    }

    private String getSuitesRootDir() {
        String suitesRootDir = configuration.getString("harness.testsuites.rootDir", "testsuites");
        checkSuiteRootDir(suitesRootDir);
        if (!suitesRootDir.startsWith("/") && System.getProperty("harness.home") != null) {
            suitesRootDir = System.getProperty("harness.home") + "/" + suitesRootDir;
        }
        return suitesRootDir;
    }

    private void checkSuiteRootDir(String suitesRootDir) {
        if (suitesRootDir == null || suitesRootDir.isEmpty()) {
            System.out.println("harness.testsuites.rootDir config item is empty, please set it in harness.conf!");
            System.exit(1);
        }
    }

    private void setSuitesByDirectory(List<String> suites, String suitesRootDir) {
        File folder = new File(suitesRootDir);
        if (!folder.exists()) {
            System.out.println("The test suite directory doesn't exist!");
            System.exit(1);
        }
        Collection<File> listOfSuiteFiles = FileUtils.listFiles(folder, new String[]{"xml"}, true);
        for (File file : listOfSuiteFiles) {
            if (file.isFile()) {
                String filename = file.getAbsolutePath();
                suites.add(filename);
            }
        }
    }

    private File getConfigFile(String type) throws IOException {
        File confFile = null;
        try {
            if(type.equalsIgnoreCase("jdbc")){
                confFile = new File(System.getProperty("harness.home"), "conf/harness.conf");
            }else {
                confFile = new File(System.getProperty("harness.home"), "conf/jdbc.properties");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return confFile;
    }

    public String getTestNGOutputDirectory() {
        String reportDir = configuration.getString("harness.testng.output");
        if (!reportDir.startsWith("/") && System.getProperty("harness.home") != null) {
            reportDir = System.getProperty("harness.home") + "/" + reportDir;
        }
        return reportDir;
    }

    public String getCucumberOutputDirectory() {
        String reportDir = configuration.getString("harness.cucumber.output");
        if (!reportDir.startsWith("/") && System.getProperty("harness.home") != null) {
            reportDir = System.getProperty("harness.home") + "/" + reportDir;
        }
        return reportDir;
    }

    public String getCucumberFeatrues() {
        String features = configuration.getString("harness.cucumber.features");
        if (!features.startsWith("/") && System.getProperty("harness.home") != null) {
            features = System.getProperty("harness.home") + "/" + features;
        }
        return features;
    }

    public String getSeleniumHub() {
        return configuration.getString("harness.selenium.server", "http://localhost:4444/wd/hub");
    }

    public String getAppiumServerUrl() {
        return configuration.getString("harness.appium.server", "http://localhost:4723/wd/hub");
    }

    public String getAndroidAppPath(){
        return configuration.getString("harness.android.app.path", "/tmp/testapp.apk");
    }

    public String getAndroidDeviceName(){
        return configuration.getString("harness.android.device.name", "Android Emulator");
    }

    public String getAndroidOSVersion(){
        return configuration.getString("harness.android.os.version", "7.1");
    }

    public String getAndroidResourceIdPrefix(){
        return configuration.getString("harness.android.resource.id.prefix", "");
    }

    public String getIOSAppPath(){
        return configuration.getString("harness.ios.app.path", "/tmp/RecruitPlatformAPP.app");
    }

    public String getIOSDeviceName(){
        return configuration.getString("harness.ios.device.name", "iPhone 8 Plus");
    }

    public String getIOSOSVersion(){
        return configuration.getString("harness.ios.os.version", "11.2");
    }
    public void setAppType(String appType){
        this.appType = appType;
    }

    public String getAppType(){
        return  this.appType;
    }

    public String getSeleniumDriverType() {
        return configuration.getString("harness.webdriver.type", "local");
    }

    public String getSeleniumDriverBrowser() {
        return configuration.getString("harness.webdriver.browser", "ie");
    }

    public String getBaseUrl() {
        return configuration.getString("harness.webserver.baseurl", "http://localhost");
    }


    public String getDatabaseDriver() {
        return jdbcConfiguration.getString("jdbc.driver", "com.mysql.jdbc.Driver");
    }


    public String getDatabaseUri() {
        return jdbcConfiguration.getString("jdbc.url");
    }

    public String getDatabaseUser() {
        return jdbcConfiguration.getString("jdbc.username");
    }


    public String getDatabasePassword() {
        String encryptPass = jdbcConfiguration.getString("jdbc.password");
        String decryptPass = decryptUserPassword(encryptPass);
        return decryptPass;
    }

    private static String decryptUserPassword(String encryptPassword) {
        TextCrypto encryptor = new TextCrypto();
        String decryptText = "";
        try {
            decryptText = encryptor.decryptPassword(encryptPassword);
        }catch (Exception e){
            e.printStackTrace();
        }
        return decryptText;
    }
}
