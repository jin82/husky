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
 * \* Description: Bone 也就是 class =。=
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
			new Thread(new ExplorerRunable(filePath)).start();
		}

	}

	/**
	 * 注册所有的Bone
	 * 已废弃
	 * @see jin.study.husky.bean.BoneFinder.ExplorerRunable
	 * @param parentDir 目标文件夹
	 *
	 */
	@Deprecated
	private void exploreDir(File parentDir){
		for(File file : parentDir.listFiles()){
			if(file.isDirectory()){
				exploreDir(file);
			}else{
				String className = convertClassPath(file.getAbsolutePath());
				if(className != null){
					System.out.println(className);
					boneName.add(className);
				}
			}
		}
	}

	//TODO 错误后返回null
	private String convertClassPath(String filePath){
		String tempPath = filePath.replaceAll("\\\\",".");
		if(!tempPath.endsWith(".class")){
			return null;
		}
		try{
			tempPath = tempPath.substring(tempPath.lastIndexOf(packagePath),tempPath.lastIndexOf(".class"));
		}catch (Exception e){

			e.printStackTrace();
			throw new RuntimeException("filepath is -> " + filePath);
		}

		return tempPath;
	}


	public static void main(String[] args) {
		BoneFinder boneFinder = new BoneFinder("jin.study.husky");
		boneFinder.findBone();
		BeanExplorer.container.forEach((key,value) -> {
			System.out.printf("key -> %s , value -> %s%n", key, value);
		});
	}


	/**
	 * 多线程查找并注册Bone
	 */
	private class ExplorerRunable implements Runnable{

		private File file;

		public ExplorerRunable(File file){
			this.file = file;
		}

		@Override
		public void run() {
			if(file.isDirectory()){
				exploreDir(file);
			}else{
				String className = convertClassPath(file.getAbsolutePath());
				if(className != null){
					if(BeanSwitch.isDug){
						System.out.println(className);
					}
					boneName.add(className);
				}
			}
		}

		private void exploreDir(File parentDir){
			for(File file : parentDir.listFiles()){
				new Thread(new ExplorerRunable(file)).start();
			}
		}
	}

}
