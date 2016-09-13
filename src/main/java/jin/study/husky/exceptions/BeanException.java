package jin.study.husky.exceptions;

import jin.study.husky.exceptions.enums.ExceptionEnums;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/09/13
 * \* Time: 11:15
 * \* Description:
 * \
 */
public class BeanException extends RuntimeException{

	public BeanException(ExceptionEnums e){
		super(e.getMsg());
	}

	public BeanException(String msg,String moreInfo){
		super(msg +" -> " + moreInfo);
	}

	public BeanException(ExceptionEnums e, String moreInfo){
		this(e.getMsg(),moreInfo);
	}

}
