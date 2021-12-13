package com.github.wnebyte.jcli.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate your Class with this annotation if it declares any {@link Command}
 * annotated Java Methods, to give each declared Command a prefix, and to control
 * the life-cycle of the Objects on which your (non-static) Commands are executed.
 * <br>
 * Here are some examples of usages:
 * <br><br>
 * <pre>
 *{@literal @}Controller(
 *         name = "foo"
 * )
 * public class Foo {
 *     // code
 * }
 * </pre>
 * <p>You can give each declared Command a prefix by specifying a
 * name on the Controller level.<br>
 * Here the name field is set to "foo".<br>
 * <b>Note</b> that static Commands will also receive the prefix if specified.</p>
 * <br>
 * <pre>
 *{@literal @}Controller(
 *         Scope.SINGLETON
 * )
 * public class Foo {
 *     // code
 * }
 * </pre>
 * <p>You can also specify how instances of your class are constructed and (re)used.
 * By specifying a value of type {@link Scope#SINGLETON} on the Controller level,
 * you're specifying that one object should be used for all subsequent Command invocations.<br>
 * If instead a value of type {@link Scope#TRANSIENT} is specified, a new instance will be constructed
 * prior to each invocation.<br>
 * <b>Note</b> that your class will only be instantiated if it declares at least one non-static Command annotated
 * Java Method.</p>
 * <br>
 * @see Command
 * @see Argument
 * @see Inject
 * @see Scope
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Controller {

    Scope value() default Scope.SINGLETON;

    /**
     * Specify a name for this Controller.
     * @return the name of this Controller.
     */
    String name() default "";
}
