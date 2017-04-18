package Selenium.AutoMoney;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;


public class GocTruyen8 {
	File file = new File("C://Selenium//chromedriver.exe");
	WebDriver driver;
	static String currentMessage;
	static String currentUser;
	static WebElement eChat;
	static WebElement eSub;
	static double THRES_HOLD = 1;
	static Random rd = new Random();
	static HashMap<String,Long> mapTime;
	static HashMap<String,Boolean> mapGender;
	static long INTERVAL = 20 * 1000;
	static HashMap<Integer,String> mapQuotes;
	static int NQ = 0;
	static String[] skipList ={"Chat","HÙNG","Phù Du"};
	static boolean meFake = false;
	static String linkFake = "";
	static JavascriptExecutor jse;
	public GocTruyen8() {
		ChromeOptions ops = new ChromeOptions();
		ops.addArguments("--disable-notifications");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		driver = new ChromeDriver(ops);
		mapTime = new HashMap<String, Long>();
		mapGender = new HashMap<String, Boolean>();
		mapQuotes  = new HashMap<Integer, String>();
	
		
		try {
			FileReader rd = new FileReader("D://quotes.dat");
			BufferedReader br = new BufferedReader(rd);
			int i = 0;
			while (true){
				String line = br.readLine();
				
				if (line == null)
					break;
				
				mapQuotes.put(++i, line);
				
				
			}
			br.close();
			rd.close();
			NQ = mapQuotes.size();
			System.out.println("Total "+NQ+ " quotes");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


	public GocTruyen8(boolean isFireFox) {
		file = new File("C:\\Selenium\\geckodriver.exe");

		System.setProperty("webdriver.gecko.driver", "C:\\Selenium\\geckodriver.exe");
		driver = new FirefoxDriver();
		mapTime = new HashMap<String, Long>();
		mapGender = new HashMap<String, Boolean>();
	}
	public int getGender(){
		WebElement e = driver.findElement(By.xpath("//li/span/a"));
		String at = e.getAttribute("style");
		//System.out.println(at);
		if(at.contains("Red") || at.contains("red"))
			return 0;
		else if (at.contains("Green")||at.contains("green"))
			return 1;
		return 2;

	}
	public boolean isMySelf(){
		WebElement e = driver.findElement(By.xpath("//li/span/a"));
		linkFake = e.getAttribute("href");
		if(linkFake.contains("460439"))
			return true;
		else return false;
		
	}
	public boolean isAdmin(){
		WebElement e = driver.findElement(By.xpath("//li/span/a"));
		String ss = e.getAttribute("href");
		if(ss.contains("80556"))
			return true;
		else return false;
	}
	

	public boolean isSkipName(String name){
		boolean re = false;
		meFake =false;
		for(int i = 0;i < skipList.length;++i)
			if(name.contains(skipList[i]))
				{
					re = true;
					break;
				}
		if(name.contains("Chat") &&!isMySelf())
			{
				meFake = true;			
				re = false;
			}
		return re;
	}
	public String getCurrentName() {
		WebElement e = driver.findElement(By.xpath("//li/span/a/b"));

		return e.getText();
	}

	public String getCurrentMess() {
		WebElement e = driver.findElement(By.xpath("//li/span[2]"));
		return e.getText();

	}

	public void sendMess(String mess) {

		eChat.sendKeys(mess);
		eChat.sendKeys(Keys.ENTER);
	}
	public void testMess(String mess){
		System.out.println(mess);
	}

	public boolean getDecision() {
		if (rd.nextDouble() < THRES_HOLD)
			return true;
		else
			return false;

	}
	public String genMessByGender(String user,int gender){
		if(gender == 0){
			return "#130 "+user + " thật xinh đẹp.";
		}
		else if(gender == 1){
			return "#183 "+user +" thật mạnh mẽ.";
		
		
		}
		else return "#251 "+user + " luôn sống thật với chính mình.";
	}
	public boolean getDecision(String user) {
		System.out.println("Get decision");
		if (rd.nextDouble() < THRES_HOLD)
			
			{
				Long u = mapTime.get(user);
				long t = System.currentTimeMillis();
				if (u==null)
					u = 0l;
				if ( (t-u)> INTERVAL)
					{
						
						System.out.println(t + " "+u + " "+(t-u));
						mapTime.put(user, t);
						return true;
					}
				else
					return false;
				
				
			}
		else
			return false;

	}
	public boolean getDecisionThredHold(double THRES){
		double t = rd.nextDouble();
		if(t <= THRES)
			return true;
		else return false;
	}
	private class CheckingRemindMe implements Runnable{
		GocTruyen8 wc8;
		public CheckingRemindMe(GocTruyen8 wc){
			wc8 = wc;
		}
		public boolean ifAskMe(){
			String mess = wc8.getCurrentMess().toLowerCase();
			//mess="chat  tra loi";
			//System.out.println("Checking..."+mess);
			if(mess.contains("chat") ||mess.contains("chát")  ){
			//	System.out.println("Contain");
				if(mess.contains("hoi") || mess.contains("hỏi") || mess.contains("trả lời") || mess.contains("tra loi") ){
					System.out.println("Here");
					if(wc8.getDecisionThredHold(0.5))
						wc8.sendMess("#216 Mình không biết.");

					else if(wc8.getDecisionThredHold(0.8))
						wc8.sendMess("#206 Hãy lắng nghe trái tim bạn");
					else if(wc8.getDecisionThredHold(0.8))
						wc8.sendMess("#100 Bạn đang hỏi mình");
					
					try {
						Thread.currentThread().sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				else{
					if(wc8.getDecisionThredHold(0.5))
						wc8.sendMess("#216 Mình không biết.");
					if(wc8.getDecisionThredHold(0.8))
						wc8.sendMess("#206 Hãy lắng nghe trái tim bạn");
					else
						wc8.sendMess("#206 Mong mọi người bỏ qua. Chúc một ngày vui vẻ.");
					
					try {
						Thread.currentThread().sleep(3000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				
			}
			return true;
			
		}

		public void run() {
			// TODO Auto-generated method stub
			while (true){
				try{
					ifAskMe();
				
					Thread.currentThread().sleep(500);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					String n = e.getClass().getName();
					if (n == "UnhandledAlertException"){
						driver.switchTo().alert().accept();
					}
					
					continue;
				}
			}
		}
		
	}
	public void SetName(String s){
		WebElement e = driver.findElement(By.name("nme"));
		jse.executeScript("document.getElementsByName('nme')[0].setAttribute('type','text')");
		e.sendKeys("s");
		
		
		
	}

	public void Run() {
		driver.get("http://goctruyen.com");

		System.out.println("Page title is: " + driver.getTitle());
		jse = ((JavascriptExecutor) driver);
		currentMessage = "";
		currentUser = "";
		String mess, user;
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
		driver.switchTo().frame("cboxform4-4248765");
		SetName("XYZ");
		eChat = driver.findElement(By.name("pst"));
		eSub = driver.findElement(By.name("sub"));
		eChat.clear();
		eChat.sendKeys("Chào mọi người");
		eSub.click();
		
		/*eChat = driver.findElement(By.id("chat-text"));
		System.out.println(eChat);
		(new Thread(new CheckingRemindMe(this))).start();
		
		

		while (true) {
			try {
				try {
					Thread.currentThread().sleep(100);
				} catch (InterruptedException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
				

				mess = getCurrentMess();
				user = getCurrentName();

				char[] m1 = mess.toCharArray();
				char[] u1 = user.toCharArray();
				mess = "";
				user = "";
				
				for (int i = 0; i < m1.length; ++i)
					if ((int) m1[i] < 30000)
						mess = mess + m1[i];
				for (int i = 0; i < u1.length; ++i)
					if ((int) (u1[i]) < 30000) {

						user = user + u1[i];
					}
				 //System.out.println(mess + " " +user);
				if(isAdmin()){
					sendMess("#177 Gặp admin rồi. Mình đi đây.");
					System.exit(-1);
				}
		

				if (!mess.equals(currentMessage) && !isSkipName(user)) {
					
					

					String ms = "@" + user + " #251 "+mapQuotes.get(rd.nextInt(NQ));
					//if(getDecisionThredHold(0.2))
									
					//if(mess.contains("Chat") || mess.contains("chat"))
					//	ms = "@" +user + " Chúc bạn một ngày vui vẻ. #251";
					if(getDecisionThredHold(0.3))
						ms = genMessByGender(user, getGender());	
					if(meFake)
						ms = "Bạn là kẻ giả mạo." + linkFake;
					currentMessage = mess;
					currentUser = user;

					if (!getDecision(user))
						continue;
					
					
					sendMess(ms);
					System.out.println("Sending mess " + ms);
					try {
						Thread.currentThread().sleep(180000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			} catch (Exception e) {
				e.printStackTrace();
				String n = e.getClass().getName();
				if (n == "UnhandledAlertException"){
					driver.switchTo().alert().accept();
				}
				
				continue;
			}
		}*/

	}

	public static void main(String[] args) {
		GocTruyen8 wc = new GocTruyen8();
		wc.Run();
	}

}
