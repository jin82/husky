package jin.study.husky.model;

import jin.study.husky.annotations.Sofa;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/09/11
 * \* Time: 21:07
 * \* Description:
 * \
 */
@Sofa
public class Cat {

	private String name;

	private Mouse mouse;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Mouse getMouse() {
		return mouse;
	}

	public void setMouse(Mouse mouse) {
		this.mouse = mouse;
	}

	@Override
	public String toString() {
		return "Cat -> [ name " + name +" ]";
	}
}
