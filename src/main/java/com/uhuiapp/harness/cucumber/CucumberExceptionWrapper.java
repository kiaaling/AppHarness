package com.uhuiapp.harness.cucumber;

import cucumber.runtime.CucumberException;
import cucumber.runtime.model.CucumberFeature;

/**
 * Created by Zhang Zhaoxiong on 2017/3/24.
 */
public class CucumberExceptionWrapper implements CucumberFeatureWrapper {
    private CucumberException exception;

    public CucumberExceptionWrapper(CucumberException e) {
        this.exception = e;
    }

    @Override
    public CucumberFeature getCucumberFeature() {
        throw this.exception;
    }

}