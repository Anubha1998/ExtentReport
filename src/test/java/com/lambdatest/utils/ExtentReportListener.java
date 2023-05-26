package com.lambdatest.utils;

import java.io.File;
import java.util.Map;

import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class ExtentReportListener {

    // initialize the HtmlReporter
    public static ExtentHtmlReporter reports;
    // create ExtentReports and attach reporter(s)
    public static ExtentReports extent;
    // creates a toggle for the given test, adds all log events under it
    public static ExtentTest test;

    public static String resultpath = getResultPath();

    public static void deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (null != files) {
                for (int i = 0; i < files.length; i++) {
                    System.out.println(files[i].getName());
                    if (files[i].isDirectory()) {
                        deleteDirectory(files[i]);
                    } else {
                        files[i].delete();
                    }
                }
            }
        }
    }

    private static String getResultPath() {
        resultpath = "test";// new SimpleDateFormat("yyyy-MM-dd hh-mm.ss").format(new Date());
        if (!new File(resultpath).isDirectory()) {
            new File(resultpath);
        }
        return resultpath;
    }

    public static void onTestStart() {
        reports = new ExtentHtmlReporter("./extent.html");
        extent = new ExtentReports();
        extent.attachReporter(reports);
    }

    public static void onTestPass(String sessionName, Map<String, String> reportMetadata) {
        test = extent.createTest(sessionName);
        test.log(Status.INFO, sessionName + "Started");
        test.pass(sessionName + "PASSED");
        if (reportMetadata == null)
            return;
        for (String key : reportMetadata.keySet()) {
            test.log(Status.INFO, key + " " + reportMetadata.get(key));
        }
    }

    public void onTestFailure(String sessionName) {
        test = extent.createTest(sessionName);
        test.log(Status.INFO, sessionName + "Started");
        test.fail(sessionName + "FAILED");
    }

    public static void onTestUnmarked(String sessionName) {
        test = extent.createTest(sessionName);
        test.log(Status.INFO, sessionName + "UNMARKED");
    }

    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {

    }

    public static void onFinish() {
        extent.flush();
    }

}
