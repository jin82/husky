package jin.study.husky.serivces;

import jin.study.husky.annotations.Sofa;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/09/13
 * \* Time: 14:08
 * \* Description:
 * \
 */
@Sofa("hello")
public class HelloService {

	public String info(){
		return "Hello World!";
	}
}
