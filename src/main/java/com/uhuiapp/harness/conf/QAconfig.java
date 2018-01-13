package com.uhuiapp.harness.conf;

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

    public QAconfig() {
        try {
            configuration = new PropertiesConfiguration(getConfigFile());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public List<String> getTestSuites() {
        List suites = Lists.newArrayList();
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

    private void setSuitesByDirectory(List suites, String suitesRootDir) {
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

    private File getConfigFile() throws IOException {
        File confFile = null;
        try {
            confFile = new File(System.getProperty("harness.home"), "conf/harness.conf");
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

    public String getSeleniumDriverType() {
        return configuration.getString("harness.webdriver.type", "local");
    }

    public String getSeleniumDriverBrowser() {
        return configuration.getString("harness.webdriver.browser", "ie");
    }

    public String getBaseUrl() {
        return configuration.getString("harness.webserver.baseurl", "http://localhost");
    }
}