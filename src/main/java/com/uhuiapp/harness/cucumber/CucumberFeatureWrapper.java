package com.uhuiapp.harness.cucumber;

/**
 * Created by Zhang Zhaoxiong on 2017/3/24.
 */

import cucumber.runtime.model.CucumberFeature;

/**
 * The only purpose of this interface is to be able to provide a custom
 * <pre>toString()</pre>, making TestNG reports look more descriptive.
 *
 * @see CucumberFeatureWrapperImpl
 */
public interface CucumberFeatureWrapper {
    public CucumberFeature getCucumberFeature();
}