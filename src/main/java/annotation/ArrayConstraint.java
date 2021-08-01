package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface ArrayConstraint {

    int minLength() default 0;

    int maxLength() default Integer.MAX_VALUE;

    String errorMessage() default "";
}
