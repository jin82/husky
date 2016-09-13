package jin.study.husky.test;

import jin.study.husky.bean.BeanExplorer;
import jin.study.husky.bean.BeanUtils;
import jin.study.husky.serivces.ManService;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/09/12
 * \* Time: 23:29
 * \* Description:
 * \
 */
public class BoneFinderTest {

	public static void main(String[] args) {
		//BeanSwitch.isDug = true;
		BeanExplorer explorer = new BeanExplorer("jin.study.husky");

		BeanUtils.container().forEach((k,v)->{
			System.out.printf("k= %s,v= %s %n",k,v);
		});

		ManService service = BeanUtils.get(ManService.class);
		System.out.println(service.introduce());


	}

}
