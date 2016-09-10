package jin.study.husky.bean;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/09/11
 * \* Time: 0:07
 * \* Description: bean 操作器类
 * \
 */
public class BeanOperation {

	private Object sourceBean;

	private Map<String,String> properties;


	private Object resultBean = null;

	public BeanOperation(Object sourceBean,Map<String,String> properties){
		this.sourceBean = sourceBean;
		this.properties = properties;
	}

	private void handleBean(){
		properties.forEach(this::sztSingleField);
		resultBean = sourceBean;
	}

	private void sztSingleField(String property,String value){
		String keys[] = property.split("\\.");
		if(keys.length == 0){
			throw new RuntimeException("属性名称错误");
		}
		try{
			for (int i = 0; i < keys.length; i++) {
				String key = keys[i];
				if(i == 0){
					continue;
				}
				//最后一个
				if(i == keys.length -1){
					Class<?> clazz = sourceBean.getClass();
					FieldOperation fieldOperation = new FieldOperation(clazz);
					Method setterMethod = fieldOperation.build(key).setterMethod();
					setterMethod.invoke(sourceBean,value);
				}

			}
		}catch (Exception e){
			e.printStackTrace();
		}

	}



	/**
	 * 将属性设置在对象上，并返回对象
	 * @return 操作后的对象
	 */
	public Object gztResultBean(){
		if (resultBean == null) {
			handleBean();
		}
		return resultBean;
	}

	private class FieldOperation{
		private Class<?> clazz ;

		private String fieldName;

		public FieldOperation(Class<?> clazz) {
			this.clazz = clazz;
		}

		public FieldOperation build(String fieldName){
			this.fieldName = fieldName;
			return this;
		}

		private Method getterMethod(){
			try{
				String methodName = "get" + toUpperCaseFirst(fieldName);
				return clazz.getDeclaredMethod(methodName);
			}catch (NoSuchMethodException e){
				e.printStackTrace();
				throw new RuntimeException();
			}
		}

		private Class<?> gztFieldType(){
			try {
				return clazz.getDeclaredField(fieldName).getType();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
				throw new RuntimeException();
			}
		}

		private Method setterMethod(){
			try{
				String methodName = "set" + toUpperCaseFirst(fieldName);
				return clazz.getDeclaredMethod(methodName, new Class<?>[]{gztFieldType()});
			}catch (NoSuchMethodException e){
				e.printStackTrace();
				throw new RuntimeException();
			}
		}

		private String toUpperCaseFirst(String str){
			return str.substring(0,1).toUpperCase() + str.substring(1);
		}
	}



}
