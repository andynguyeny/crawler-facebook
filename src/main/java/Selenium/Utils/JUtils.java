package Selenium.Utils;

import java.io.File;

public class JUtils {
	public static boolean isDirExist(String path){
		File f = new File(path);
		return f.exists();
	}
	public static void createDirOnNoExist(String path){
		File dir = new File(path);
		if(!dir.exists()){
			dir.mkdirs();
		}
	}

}
