package jin.study.husky.model;

import jin.study.husky.annotations.Sofa;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/09/11
 * \* Time: 0:48
 * \* Description:
 * \
 */
@Sofa
public class Dog {

	private String name;

	private Integer age;

	private Cat friend ;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Cat getFriend() {
		return friend;
	}

	public void setFriend(Cat friend) {
		this.friend = friend;
	}

	@Override
	public String toString() {
		return "DOG -> [ name = " + name + " ,age = " + age + " ] " ;
	}
}
