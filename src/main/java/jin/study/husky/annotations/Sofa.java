package jin.study.husky.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/09/12
 * \* Time: 19:46
 * \* Description:
 * \
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Sofa {
	String value() default "";
}
