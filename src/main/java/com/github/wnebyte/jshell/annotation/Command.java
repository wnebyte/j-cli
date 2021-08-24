package com.github.wnebyte.jshell.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.github.wnebyte.jshell.Shell;

/**
 * Annotate your Java Methods with this annotation and they can be initialized, and invoked via an
 * instance of the {@link Shell} class at runtime.<br>
 * Here are some examples of usage:<br><br>
 * <pre>
 *{@literal @}Command
 * public void foo() {
 *     // code
 * }
 * </pre>
 * <p>Here the name field is implicitly set to the name of the method, "foo".</p>
 * <br>
 * <pre>
 *{@literal @}Command(
 *         name = "foo",
 *         description = "demonstration foo"
 * )
 * public void foo() {
 *     // code
 * }
 * </pre>
 * <p>Here the name field is explicitly set to "foo",
 * and the description field is set to "demonstration foo".</p>
 * @see Argument
 * @see Controller
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {

    String DEFAULT_NAME = "";

    String DEFAULT_DESCRIPTION = "";

    /**
     * Specify a name for this Command, defaults to the name of the Java Method.
     * @return the name of the <code>Command</code>.
     */
    String name() default DEFAULT_NAME;

    /**
     * Specify a description for this Command.
     * @return the description of this Command.
     */
    String description() default DEFAULT_DESCRIPTION;
}
