package com.uhuiapp.harness.testcases.common;

import com.uhuiapp.harness.testcases.AppiumBasicTest;
import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.annotations.Test;

/**
 * Created by zhaoxiong on 2018/1/15.
 */
public class EditPersonalInformation  extends AppiumBasicTest {
    private static final Logger log = LogManager.getLogger(EditPersonalInformation.class);

    @Test
    public void sampleTest() {
        MobileElement el1 = (MobileElement) driver.findElementById("cn.uhui.cqt.talent:id/btIp3");
        el1.click();
        MobileElement el2 = (MobileElement) driver.findElementById("cn.uhui.cqt.talent:id/btAccountLogin");
        el2.click();
        MobileElement el3 = (MobileElement) driver.findElementById("cn.uhui.cqt.talent:id/etTell");
        el3.sendKeys("18222618300");
        MobileElement el4 = (MobileElement) driver.findElementById("cn.uhui.cqt.talent:id/etPwd");
        el4.sendKeys("654321");
        MobileElement el5 = (MobileElement) driver.findElementById("cn.uhui.cqt.talent:id/btLogin");
        el5.click();
        MobileElement el6 = (MobileElement) driver.findElementById("cn.uhui.cqt.talent:id/tvEditVita");
        el6.click();
        MobileElement el7 = (MobileElement) driver.findElementById("cn.uhui.cqt.talent:id/llBaseInfo");
        el7.click();
        MobileElement el8 = (MobileElement) driver.findElementById("cn.uhui.cqt.talent:id/etName");
        el8.sendKeys("张招雄");
        MobileElement el9 = (MobileElement) driver.findElementById("cn.uhui.cqt.talent:id/llSex");
        el9.click();
        MobileElement el10 = (MobileElement) driver.findElementByXPath("/hierarchy/android.widget.FrameLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.ScrollView/android.widget.LinearLayout/android.widget.ListView/android.widget.LinearLayout[2]/android.widget.LinearLayout");
        el10.click();
        try {
            Thread.sleep(1000);
        }catch (Exception e){
            e.printStackTrace();
        }

        TouchAction scrollUp = new TouchAction(driver);
        scrollUp.press(604,2312).moveTo(-16,-433).release().perform();

        MobileElement el11 = (MobileElement) driver.findElementById("cn.uhui.cqt.talent:id/btSubmit");
        el11.click();
        log.info("成功保存个人信息！");
    }


}
