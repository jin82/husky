package jin.study.husky.serivces;

import jin.study.husky.annotations.Bite;
import jin.study.husky.annotations.Sofa;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/09/13
 * \* Time: 14:12
 * \* Description:
 * \
 */
@Sofa
public class ManService {

	@Bite("hello")
	private HelloService helloService;

	@Bite
	private OkService okService;

	public String introduce(){
		String hello = helloService.info();
		return "husky :" + hello + okService.info();
	}
}
