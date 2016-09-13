package jin.study.husky.bean;

import jin.study.husky.exceptions.BeanException;
import jin.study.husky.exceptions.enums.ExceptionEnums;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.Map;

import static jin.study.husky.exceptions.enums.ExceptionEnums.FIELD_ERROR;
import static jin.study.husky.exceptions.enums.ExceptionEnums.INSTANCE_FAIL;
import static jin.study.husky.exceptions.enums.ExceptionEnums.NO_SUCH_METHOD;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/09/11
 * \* Time: 0:07
 * \* Description: bean 操作器类
 * \
 */
public class BeanOperation {

	/**
	 * 操作的根BEAN
	 */
	private Object rootBean;

	private String className;

	private Class<?> clazz ;

	/**
	 * 当前操作的BEAN
	 */
	private Object sourceBean;

	private Map<String,String> properties;

	private Object resultBean = null;

	public FieldOperation fieldOperation = new FieldOperation();

	public BeanOperation(String className,Map<String,String> properties){
		this.className = className;
		this.properties = properties;
		try {
			this.clazz = Class.forName(className);
		} catch (ClassNotFoundException e) {
			throw new BeanException(ExceptionEnums.CLASS_NOT_FOUND,className);
		}
	}


	/**
	 * 将数据注入BEAN
	 */
	private void handleBean(){
		try {
			rootBean = this.clazz.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new BeanException(INSTANCE_FAIL , clazz.getName());
		}
		properties.forEach(this::sztSingleField);
		resultBean = sourceBean;
	}

	/**
	 * 设置单个field的值
	 * @param property field名称
	 * @param value 值
	 */
	private void sztSingleField(String property,String value){
		String keys[] = property.split("\\.");
		if(keys.length == 0){
			throw new BeanException(FIELD_ERROR);
		}

		for (int i = 0; i < keys.length; i++) {
			String key = keys[i];

			if(i == 0){
				sourceBean = rootBean; //当前操作的bean初始化为根bean
				continue;
			}
			try{
				fieldOperation.build(key);
				//最后一个
				if(i == keys.length -1){
					Method setterMethod = fieldOperation.setterMethod();

					setterMethod.invoke(sourceBean,fieldOperation.valueFactory(value));
				}else{
					Object nextBean = fieldOperation.getterMethod().invoke(sourceBean);
					if(nextBean == null){
						String className = fieldOperation.gztFieldType().getName();
						nextBean = Class.forName(className).newInstance();
						fieldOperation.setterMethod().invoke(sourceBean,nextBean);
					}
					sourceBean = nextBean;
				}
			}catch (Exception e){
				throw new BeanException(FIELD_ERROR,"调用 -> " + key +", e ->" + e);
			}

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

	/**
	 * field控制器
	 */
	private class FieldOperation{

		private String fieldName;

		public FieldOperation build(String fieldName){
			this.fieldName = fieldName;
			return this;
		}

		/**
		 * 得到当前的getter方法
		 * @return getter方法
		 */
		private Method getterMethod(){
			String methodName = "get" + toUpperCaseFirst(fieldName);
			try{
				return sourceBean.getClass().getDeclaredMethod(methodName);
			}catch (NoSuchMethodException e){
				e.printStackTrace();
				throw new BeanException(NO_SUCH_METHOD,methodName);
			}
		}

		/**
		 * 得到数据类型
		 * @return 数据类型
		 */
		private Class<?> gztFieldType(){
			try {
				return sourceBean.getClass().getDeclaredField(fieldName).getType();
			} catch (NoSuchFieldException e) {
				throw new BeanException(FIELD_ERROR, fieldName);
			}
		}

		/**
		 * 得到当前的setter方法
		 * @return setter方法
		 */
		private Method setterMethod(){
			String methodName = "set" + toUpperCaseFirst(fieldName);
			try{
				return sourceBean.getClass().getDeclaredMethod(methodName, new Class<?>[]{gztFieldType()});
			}catch (NoSuchMethodException e){
				throw new BeanException(NO_SUCH_METHOD,methodName);
			}
		}

		/**
		 * 将字符串第一位变为大写
		 * @param str 原字符串
		 * @return 修改后的字符串
		 */
		private String toUpperCaseFirst(String str){
			return str.substring(0,1).toUpperCase() + str.substring(1);
		}

		/**
		 * 返回当前field的正确类型的值
		 * @param value String类型的value
		 * @return 类型直接的值
		 */
		private Object valueFactory(String value){
			try {
				Type type = gztFieldType();
				if (type == String.class) {
					return value;
				} else if (type == Integer.class) {
					return Integer.parseInt(value);
				}
			}catch (Exception e){
				throw new BeanException("参数类型错误",value);
			}
			return null;
		}
	}



}
