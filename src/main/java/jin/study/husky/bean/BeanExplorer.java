package jin.study.husky.bean;

import jin.study.husky.annotations.Sofa;
import org.apache.commons.lang3.StringUtils;

import java.util.LinkedHashMap;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/09/12
 * \* Time: 19:39
 * \* Description:
 * \
 */
public class BeanExplorer {

	final static LinkedHashMap<String,Object> container = new LinkedHashMap<>();

	private BoneFinder finder;

	public BeanExplorer(String basePackage){
		finder = new BoneFinder(basePackage);
		refresh();
	}

	public void refresh(){
		finder.findBone();
		finder.boneName.forEach(this::initializeBean);
	}

	private void initializeBean (String className){
		Class<?> source;
		try {
			source = Thread.currentThread().getContextClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}
		Sofa sofa = source.getAnnotation(Sofa.class);
		if(sofa == null){
			return ;
		}

		String beanName;
		String sofaValue = sofa.value();
		if(StringUtils.isBlank(sofaValue)){
			beanName = className;
		}else{
			beanName = sofaValue;
		}

		Object sofaBean;
		try {
			sofaBean = source.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
			throw new RuntimeException();
		}

		container.put(beanName,sofaBean);
	}

	public static void main(String[] args) {
		String testPath = "jin.study.husky";
		BeanExplorer explorer = new BeanExplorer(testPath);
		container.forEach((k,v)->{
			System.out.printf("key -> %s value -> %s %n",k,v);
		});
	}

}
