package com.uhuiapp.harness.cucumber;

/**
 * Created by Zhang Zhaoxiong on 2017/3/24.
 */

import cucumber.runtime.model.CucumberFeature;

/**
 * The only purpose of this class is to provide custom {@linkplain #toString()},
 * making TestNG reports look more descriptive.
 *
 * @see AbstractTestNGCucumberTests#feature(CucumberFeatureWrapper)
 */
public class CucumberFeatureWrapperImpl implements CucumberFeatureWrapper {
    private final CucumberFeature cucumberFeature;

    public CucumberFeatureWrapperImpl(CucumberFeature cucumberFeature) {
        this.cucumberFeature = cucumberFeature;
    }

    @Override
    public CucumberFeature getCucumberFeature() {
        return cucumberFeature;
    }

    @Override
    public String toString() {
        return cucumberFeature.getGherkinFeature().getName();
    }
}