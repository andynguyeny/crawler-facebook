package Selenium.AutoMoney;

import java.io.File;
import java.util.Formatter;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
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

import Selenium.Utils.JUtils;
import Selenium.Utils.JWeb;

public class FacebookCW {
	static String ACCESS_TOKEN = "";
	//static String ROOT_DIR = "D:/Data/Crawler/Facebook/Robert Town IELTS/";//Robert Town
	//static String ROOT_DIR = "D:/Data/Crawler/Facebook/Chua Writing Mien Phi/";
	//static String ROOT_DIR = "D:/Data/Crawler/Facebook/Huong dan viet IELTS/";
	static String ROOT_DIR = "D:/Data/Crawler/Facebook/IELTS SHARE/";

	static String formatAlbumPhotosUrl = "https://graph.facebook.com/v2.8/%s/photos/?access_token=%s&limit=80";
	static String formatObjectPictureUrl = "https://graph.facebook.com/v2.8/%s/picture/?access_token=" + ACCESS_TOKEN;
	static int index = 0;

	synchronized int getNextIndex() {
		System.out.print("\r" + index);
		return index++;
	}

	final File file = new File("C://Selenium//chromedriver.exe");

	/*
	 * public void getSinglePicture() {
	 * 
	 * String url =
	 * "https://graph.facebook.com/v2.8/485721618268955/photos/?access_token=1440624295990167|JBkkyOqgipCof714YlEzORYs_oA";
	 * String line = (JWeb.getFullURLHTTPS(url)); JSONObject json = new
	 * JSONObject(line); System.out.println(json.toString());
	 * System.out.println(json.get("data"));
	 * 
	 * }
	 */

	public void getAlbum(String albumName, String albumId) {
		String saveDir = ROOT_DIR + albumName;
		JUtils.createDirOnNoExist(saveDir);
		String startURL = String.format(formatAlbumPhotosUrl, albumId, ACCESS_TOKEN);
		JSONObject albumPhotosJSON = new JSONObject(JWeb.getFullURLHTTPS(startURL));
		Thread startThread = new ImageCrawler(albumPhotosJSON, saveDir);
		startThread.start();
	}

	private void getSingleImage(String id, String dir, String name) {

		String urls = String.format(formatObjectPictureUrl, id);
		JWeb.downloadImageFromHTTPS(urls, dir, name);
	}

	class ImageCrawler extends Thread {
		JSONObject data;
		String dir;

		public ImageCrawler(JSONObject data, String dir) {
			// TODO Auto-generated constructor stub
			this.data = data;
			this.dir = dir;
		}

		@Override
		public void run() {
			try {
				
				String nextURL = data.getJSONObject("paging").getString("next");
				if (nextURL != null) {
					System.out.println(nextURL);
					System.out.println("Start new thread "+nextURL);


					JSONObject nextObj = new JSONObject(JWeb.getFullURLHTTPS(nextURL));
					 Thread next = new ImageCrawler(nextObj,dir);
					 next.start();
				}
			} catch (Exception e) {
				//e.printStackTrace();
			}
			try {
				JSONArray obj = data.getJSONArray("data");
				int ln = obj.length();
				for (int i = 0; i < ln; ++i) {
					JSONObject imgInfo = obj.getJSONObject(i);
					String id = imgInfo.getString("id");
					String name = "";
					{
						if (imgInfo.has("name"))
							name = imgInfo.getString("name");
						int sz = name.length();
						if (sz > 70)
							sz = 70;
						name = name.substring(0, sz);
						name = StringUtils.stripAccents(name);
					}
					name = getNextIndex() + "_" + name.replaceAll("[\n\r]", " ").replaceAll("[^a-zA-Z\\s]", "")
							+ ".jpg".replaceAll("\\s+", "\\s");
					// System.out.println("X __: "+name);
					getSingleImage(id, dir, name);

				}
			} catch (Exception e) {
				e.printStackTrace();
				// System.exit(-1);
			}

		}
	}

	public void run() {
		String albumName = "Timeline Photos";
		//String albumId = "485721618268955";//Robert town
		//String albumId = "300215900167043";//Chua writing
		//String albumId = "557032450993351";//Hoc viet IELTS
		String albumId = "1441782932749490";//IELTS share
		getAlbum(albumName, albumId);
	}

	public static void main(String[] args) {
		FacebookCW fb = new FacebookCW();
		// fb.getRobertTown();
		fb.run();
	}
}
