package com.github.wnebyte.jcli.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate your Class with this annotation if it declares any {@link Command}
 * annotated Java Methods, to give each declared Command a prefix.
 * <br>
 * Here are some examples of usages:
 * <br><br>
 * <pre>
 *{@literal @}Controller
 * public class Foo {
 *     // code
 * }
 * </pre>
 * <p>Here the name field is implicitly set to "foo".</p>
 * <br>
 * <pre>
 *{@literal @}Controller(
 *         name = "foo"
 * )
 * public class Foo {
 *     // code
 * }
 * </pre>
 * <p>Here the name field is explicitly set to "foo".</p>
 * @see Command
 * @see Argument
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Controller {

    Scope value() default Scope.TRANSIENT;

    /**
     * Specify a name for this Controller.
     * @return the name of this Controller.
     */
    String name() default "";
}
