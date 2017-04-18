package Selenium.Utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.net.ssl.HttpsURLConnection;

public class JWeb {
	public static void downloadImage(String url,String dir,String name) {
		try {
			InputStream in = new URL(url).openStream();

			Files.copy(in, Paths.get(dir +"/"+name));

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void downloadImageFromHTTPS(String url,String dir,String name) {
		try {
			URL link = new URL(url);
			HttpsURLConnection con = (HttpsURLConnection) link.openConnection();

			Files.copy(con.getInputStream(), Paths.get(dir +"/"+name),StandardCopyOption.REPLACE_EXISTING);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static String getFullURL(String url) {
		StringWriter sw = null;
		BufferedWriter bufferWriter;
		sw = new StringWriter();
		bufferWriter = new BufferedWriter(sw);
		try {
			URL oracle = new URL(url);
			URLConnection yc = oracle.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				bufferWriter.write(inputLine + "\n");
			}
			in.close();
			bufferWriter.close();
			return sw.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	public static String getFullURLHTTPS(String urls) {
		StringWriter sw = null;
		BufferedWriter bufferWriter;
		sw = new StringWriter();
		bufferWriter = new BufferedWriter(sw);
		try {
			URL oracle = new URL(urls);
			HttpsURLConnection con = (HttpsURLConnection) oracle.openConnection();
			BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
			String inputLine;
			while ((inputLine = in.readLine()) != null) {
				bufferWriter.write(inputLine + "\n");
			}
			in.close();
			bufferWriter.close();
			return sw.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}
}
