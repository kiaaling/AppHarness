package com.uhuiapp.harness.cucumber;

import org.testng.IExecutionListener;

/**
 * Created by Zhang Zhaoxiong on 2017/3/3.
 */
public class TestNGExecutionListener implements IExecutionListener {
    @Override
    public void onExecutionStart() {
        System.out.println("TestNG is starting the cucumber execution");
    }

    @Override
    public void onExecutionFinish() {
        System.out.println("Generating the Masterthought Report");
        GenerateReport.GenerateMasterthoughtReport();
        System.out.println("TestNG has finished the cucumber execution");
    }
}
