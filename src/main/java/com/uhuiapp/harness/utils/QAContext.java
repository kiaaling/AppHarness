package com.uhuiapp.harness.utils;

import com.uhuiapp.harness.conf.QAconfig;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZhangZhaoxiong on 2016/1/25.
 */
public class QAContext {
    public static QAconfig qAconfig = new QAconfig();
    public static Map<String, Object> storeObjectMap = new HashMap<String, Object>();

}
