package api.utilities;

import java.text.SimpleDateFormat;

import org.apache.poi.hpsf.Date;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

public class ExtentReportManager implements ITestListener

{
	public ExtentSparkReporter sparkReporter;
	public ExtentReports extent;
	public ExtentTest test;
	
	String repName;
	
	public void onStart(ITestContext testContext)
	{
	
//		String timeStamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()); // time stamp
//		repName = "Test-Report-"+timeStamp+".html";
//		
		sparkReporter = new ExtentSparkReporter("./report/result.html"+repName); // specify location of the report
		
	//	sparkReporter = new ExtentSparkReporter(filePath: System.getProperty("user.dir") + "/reports/" + reportname + ".html");
		
	//	sparkReporter = new ExtentSparkReporter("ExtentListenerReportFolder.html");
		
		//these are all related to look and feel so we will use sparkReporter
		sparkReporter.config().setDocumentTitle("RestAssuredAutomationProject"); // Title of report
		sparkReporter.config().setReportName("Pet Store Users API");   // name of the report
		sparkReporter.config().setTheme(Theme.DARK);
		
		// To specify the common information we will specfy the extent report
		extent = new ExtentReports();
		extent.attachReporter(sparkReporter);
		extent.setSystemInfo("Application", "Pet Store Users API");
		extent.setSystemInfo("Operating System", System.getProperty("os.name"));
		extent.setSystemInfo("User Name", System.getProperty("user.name"));
		extent.setSystemInfo("Environemnt", "QA"); 
		extent.setSystemInfo("user", "pavan");
				
	}
	
	public void onTestSuccess(ITestResult result)
	{
		test = extent.createTest(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.createNode(result.getName());
		test.log(Status.PASS, "Test Passed");
		
	}
	
	public void onTestFailure(ITestResult result)
	{
		test = extent.createTest(result.getName());
		test.createNode(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.FAIL, "Test Failed");
		test.log(Status.FAIL, result.getThrowable().getMessage());
		
	}
	
	public void onTestSkipped(ITestResult result)
	{
		test = extent.createTest(result.getName());
		test.createNode(result.getName());
		test.assignCategory(result.getMethod().getGroups());
		test.log(Status.SKIP, "Test Skipped");
		test.log(Status.SKIP, result.getThrowable().getMessage());		
	}
	
	public void onFinish(ITestContext testContext)
	{
		extent.flush();
	}
	

}
