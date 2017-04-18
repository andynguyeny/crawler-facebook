package Selenium.AutoMoney;

import java.io.File;
import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Facebook {
 	final File file = new File("C://Selenium//chromedriver.exe");
    WebDriver driver;
    public Facebook(){
    	 ChromeOptions ops = new ChromeOptions();
         ops.addArguments("--disable-notifications");
    	System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
    	driver = new ChromeDriver(ops);

    }
    
	public void checkAlert() {
	    try {
	        WebDriverWait wait = new WebDriverWait(driver, 2);
	        wait.until(ExpectedConditions.alertIsPresent());
	        Alert alert = driver.switchTo().alert();
	        alert.dismiss();
	        System.out.println("Dismissing");
	    } catch (Exception e) {
	        //exception handling
	    	System.out.println("WTF");
	    }
	}
	public void GetRobertTown(){
		driver.get("https://www.facebook.com/");
        // Alternatively the same thing can be done like this
        // driver.navigate().to("http://www.google.com");

        // Find the text input element by its name
       // WebElement element = driver.findElement(By.name("q"));
		JavascriptExecutor js = ((JavascriptExecutor) driver);
        WebElement eUserName = driver.findElement(By.name("email"));
        eUserName.sendKeys("");
        WebElement ePassword = driver.findElement(By.name("pass"));
        ePassword.sendKeys("");
        ePassword.submit();
        driver.get("https://www.facebook.com/pg/RobertIELTStown/photos/?ref=page_internal");
        
        js.executeScript("window.scrollBy(0,450)", "");
        
        List<WebElement> listFig = driver.findElements(By.className(" _2eea"));
        System.out.println(listFig.size());
        WebElement ee = listFig.get(0).findElement(By.xpath(".//a/img"));
        System.out.println(ee.getAttribute("src"));
        
	}
	public void Run(){
		 	driver.get("https://www.facebook.com/");
	        // Alternatively the same thing can be done like this
	        // driver.navigate().to("http://www.google.com");

	        // Find the text input element by its name
	       // WebElement element = driver.findElement(By.name("q"));
	        WebElement eUserName = driver.findElement(By.name("email"));
	        eUserName.sendKeys("tieudao.nda@gmail.com");
	        WebElement ePassword = driver.findElement(By.name("pass"));
	        ePassword.sendKeys("Myfacebook1");
	        ePassword.submit();
	        driver.get("https://www.facebook.com/pg/RobertIELTStown/photos/?ref=page_internal");
	        
	        
	        System.out.println("Page title is: " + driver.getTitle());
	        JavascriptExecutor jse = ((JavascriptExecutor) driver);
	        WebElement e = driver.findElement(By.name("mercurymessages"));
	        e.click();
	        
	        //e = driver.findElement(By.xpath("//span[@data-offset-key='))
/*
	      //  checkAlert();
	        try {
				Thread.currentThread().sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");


	        try {
	   			Thread.currentThread().sleep(1000);
	   		} catch (InterruptedException e) {
	   			// TODO Auto-generated catch block
	   			e.printStackTrace();
	   		}
	        jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");

	           try {
	   			Thread.currentThread().sleep(1000);
	   		} catch (InterruptedException e) {
	   			// TODO Auto-generated catch block
	   			e.printStackTrace();
	   		}
	           jse.executeScript("window.scrollTo(0, document.body.scrollHeight)");*/
	           
	          
	}
	public static void main(String[] args) {
      Facebook fb = new Facebook();
      fb.GetRobertTown();
    }
}
