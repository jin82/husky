package jin.study.husky.bean;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.Enumeration;
import java.util.LinkedHashSet;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/09/12
 * \* Time: 20:25
 * \* Description:
 * \
 */
public class BoneFinder {

	private String packagePath;

	private String dirPath;

	final LinkedHashSet<String> boneName = new LinkedHashSet<>();

	public BoneFinder(String packagePath) {
		this.packagePath = packagePath;
	}

	void findBone() {

		dirPath = packagePath.replaceAll("\\.", "\\/");

		Enumeration<URL> urls;
		try {
			urls = Thread.currentThread().getContextClassLoader().getResources(dirPath);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

		while (urls.hasMoreElements()){
			URL url = urls.nextElement();
			String path = null;
			try {
				path = URLDecoder.decode(url.getFile(),"UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				throw new RuntimeException();
			}

			File filePath = new File(path);
			exploreDir(filePath);

		}

	}

	private void exploreDir(File parentDir){
		for(File file : parentDir.listFiles()){
			if(file.isDirectory()){
				exploreDir(file);
			}else{
				String className = convertClassPath(file.getAbsolutePath());
				boneName.add(className);
			}
		}
	}

	private String convertClassPath(String filePath){
		String tempPath = filePath.replaceAll("\\\\",".");
		tempPath = tempPath.substring(tempPath.lastIndexOf(packagePath),tempPath.lastIndexOf(".class"));
		return tempPath;
	}


	public static void main(String[] args) {
		BoneFinder boneFinder = new BoneFinder("jin.study.husky");
		boneFinder.findBone();
		BeanExplorer.container.forEach((key,value) -> {
			System.out.printf("key -> %s , value -> %s%n", key, value);
		});
	}

}
