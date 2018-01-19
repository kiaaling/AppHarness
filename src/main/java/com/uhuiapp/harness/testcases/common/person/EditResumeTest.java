package com.uhuiapp.harness.testcases.common.person;

import com.uhuiapp.harness.testcases.AppiumBasicTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebElement;
import org.testng.annotations.Test;

/**
 * Created by zhaoxiong on 2018/1/15.
 */
public class EditResumeTest extends AppiumBasicTest {
    private static final Logger log = LogManager.getLogger(EditResumeTest.class);

    @Test
    public void editPersonalInformationTest() {
        loginToPersonSystem();
        editPersonInformation();
        checkDatabase();
    }

    private void editPersonInformation(){
        WebElement el6 = findElementById("buttonGotoResumeEdit");
        el6.click();
        WebElement el7 = findElementById("buttonGotoPersonalInformation");
        el7.click();
        WebElement el8 = findElementById("inputName");
        el8.sendKeys("张招雄");

        WebElement sex = findElementById("selectSex");
        sex.click();
        WebElement sexValue = findElementByXPath("//*[@text=\"女\"]");
        sexValue.click();

        WebElement bloodType = findElementById("selectBloodType");
        bloodType.click();
        WebElement bloodTypeValue = findElementByXPath("//*[@text=\"AB\"]");
        bloodTypeValue.click();

        WebElement chineseZodiac = findElementById("selectChineseZodiac");
        chineseZodiac.click();
        WebElement chineseZodiacValue = findElementByXPath("//*[@text=\"龙\"]");
        chineseZodiacValue.click();

        WebElement constellation = findElementById("selectConstellation");
        constellation.click();
        WebElement selectConstellationValue = findElementByXPath("//*[@text=\"天蝎座\"");
        selectConstellationValue.click();

        WebElement birthDay = findElementById("inputBirthdayYearMonth");
        birthDay.sendKeys("1992-08");

        WebElement startWorkDay = findElementById("inputStartWorkYearMonth");
        startWorkDay.sendKeys("2014-09");

        WebElement currentCity = findElementById("selectCurrentCity");
        currentCity.click();
        waitSeconds(1);
        if(isMobileApp()){
            touchActionScrollScreen(139, 6, 1982,-108);
            WebElement confirm = findElementById("tv_confirm");
            confirm.click();
        }

        WebElement phone = findElementById("inputPhoneNumber");
        phone.sendKeys("13812344321");

        WebElement email = findElementById("inputEmail");
        email.sendKeys("13812344321@qq.com");

        waitSeconds(1);
        if(isMobileApp()){
            touchActionScrollScreen(604,2312,-16,-433);
        }

        WebElement submit = findElementById("buttonSubmit");
        submit.click();
        findElementById("buttonGotoResumeEdit");
        log.info("成功保存个人信息！");
    }

    private void checkDatabase() {

    }

    private void loginToPersonSystem() {
        WebElement el2 = findElementById("buttonAccountLogin");
        el2.click();
        WebElement el3 = findElementById("inputAccount");
        el3.click();
        el3.sendKeys("18222618300");
        WebElement el4 = findElementById("inputPassword");
        el4.click();
        el4.sendKeys("654321");
        WebElement el5 = findElementById("buttonLogin");
        el5.click();
        waitSeconds(2);
        findElementById("buttonGotoResumeEdit");
        log.info("Login to system successfully!");
    }


}
