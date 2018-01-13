package com.uhuiapp.harness.cucumber;

import com.uhuiapp.harness.utils.QAContext;
import net.masterthought.cucumber.Configuration;
import net.masterthought.cucumber.ReportBuilder;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhang Zhaoxiong on 2017/3/3.
 */
public class GenerateReport {
    public static void GenerateMasterthoughtReport() {
        try {
            File reportOutputDirectory = new File(QAContext.qAconfig.getCucumberOutputDirectory());

            List<String> list = new ArrayList<String>();
            File[] matchingFiles = reportOutputDirectory.listFiles(new FilenameFilter() {
                public boolean accept(File dir, String name) {
                    return name.endsWith(".json");
                }
            });

            for (File file : matchingFiles) {
                if (file.isFile()) {
                    list.add(file.getAbsolutePath());
                }
            }

            String buildNumber = "1";
            String buildProject = "cucumber-testng-demo";
            boolean runWithJenkins = false;
            boolean parallelTesting = true;

            Configuration configuration = new Configuration(reportOutputDirectory, buildProject);
            configuration.setParallelTesting(parallelTesting);
            configuration.setRunWithJenkins(runWithJenkins);
            configuration.setBuildNumber(buildNumber);

            ReportBuilder reportBuilder = new ReportBuilder(list, configuration);

            reportBuilder.generateReports();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
