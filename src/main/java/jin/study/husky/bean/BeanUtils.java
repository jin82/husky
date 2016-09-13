package jin.study.husky.bean;

import java.util.LinkedHashMap;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/09/13
 * \* Time: 14:02
 * \* Description:
 * \
 */
public class BeanUtils {
	public static LinkedHashMap<String,Object> container(){
		return BeanExplorer.container;
	}

	public static <T> T get(Class<?> clazz){
		return (T)container().get(clazz.getName());
	}

}
