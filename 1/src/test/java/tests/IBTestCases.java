package tests;

import com.aventstack.extentreports.Status;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.*;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.*;

import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import java.util.*; 

public class IBTestCases extends BaseTest {

    public static WebDriver driver = null;
    
    
    @BeforeSuite
    public void setUp(){
    	System.setProperty("webdriver.chrome.driver", "/Users/jp/Downloads/chromedriver");
		driver = new ChromeDriver();
    }

    @BeforeSuite
    public void startReport(){
        initializeReport();
    }
    
    @Test(priority=1)
    public void verifyLogin(){
        String methodName = new Exception().getStackTrace()[0].getMethodName();
        test = extent.createTest(methodName,"Verify Page Title");
        test.log(Status.INFO,"starting");

        driver.get("https://ndcdyn.interactivebrokers.com/sso/Login?c=t");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(2));
        
        if(driver.getTitle().equals(driver.getTitle())) {
        	test.log(Status.PASS,"Title Matches");
        }else {
        	test.log(Status.FAIL,"Did not arrive at correct page");
        }
        Assert.assertEquals(driver.getTitle(),driver.getTitle());
        
        WebElement userNameInput = driver.findElement(By.id("user_name"));
        WebElement passwordInput = driver.findElement(By.id("password"));
        
        userNameInput.sendKeys("jonpalmieri2022");
        passwordInput.sendKeys("Jonboy20");
        
