### 编译打包 ###
#### 本地编译
	./gradlew clean build
#### 上传到 nexus
    ./gradlew clean upload

### 运行时配置说明
```
#TestNG测试用例目录
harness.testsuites.rootDir = testsuites

#TestNG测试报告目录
harness.testng.output = testng-reports

#cucumber测试报告目录
harness.cucumber.output = cucumber-reports

#cucumber测试用例目录
harness.cucumber.features = testdata/features/demo

#指定cucumber tags
harness.cucumber.tags =

#指定selenium的Driver类型，可以是 local或remoter. 缺省是local，即使用本地Driver.
harness.webdriver.type = local

#指定浏览器类型，可以是：ie|chrome|firefox|safari|htmlunit , 缺省为firefox
harness.webdriver.browser = chrome

#指定Selenium 服务器，如果harness.webdriver.type = local，该项必须指定
harness.selenium.server = http://localhost:4444/wd/hub

#需要测试的Web服务器地址。
harness.webserver.baseurl = http://www.talent.yfq.com/

#Appium config
#配置App测试的 appium服务器地址
harness.appium.server = http://localhost:4723/wd/hub

#查找元素或进行操作时的超时时间，单位是秒
harness.appium.implicit.timeout = 20

#测试Android App时的配置参数
harness.android.app.path = /Users/zhaoxiong/temp/Talent-V-1-0-0.apk
harness.android.os.version = 7.1
harness.android.device.name = Android Emulator
harness.android.resource.id.prefix = cn.uhui.cqt.talent:id/

#测试 IOS App时的配置参数
harness.ios.app.path = /Users/zhaoxiong/temp/RecruitPlatformAPP.app
harness.ios.device.name = iPhone 8 Plus
harness.ios.os.version = 11.2
```   