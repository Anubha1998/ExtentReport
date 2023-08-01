package com.lambdaTestApp.android;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.lambdatest.utils.ExtentReportListener;
import com.lambdatest.utils.LambdaTestApi;
import io.appium.java_client.MobileBy;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.AndroidElement;

import org.json.JSONException;
import org.json.JSONObject;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.openqa.selenium.remote.DesiredCapabilities;



public class Android1 extends AppUploadAndroid{

        String userName = "anubhas";
        String accessKey = "JvGShZ2Bm8RdgmGFbbx4ZtbOb6DeQ8nqSvtHDZdDY7PzqaZMTq";
        private String Status = "failed";
        @BeforeClass
        public void setup(){
               ExtentReportListener reporter = new ExtentReportListener();
                ExtentReportListener.onTestStart();
        }

        @Test
        @org.testng.annotations.Parameters(value = {"device", "platform"})



        public void basicTest(String device, String platform) throws IOException, InterruptedException, JSONException, URISyntaxException {
                upload();
                DesiredCapabilities caps = new DesiredCapabilities();
                caps.setCapability("deviceName", device);
                caps.setCapability("isRealMobile", true);
              //  caps.setCapability("platformVersion",version);
                caps.setCapability("platformName", platform);
                caps.setCapability("build", "Extent Report Parallel-6");
                caps.setCapability("name", "Extent Report Parallel-6");
                caps.setCapability("app", "android_appurl");
                caps.setCapability("appProfiling", true);
                LambdaTestApi ltApi = new LambdaTestApi();
               /* ExtentReportListener reporter = new ExtentReportListener(); */

                AndroidDriver<AndroidElement> driver = new AndroidDriver<AndroidElement>(
                                new URL("https://" + userName + ":" + accessKey + "@beta-hub.lambdatest.com/wd/hub"),
                                caps);
                       /*  ExtentReportListener.onTestStart(); */

                SessionId sessionid = ((AndroidDriver) driver).getSessionId();
                System.out.println(sessionid);
                WebDriverWait wait = new WebDriverWait(driver, 10);
                AndroidElement searchElement = (AndroidElement) wait
                                .until(ExpectedConditions
                                                .elementToBeClickable(MobileBy.AccessibilityId("Search Wikipedia")));
                searchElement.click();
                AndroidElement insertTextElement = (AndroidElement) wait
                                .until(ExpectedConditions.elementToBeClickable(
                                                MobileBy.id("org.wikipedia.alpha:id/search_src_text")));
                insertTextElement.sendKeys("LambdaTest");
                Thread.sleep(5000);

                List<AndroidElement> allProductsName = driver.findElementsByClassName("android.widget.TextView");
                assert (allProductsName.size() > 0);

                String session_id = sessionid.toString();
                System.out.println("Response is "+ ltApi.getSessionDetails(session_id));
                String sessionName = ltApi.getValue(ltApi.getSessionDetails(session_id), "name");
                System.out.println(ltApi.getSessionDetails(session_id));
                ltApi.markTestStatus(session_id, "PASSED", "Pass");

                JSONObject videoResponse = ltApi.getVideo(session_id);
                Map<String, String> metadata = new LinkedHashMap<String, String>();
                metadata.put("View Test", "<a href='https://automation.lambdatest.com/test?sessionId="+session_id+"' target='_blank'>Logs</a>");
                metadata.put("Download Test", "<a href='"+videoResponse.getString("url")+"'>Video</a>");
                metadata.put("Watch Test ", "<video width='400' controls><source src='"+videoResponse.getString("url")+"' type='video/mp4'></video>");
               ExtentReportListener.onTestPass(sessionName, metadata);

                // The driver.quit statement is required, otherwise the test continues to
            // execute, leading to a timeout.
                driver.quit();


                        ExtentReportListener.onFinish();

        }


}