        WebElement submitButton = driver.findElement(By.id("submitForm"));
        submitButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        
        if(driver.findElement(By.xpath("/html/body/nav/div/div[2]/ul/li[3]/a/span")).getText().equals("U10633074")) {
        	test.log(Status.PASS,"Successfully Logged In");
        }else {
        	test.log(Status.FAIL,"Did not Login Correctly");
        }
        Assert.assertEquals("U10633074", driver.findElement(By.xpath("/html/body/nav/div/div[2]/ul/li[3]/a/span")).getText());
    }
    
    @Test(priority=2)
    public void verifyAboutYouPage(){
        String methodName = new Exception().getStackTrace()[0].getMethodName();
        test = extent.createTest(methodName,"Verify About You Page");
        test.log(Status.INFO,"starting");
        
        //1. Enter DOB
        	//Asset by then looking at DOB in HTML and comparing against what we set it to
        WebElement dob = (new WebDriverWait(driver, Duration.ofSeconds(50))).until(ExpectedConditions.elementToBeClickable(By.id("date")));
        dob.sendKeys("08/30/1998");
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        
        if(driver.findElement(By.xpath("/html/body/div[2]/div/div/form/section[3]/div/div/div/div[1]/div/div/input")).getAttribute("value").equals("08/30/1998")) {
        	test.log(Status.PASS,"Successfully Added DOB");
        }else {
        	test.log(Status.FAIL,"DOB not Set Correctly");
        }
        Assert.assertEquals("08/30/1998", driver.findElement(By.xpath("/html/body/div[2]/div/div/form/section[3]/div/div/div/div[1]/div/div/input")).getAttribute("value"));
     
                
        //2. Print all the categories under Source of Wealth 
        	//Asset by comparing what we know this should be returned as
        int SoWCatCount = 0;
        test.log(Status.INFO,"Sources of Wealth Categories: ");
        List<WebElement> SoWCategories = driver.findElements(By.className("col-xs-10"));
        for(WebElement category: SoWCategories) {
        	if(category.getText().equals("Allowance / Spousal Income") || category.getText().equals("Disability / Severance / Unemployment") || category.getText().equals("Income from Employment") || category.getText().equals("Inheritance/Gift") || category.getText().equals("Interest / Dividend Income") || category.getText().equals("Market Trading Profits") || category.getText().equals("Pension / Government Retirement benefit") || category.getText().equals("Property") || category.getText().equals("Other")){
        		SoWCatCount++;
        	}
        	test.log(Status.INFO,category.getText());
        }
        if(SoWCatCount == 9) {
        	test.log(Status.PASS,"Successfully Printed All Source of Wealth Categories");
        }else {
        	test.log(Status.FAIL,"Did Not Print All Source of Wealth Categories");
        }
        Assert.assertEquals(9, SoWCatCount);

        //3. Complete the remaining mandatory fields to continue to the next page -- can be manual
        	//Manual, no Assert Needed
        WebElement firstName = driver.findElement(By.id("firstName"));
        if(firstName.getAttribute("value") == "Jonathan") {
            WebElement numOfDependents = driver.findElement(By.id("numOfDependents"));
            numOfDependents.sendKeys("0");
        }else {
        	WebElement salutation = driver.findElement(By.id("salutation"));
            salutation.sendKeys("Mr.");
            
            firstName.sendKeys("Jonathan");
            
            WebElement lastName = driver.findElement(By.id("lastName"));
            lastName.sendKeys("Palmieri");
            
            WebElement street1Main = driver.findElement(By.id("street1Main"));
            street1Main.sendKeys("11 Danand Lane");
            
            WebElement cityMain = driver.findElement(By.id("cityMain"));
            cityMain.sendKeys("Patterson");
            
            WebElement postalCodeMain = driver.findElement(By.id("postalCodeMain"));
            postalCodeMain.sendKeys("12563");
            
            WebElement number1 = driver.findElement(By.id("number1"));
            number1.sendKeys("8456679829");
            
            WebElement maritalStatus = driver.findElement(By.id("maritalStatus"));
            maritalStatus.sendKeys("Single");
            
            WebElement numOfDependents = driver.findElement(By.id("numOfDependents"));
            numOfDependents.sendKeys("0");
            
            WebElement taxResidency1Number = driver.findElement(By.id("taxResidency1Number"));
            taxResidency1Number.sendKeys("073881234");
            
            WebElement employmentType = driver.findElement(By.id("employmentType"));
            employmentType.sendKeys("Unemployed");
            
            WebElement SoWType = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/section[5]/div/div/div[1]/div/sources-of-wealth/div/any/ng-switch/am-form-sources-of-wealth-np/div/div/div/div[2]/div/div/any[1]/div/div[1]/p/div/ins"));
    		JavascriptExecutor jse = (JavascriptExecutor)driver;
    		jse.executeScript("arguments[0].click()", SoWType);
            
            WebElement question0 = driver.findElement(By.id("question0"));
            question0.sendKeys("In what city were you married?");
            WebElement answer0 = driver.findElement(By.id("answer0"));
            answer0.sendKeys("White Plains");
            
            WebElement question1 = driver.findElement(By.id("question1"));
            question1.sendKeys("What is name of first boyfriend/girlfriend?");
            WebElement answer1 = driver.findElement(By.id("answer1"));
            answer1.sendKeys("Sunny");
            
            WebElement question2 = driver.findElement(By.id("question2"));
            question2.sendKeys("What is the name of a school you attended?");
            WebElement answer2 = driver.findElement(By.id("answer2"));
            answer2.sendKeys("SBU");
        }
        
        driver.findElement(By.id("continue")).click();
        
        WebElement nextPageConfirmation = (new WebDriverWait(driver, Duration.ofSeconds(50))).until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[4]/div/div/div[1]/h5")));
        if(nextPageConfirmation.getAttribute("value") == "Mobile Phone Confirmation") {
        	test.log(Status.PASS,"Successfully Completed About Me Section");
        }else {
        	test.log(Status.FAIL,"Issue Completing About Me Section");
        }
        Assert.assertEquals("Mobile Phone Confirmation", nextPageConfirmation.getText());
    }
    
    @Test(priority=3)
    public void verifyMobilePhoneConfirmationPopup(){
        String methodName = new Exception().getStackTrace()[0].getMethodName();
        test = extent.createTest(methodName,"Verify Mobile Phone Confirmation Popup");
        test.log(Status.INFO,"starting");
        
        //1. Click the ‘x’ at the top right to close the pop up 
        	//Going to need to see how this pops up when you click continue from previous test
        	//It may appear right away or after clicking something
        WebElement mobilePhonePopUpText = (new WebDriverWait(driver, Duration.ofSeconds(10))).until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[4]/div/div/div[1]/h5")));

        WebElement xButton = (new WebDriverWait(driver, Duration.ofSeconds(50))).until(ExpectedConditions.elementToBeClickable(By.className("close")));
        xButton.click();
        
        if(mobilePhonePopUpText.getAttribute("value") == "Mobile Phone Confirmation") {
        	test.log(Status.FAIL,"Mobile Phone Confirmaton Pop Up Has Not Been Closed");
        }else {
        	test.log(Status.PASS,"Mobile Phone Confirmation Pop Up Has Been Closed");
        }
        Assert.assertEquals("", mobilePhonePopUpText.getText());
        
        //Because in a fresh flow I would need to confirm the mobile number I can set a long wait, manually go into the mobile confirmation popup again
        //And verify my mobile number after getting the code, then it should go to the next page to verify the information
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(200));
        
    }
    
    @Test(priority=4)
    public void verifyNomineeInformationPage(){
        String methodName = new Exception().getStackTrace()[0].getMethodName();
        test = extent.createTest(methodName,"Verify Nominee Information Page");
        test.log(Status.INFO,"starting");
        test.log(Status.SKIP, "No Ready for Testing");
        
        //1. Select ‘No’ and continue 
        	//Asset if after we wait we compare against the new webpage title after hitting continue
        //After I Click X on the phone number pop up, I am seeing a different message saying confirming my phone # is mandatory to proceed. 
        //When I confirm my number it skips Nominee information and goes right to Configure Your Trading Account
        //This could be because I ran into account issues and had to create another test account myself, but this part will need to be skipped
        //This blocks the ability for the test to flow smoothly through one time, it requires a security code to be entered every time at login now

    }
    
    @Test(priority=5)
    public void verifyConfigureYourTradingAccount(){
        String methodName = new Exception().getStackTrace()[0].getMethodName();
        test = extent.createTest(methodName,"Configure Your Trading Account");
        test.log(Status.INFO,"starting");
        
        //1. Select the highest possible values for Annual Net Income, Net Worth and Liquid Net Worth
        	//Assert by comparing the know max to the value of these after we set to max
        Select annualIncome = new Select(driver.findElement(By.xpath("/html/body/div[2]/div/div/form/section[2]/div/div/div[1]/div/investment-config-form/div/am-form-financial-net-worth/general-financial-net-worth-form/am-form-select[1]/div/div/div[2]/am-select/select")));
        annualIncome.selectByVisibleText("> 1,000,000");
        
        Select netWorth = new Select(driver.findElement(By.xpath("/html/body/div[2]/div/div/form/section[2]/div/div/div[1]/div/investment-config-form/div/am-form-financial-net-worth/general-financial-net-worth-form/am-form-select[2]/div/div/div[2]/am-select/select")));
        netWorth.selectByVisibleText("> 50,000,000");
        
        Select liquidNetWorth = new Select(driver.findElement(By.xpath("/html/body/div[2]/div/div/form/section[2]/div/div/div[1]/div/investment-config-form/div/am-form-financial-net-worth/general-financial-net-worth-form/am-form-select[3]/div/div/div[2]/am-select/select")));
        liquidNetWorth.selectByVisibleText("> 5,000,000");
        
        //2. Select Growth for Investment Objectives & Intended Purpose of Trading 
        	//Assert by reading back value after we set it and compare against what we know it should be
        WebElement investmentObjective = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/section[2]/div/div/div[1]/div/investment-config-form/div/am-form-investment-objectives/div/div[2]/p/div/ins"));
		JavascriptExecutor jse = (JavascriptExecutor)driver;
		jse.executeScript("arguments[0].click()", investmentObjective);
        
        //3. Click on the Select more products now option
        	//Assert by looking for something that will only be there after you click this options
        WebElement selectMoreProductsNow = driver.findElement(By.linkText("Select more products now"));
        selectMoreProductsNow.click();
		
        
        //4. Remove Options by clicking the ‘x’ within the Options sections
        	//Assert by confirming the click worked and removed options
        WebElement xButtonOptions = (new WebDriverWait(driver, Duration.ofSeconds(50))).until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div/div/form/section[2]/div/div/div[1]/div/investment-config-form/div/trading-permission-selection-form/general-trading-permission/div[3]/div/div[1]/span/am-button/a/i")));
        xButtonOptions.click();
        
        //5. Select the highest experience values for each of the Stocks drop down menus
        	//Assert by confirming the selects were done correctly and proper values were changes
        Select STOCK_years = new Select(driver.findElement(By.xpath("/html/body/div[2]/div/div/form/section[2]/div/div/div[1]/div/investment-config-form/div/trading-permission-selection-form/general-trading-permission/div[2]/div/div[2]/div/div/div[1]/div/div[1]/am-select/select")));
        STOCK_years.selectByVisibleText("> 10 Years of Experience");
        
        Select STOCK_trades = new Select(driver.findElement(By.xpath("/html/body/div[2]/div/div/form/section[2]/div/div/div[1]/div/investment-config-form/div/trading-permission-selection-form/general-trading-permission/div[2]/div/div[2]/div/div/div[1]/div/div[2]/am-select/select")));
        STOCK_trades.selectByVisibleText("> 100 Trades per Year");
        
        Select STOCK_knowledge = new Select(driver.findElement(By.xpath("/html/body/div[2]/div/div/form/section[2]/div/div/div[1]/div/investment-config-form/div/trading-permission-selection-form/general-trading-permission/div[2]/div/div[2]/div/div/div[1]/div/div[3]/am-select/select")));
        STOCK_knowledge.selectByVisibleText("Extensive Knowledge");
        
        //6. Validate No is selected by default for the Regulatory Information section
        	//Assert by just comparing what is on webpage vs what we know it should be 
        WebElement RegQ1 = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/section[3]/div/div/div/div[1]/div/div/affiliate-designation/affiliate-designation-form/div/div/div/section/div/affiliate-question[1]/div/div/div[2]/p/am-switch/div/a[2]"));
        if (RegQ1.isSelected()){
        	test.log(Status.PASS,"Regulatory Question #1 is No");
        }else {
            test.log(Status.FAIL,"Regulatory Question #1 is Not No");
        }
        Assert.assertEquals("True", RegQ1.isSelected());
        
        WebElement RegQ2 = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/section[3]/div/div/div/div[1]/div/div/affiliate-designation/affiliate-designation-form/div/div/div/section/div/affiliate-question[2]/div/div/div[2]/p/am-switch/div/a[2]"));
        if (RegQ2.isSelected()){
        	test.log(Status.PASS,"Regulatory Question #2 is No");
        }else {
            test.log(Status.FAIL,"Regulatory Question #2 is Not No");
        }
        Assert.assertEquals("True", RegQ2.isSelected());
        
        WebElement RegQ3 = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/section[3]/div/div/div/div[2]/div/div/div/button[2]"));
        if (RegQ3.isSelected()){
        	test.log(Status.PASS,"Regulatory Question #3 is No");
        }else {
            test.log(Status.FAIL,"Regulatory Question #3 is Not No");
        }
        Assert.assertEquals("True", RegQ3.isSelected());
        
        //7. Pick a random option from the How Did You Hear About Us dropdown and click continue 
        	//Assert by making sure the value it was set to it a valid choice
        	//Is it possible to confirm randomness?
        Select howFoundL1 = new Select(driver.findElement(By.id("howFoundL1")));
        howFoundL1.selectByVisibleText("Search");
        
        driver.findElement(By.id("continue")).click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        
    }
    
    @Test(priority=6)
    public void verifyConfirmYourTaxResidence(){
        String methodName = new Exception().getStackTrace()[0].getMethodName();
        test = extent.createTest(methodName,"Confirm Your Tax Residence");
        test.log(Status.INFO,"starting");
        test.log(Status.SKIP, "No Ready for Testing");
        
        //1. Certify and select India from the dropdown menu in the Treaty Benefits Qualifications section
        	//Assert by making sure set values are reflecting changes
        
        //2. Complete the signature field and checkbox, then click Continue
        	//Assert by waiting and confirming we are on the next page and not met with an error message
        
        //Again, given the set of steps are a bit different I did not get to see this page in my flow, it skipped over this 
        //and went onto the Review and Sign section

    }
    
    @Test(priority=7)
    public void verifyReviewSndSignAgreements(){
        String methodName = new Exception().getStackTrace()[0].getMethodName();
        test = extent.createTest(methodName,"Review and Sign Agreements");
        test.log(Status.INFO,"starting");
        
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //1. Print all values within the Financial Information section
        	//Assert by making sure the values from financial Info section are what they should be
        WebElement finInfo = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/section[2]/div/div/div/div/account-review/account-info/div[3]"));
        test.log(Status.INFO, finInfo.getText());
        
        //2. Validate all agreements are checked by default 
        	//Assert by making sure they're all checked
        List<WebElement> checkboxes = driver.findElements(By.className("icheckbox_square-blue"));
        int boxCount = 1;
        for(WebElement checkbox: checkboxes) {
        	if(checkbox.isSelected()){
        		test.log(Status.PASS,"Checkbox #" + boxCount + "is checked by Default");
        	}else {
        		test.log(Status.FAIL,"Checkbox #" + boxCount + "is not checked by Default");
        	}
        	Assert.assertEquals("True", checkbox.isSelected());
        	boxCount += 1;
        }
        
        //3. Complete the Signature field and click Continue
        	//Assert by confirming we have made it past continue to next page
        WebElement signatures0 = driver.findElement(By.id("signatures0"));
        signatures0.sendKeys("Jonathan Palmieri");
        
        driver.findElement(By.id("continue")).click();
        
        WebElement nextPageConfirmation = (new WebDriverWait(driver, Duration.ofSeconds(50))).until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div/div/form/div[1]/platform-modal/div/div/div/div/div[1]/div/div/div/info-section/div/div[1]/div/div/div/div[3]/a")));
        if(nextPageConfirmation.getAttribute("value") == "Continue application") {
        	test.log(Status.PASS,"Have Continued Successfully");
        }else {
        	test.log(Status.FAIL,"Have Not Continued Successfully");
        }
        Assert.assertEquals("Continue application", nextPageConfirmation.getText());
        
        
    }
    
    @Test(priority=8)
    public void verifyPopUpAndApplicationStatus (){
        String methodName = new Exception().getStackTrace()[0].getMethodName();
        test = extent.createTest(methodName,"Pop Up and Application Status");
        test.log(Status.INFO,"starting");
        
        //1. A pop up will appear, click on Continue application once available 
        	//Wait until popup text is avaialble, Assert by continuing and making sure popup is gone
        WebElement continueAppButton = (new WebDriverWait(driver, Duration.ofSeconds(50))).until(ExpectedConditions.elementToBeClickable(By.xpath("/html/body/div[2]/div/div/form/div[1]/platform-modal/div/div/div/div/div[1]/div/div/div/info-section/div/div[1]/div/div/div/div[3]/a")));
        continueAppButton.click();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        
        //2. Confirm you have landed on the below page 
        	//I believe this is the next page after continue so this is the Assert portion, will need to confirm
        WebElement landingConfirmation = driver.findElement(By.xpath("/html/body/div[2]/div/div/form/section[2]/div/div/div/h3"));
        if(landingConfirmation.getAttribute("value") == "Final Steps to a Completed Application") {
        	test.log(Status.PASS,"Have Reached Final Landing Page Successfully");
        }else {
        	test.log(Status.FAIL,"Have Not Reached Final Landing Page Successfully");
        }
        Assert.assertEquals("Final Steps to a Completed Application", landingConfirmation.getText());
        

    }
    
    @AfterSuite
    public void endReport(){
        extent.flush();
        
    }
    
    
    @AfterSuite
    public void end(){
        driver.quit();

    }
    

}