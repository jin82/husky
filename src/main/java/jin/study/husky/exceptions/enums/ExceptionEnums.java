package jin.study.husky.exceptions.enums;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/09/13
 * \* Time: 11:17
 * \* Description:
 * \
 */
public enum ExceptionEnums {
	CLASS_NOT_FOUND ("没有找到对应的类"),
	INSTANCE_FAIL  ("Bone实例化失败"),
	FIELD_ERROR ("属性名称错误"),
	NO_SUCH_METHOD ("方法名称错误"),
	Inject_BEAN_MISS ("注入bean失败，这个bean没有被注解")

	;
	private String msg;

	ExceptionEnums(String msg){
		this.msg = msg;
	}

	public String getMsg(){
		return this.msg;
	}

}
