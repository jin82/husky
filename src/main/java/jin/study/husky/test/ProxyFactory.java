package jin.study.husky.test;

import jin.study.husky.serivces.interfaces.Eat;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Random;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/09/16
 * \* Time: 21:01
 * \* Description:
 * \
 */
public class ProxyFactory implements InvocationHandler{
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		System.out.println(proxy.getClass().getInterfaces());
		return new Random().nextInt();
	}

	public void test() throws Exception{
		Eat eat = (Eat)Proxy.newProxyInstance(Eat.class.getClassLoader(),new Class[]{Eat.class},this);
		System.out.println(eat.beforeEat());
		Thread.sleep(1000);
		System.out.println(eat.afterEat());
	}

	public static void main(String[] args) throws Exception{
		ProxyFactory factory = new ProxyFactory();
		factory.test();
	}
}


