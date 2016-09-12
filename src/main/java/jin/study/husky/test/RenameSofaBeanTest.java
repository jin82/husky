package jin.study.husky.test;

import jin.study.husky.annotations.Sofa;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/09/12
 * \* Time: 20:00
 * \* Description:
 * \
 */
@Sofa("RenameSofa")
public class RenameSofaBeanTest {
	@Override
	public String toString() {
		return "this is renameed sofa!";
	}
}
