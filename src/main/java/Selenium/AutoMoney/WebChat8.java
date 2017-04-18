package Selenium.AutoMoney;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;

public class WebChat8 {
	File file = new File("C://Selenium//chromedriver.exe");
	WebDriver driver;
	static String currentMessage;
	static String currentUser;
	static String currentLinkID;
	static int currentGender;
	
	static String oldMessage;
	static String oldUser;
	

	
	static WebElement eSend;
	static double THRES_HOLD = 1;
	static Random rd = new Random();
	static HashMap<String, Long> mapTime;
	static HashMap<String, Boolean> mapGender;
	static long INTERVAL = 20 * 1000;
	static HashMap<Integer,HashMap<Integer, String>> mapQuotes;
	static int NQ = 0;
	static String[] skipList = { "Wh", "HÙNG", "Phù Du","Kỳ Phong Thiếu Gia","Nhím","ĐI","Gió Miên Man","ỚT HIỂM DỊU DÀNG","Sand" };
	static String[] skipID = {"439503","459017","460705","141681","459017","461563","454795"};

	static boolean meFake = false;
	static WebElement eChatBox;
	

	

	public WebChat8() {
		ChromeOptions ops = new ChromeOptions();
		ops.addArguments("--disable-notifications");
		System.setProperty("webdriver.chrome.driver", file.getAbsolutePath());
		driver = new ChromeDriver(ops);
		mapTime = new HashMap<String, Long>();
		mapGender = new HashMap<String, Boolean>();
		mapQuotes = new HashMap<Integer,HashMap<Integer, String>>();
		int cc = 0;
		ArrayList<String> lsFiles = new ArrayList<String>();
		lsFiles.add("quotes_women.dat");
		lsFiles.add("quotes_men.dat");
		lsFiles.add("quotes.dat");
		
		

		try {
		for(int ii = 0; ii < lsFiles.size(); ++ii){
			
			HashMap<Integer,String> quotes = new HashMap<Integer,String>();
			FileReader rd = new FileReader(lsFiles.get(ii));
			BufferedReader br = new BufferedReader(rd);
			int i = 0;
			while (true) {
				String line = br.readLine();
			
				if (line == null)
					break;
				
				if (line.length()>190)
					continue;

				quotes.put(++i, line);

			}
			mapQuotes.put(ii, quotes);
			br.close();
			rd.close();
			NQ = mapQuotes.size();
			System.out.println("Total " + NQ + " quotes");
		}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getCurrentQuote(){
		if(currentGender == 0){
			return mapQuotes.get(0).get(rd.nextInt(mapQuotes.get(0).size()));
		}
		else if(currentGender == 1){
			return mapQuotes.get(1).get(rd.nextInt(mapQuotes.get(1).size()));

		}
		else {
			return mapQuotes.get(2).get(rd.nextInt(mapQuotes.get(2).size()));

		}
		
	}
	

	public WebChat8(boolean isFireFox) {
		file = new File("C:\\Selenium\\geckodriver.exe");

		System.setProperty("webdriver.gecko.driver", "C:\\Selenium\\geckodriver.exe");
		driver = new FirefoxDriver();
		mapTime = new HashMap<String, Long>();
		mapGender = new HashMap<String, Boolean>();
	}

/*	public int getGender() {
		WebElement e = driver.findElement(By.xpath("//li/span/a"));
		String at = e.getAttribute("style");
		// System.out.println(at);
		if (at.contains("Red") || at.contains("red"))
			return 0;
		else if (at.contains("Green") || at.contains("green"))
			return 1;
		return 2;

	}*/

	public boolean isMySelf() {
		
		if (currentLinkID.contains("460439"))
			return true;
		else
			return false;

	}

	public boolean isAdmin() {
	
		if (currentLinkID.contains("80556"))
			return true;
		else
			return false;
	}
	public boolean skipCurrent(){
		
		for(int i = 0; i < skipID.length; ++i)
		{
			if (currentLinkID.contains(skipID[i]))
			{
				return true;
				
			}
		}
		return false;
	}

	public boolean isSkipName(String name) {
		boolean re = false;
		meFake = false;
		for (int i = 0; i < skipList.length; ++i)
			if (name.contains(skipList[i])) {
				re = true;
				break;
			}
		for(int i = 0; i < skipID.length; ++i)
		{
			if (currentLinkID.contains(skipID[i]))
			{
				re = true;
				break;
			}
		}
		if (name.contains("Wh") && !isMySelf()) {
			meFake = true;
			re = false;
		}
		return re;
	}

/*	public String getCurrentName() {
		WebElement e = driver.findElement(By.xpath("//li/span/a/b"));
		
		return e.getText();
	}

	public String getCurrentMess() {
		WebElement e = driver.findElement(By.xpath("//li/span[2]"));
		return e.getText();

	}*/
	public void getCurrentChat(){
		//Get current user infor
		
		WebElement e = eChatBox.findElement(By.xpath(".//li"));


		currentMessage = e.findElement(By.xpath(".//span[2]")).getText();
	

		currentUser = e.findElement(By.xpath(".//span/a/b")).getText();
		
		//Get gender
		
		e = e.findElement(By.xpath(".//span/a"));
		String at = e.getAttribute("style");
		if (at.contains("Red") || at.contains("red"))
			currentGender = 0;
		else if (at.contains("Green") || at.contains("green"))
			currentGender = 1;
		else
			currentGender = 2;
		
		//Get identification
		currentLinkID = e.getAttribute("href");
		
		

	}

	public void sendMess(String mess) {

		eSend.sendKeys(mess);
		eSend.sendKeys(Keys.ENTER);
	}

	public void testMess(String mess) {
		System.out.println(mess);
	}

	public boolean getDecision() {
		if (rd.nextDouble() < THRES_HOLD)
			return true;
		else
			return false;

	}

	public String genMessByGender(String user, int gender) {
		if (gender == 0) {
			return "#130 " + user + " thật xinh đẹp.";
		} else if (gender == 1) {
			return "#183 " + user + " thật mạnh mẽ.";

		} else
			return "#251 " + user + " luôn sống thật với chính mình.";
	}

	public boolean getDecision(String user) {
		System.out.println("Get decision");
		if (rd.nextDouble() < THRES_HOLD)

		{
			Long u = mapTime.get(user);
			long t = System.currentTimeMillis();
			if (u == null)
				u = 0l;
			if ((t - u) > INTERVAL) {

				System.out.println(t + " " + u + " " + (t - u));
				mapTime.put(user, t);
				return true;
			} else
				return false;

		} else
			return false;

	}

	public boolean getDecisionThredHold(double THRES) {
		double t = rd.nextDouble();
		if (t <= THRES)
			return true;
		else
			return false;
	}

	private class CheckingRemindMe implements Runnable {
		WebChat8 wc8;

		public CheckingRemindMe(WebChat8 wc) {
			wc8 = wc;
		}
	
		public boolean ifAskMe() {
			if(isMySelf() || skipCurrent())
				return false;
			String mess = currentMessage.toLowerCase();
			// mess="chat tra loi";
			// System.out.println("Checking..."+mess);
			if (mess.contains("wh") || mess.contains("WH") || mess.contains("Wh")) {
				// System.out.println("Contain");
				String user = filterUserName(currentUser);
				if (mess.contains("hoi") || mess.contains("hỏi") || mess.contains("trả lời")
						|| mess.contains("tra loi")) {
					try {
						System.out.println("Here");
						if (wc8.getDecisionThredHold(0.5))
							wc8.sendMess("@"+user+" #216 Mình không biết.");

						else if (wc8.getDecisionThredHold(0.5))
							wc8.sendMess("@"+user+" #206 Hãy lắng nghe trái tim bạn");
						else if (wc8.getDecisionThredHold(0.8))
							wc8.sendMess("@"+user + " #100 Bạn đang hỏi mình");

						Thread.currentThread().sleep(3000);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						String n = e.getClass().getName();
						if (n == "UnhandledAlertException") {
							driver.switchTo().alert().accept();
						}
					}
				} else {
					try {
						if (wc8.getDecisionThredHold(0.4))
							wc8.sendMess("@"+user+" #216 Mình không biết.");
						else if (wc8.getDecisionThredHold(0.8))
							wc8.sendMess("@"+user+" #206 Hãy lắng nghe trái tim bạn");
						else
							wc8.sendMess("@"+user+" #206 Mong mọi người bỏ qua. Chúc một ngày vui vẻ.");

						Thread.currentThread().sleep(4000);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						String n = e.getClass().getName();
						if (n == "UnhandledAlertException") {
							driver.switchTo().alert().accept();
						}
					}
				}

			}
			return true;

		}
		public void avoidAdmin(){
			if (isAdmin()){
				sendMess("#177 Boss xuất hiện");
				System.exit(-1);
			}
		}

		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				try {
					getCurrentChat();
					ifAskMe();
					avoidAdmin();
					Thread.currentThread().sleep(400);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					
					String v = e.getMessage();
					System.out.println("Exception "+v);
					
					if (v.contains("Vui lòng không chat")) {
						driver.switchTo().alert().accept();
					}

					continue;
				}
			}
		}

	}
	public void doLogin(){
		driver.get("http://henho8.com/Account/Login");
		WebElement eUserName = driver.findElement(By.name("Email"));
		eUserName.sendKeys("");
		WebElement ePassword = driver.findElement(By.name("Password"));
		ePassword.sendKeys("");
		ePassword.submit();
	}
	public void doInitialize(){
		currentMessage = "";
		currentUser = "";
		eSend = driver.findElement(By.id("chat-text"));
		eChatBox = driver.findElement(By.id("chat-box-group"));
		oldMessage = "";
		oldUser = "";
	}
	public String filterUserName(String user){
	

		char[] u1 = user.toCharArray();
	
		user = "";

		for (int i = 0; i < u1.length; ++i)
			if ((int) u1[i] < 30000)
				user = user + u1[i];
		return user;
	}
	public void Run() {

		doLogin();
		doInitialize();
		
		String mess, user;
		System.out.println("Starting...");
		(new Thread(new CheckingRemindMe(this))).start();

		while (true) {
			try {
				try {
					Thread.currentThread().sleep(100);
				} catch (InterruptedException ex) {
					// TODO Auto-generated catch block
					ex.printStackTrace();
				}
				
				getCurrentChat();
	
				mess = currentMessage;
				user = currentUser;

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
				// System.out.println(mess + " " +user);
				if (isAdmin()) {
					sendMess("#177 Gặp admin rồi. Mình đi đây.");
					System.exit(-1);
				}

				if (!mess.equals(oldMessage) && !isSkipName(user)) {

					String ms = "@" + user + " #251 " + getCurrentQuote();
					 if(getDecisionThredHold(0.2))

					 if(mess.contains("Chat") || mess.contains("chat"))
					 ms = "@" +user + " Chúc bạn một ngày vui vẻ. #251";
					if (getDecisionThredHold(0.2))
						ms = genMessByGender(user, currentGender);
					if (meFake)
						ms = "Bạn là kẻ giả mạo." + currentLinkID;
					oldMessage = mess;
					oldUser = user;

					if (!getDecision(user))
						continue;
					System.out.println(ms.length());
					sendMess(ms);
					System.out.println("Sending mess " + ms);
					try {
						Thread.currentThread().sleep(15000);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

				}
			} catch (Exception e) {
				String v = e.getMessage();
				System.out.println("Exception "+v);
				
				if (v.contains("Vui lòng không chat")) {
					driver.switchTo().alert().accept();
				}
				continue;
			}
		}
		// getCurrentName();

		// WebElement e = driver.findElement(By.id("chat-text"));
		// e.sendKeys("#251");
		// e.sendKeys("Chào mọi người, mình là Chat.");
		// e.sendKeys(Keys.ENTER);

	}

	public static void main(String[] args) {
		System.setProperty("file.encoding", "UTF-8");
		WebChat8 wc = new WebChat8();
		wc.Run();
	}

}
