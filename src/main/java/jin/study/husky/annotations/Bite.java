package jin.study.husky.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * \*
 * \* User: jin82
 * \* Date: 2016/09/13
 * \* Time: 14:10
 * \* Description:
 * \
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Bite {
	String value() default "";
}
