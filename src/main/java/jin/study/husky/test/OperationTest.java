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
		Dog dog = new Dog();
		Map<String,String> properties = new HashMap<>();
		properties.put(BASE_PACKAGE+".model.Dog.name","Foo");
		BeanOperation operation = new BeanOperation(dog,properties);
		
		dog = (Dog)operation.gztResultBean();
		System.out.println(dog.getName());
	}
}
