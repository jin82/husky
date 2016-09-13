package jin.study.husky.bean;

import jin.study.husky.annotations.Bite;
import jin.study.husky.annotations.Sofa;
import jin.study.husky.exceptions.BeanException;
import jin.study.husky.exceptions.enums.ExceptionEnums;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;

import static jin.study.husky.exceptions.enums.ExceptionEnums.INSTANCE_FAIL;
import static jin.study.husky.exceptions.enums.ExceptionEnums.Inject_BEAN_MISS;

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

	private BeanInjecter injecter = new BeanInjecter();

	public BeanExplorer(String basePackage){
		finder = new BoneFinder(basePackage);
		refresh();
	}

	public void refresh(){
		finder.findBone();
		finder.boneName.forEach(this::initializeBean);
		injecter.inject();
	}

	private void initializeBean (String className){
		Class<?> source;
		try {
			source = Thread.currentThread().getContextClassLoader().loadClass(className);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			throw new BeanException(ExceptionEnums.CLASS_NOT_FOUND,className);
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
			throw new BeanException(INSTANCE_FAIL,beanName);
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

	/**
	 * \*
	 * \* User: jin82
	 * \* Date: 2016/09/13
	 * \* Time: 14:15
	 * \* Description: bean参数注入
	 * \
	 */
	private class BeanInjecter {

		public void inject(){

			container.forEach((k,v)->{

				Object bean = v;
				for (Field field : v.getClass().getDeclaredFields()) {
					Bite bite = field.getAnnotation(Bite.class);
					if(bite == null){
						continue;
					}

					field.setAccessible(true);
					Object sofa;
					if(StringUtils.isBlank(bite.value())){
						sofa = container.get(field.getType().getName());
					}else{
						sofa = container.get(bite.value());
					}

					if(sofa == null){
						throw new BeanException(Inject_BEAN_MISS,field.getName());
					}

					try {
						field.set(bean,sofa);
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}


				}
			});

		}


	}
}
