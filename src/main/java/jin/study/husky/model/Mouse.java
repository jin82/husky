package jin.study.husky.model;

import jin.study.husky.annotations.Sofa;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/09/11
 * \* Time: 22:42
 * \* Description:
 * \
 */
@Sofa
public class Mouse {

	private String mouseName;

	public String getMouseName() {
		return mouseName;
	}

	public void setMouseName(String mouseName) {
		this.mouseName = mouseName;
	}
}
