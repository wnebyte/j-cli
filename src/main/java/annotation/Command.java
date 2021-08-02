package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import core.Shell;

/**
 * Annotate your Java Methods with this annotation and they can be accessed via an instance of the
 * {@link Shell} class during runtime.<br/>
 * Here are some examples of usage:<br/><br/>
 * <code>
 *     {@literal @}Command<br/>
 *     public void foo() {<br/>
 *     <BLOCKQUOTE>// code</BLOCKQUOTE>
 *     }<br/>
 * </code>
 * <p>Here the name field is implicitly set to the name of the method, foo.</p>
 * <br>
 * <code>
 *      {@literal @}Command(name = "foo")<br>
 *      public void foo() {<br>
 *      <BLOCKQUOTE>// code</BLOCKQUOTE>
 *      }<br>
 * </code>
 * <p>Here the name field is explicitly set to foo.</p>
 * <br/>
 * <code>
 *      {@literal @}Command(description = "desc")<br>
 *      public void foo() {<br>
 *      <BLOCKQUOTE>// code</BLOCKQUOTE>
 *      }<br>
 * </code>
 * <p>Here the description field is set to desc.</p>
 * @see Argument
 * @see Controller
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Command {

    String DEFAULT_NAME = "";

    String DEFAULT_DESCRIPTION = "";

    /**
     * Specify a name for the <code>Command</code>.
     * <br/>
     * Defaults to the name of the Java Method.
     * <br/>
     * Normalization will remove the following characters from the name: ...
     * @return the name of the <code>Command</code>.
     */
    String name() default DEFAULT_NAME;

    /**
     * Specify a description for the <code>Command</code>.
     * @return the description of the <code>Command</code>.
     */
    String description() default DEFAULT_DESCRIPTION;
}
