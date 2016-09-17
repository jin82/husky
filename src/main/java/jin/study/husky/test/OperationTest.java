package jin.study.husky.test;

import jin.study.husky.bean.BeanOperation;
import jin.study.husky.model.Dog;

import java.util.HashMap;
import java.util.Map;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/09/11
 * \* Time: 0:47
 * \* Description:
 * \
 */
public class OperationTest {

	public static final String BASE_PACKAGE = "jin.study.husky";

	public static void main(String[] args) {
		Map<String,String> properties = new HashMap<>();
		properties.put("Dog.name","Foo");
		properties.put("Dog.age","10");
		properties.put("Dog.friend.name","Bar");
		properties.put("Dog.friend.mouse.mouseName","mouse");
		BeanOperation operation = new BeanOperation(BASE_PACKAGE+".model.Dog",properties);
		
		Dog dog = operation.gztResultBean();
		System.out.println(dog);
	}
}
