package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate your Class with this annotation if it declares any {@link Command} annotated Java Methods,
 * to give each Command a prefix.
 * <br/>
 * Here are some examples of usages:
 * <br><br/>
 * <code>
 *     {@literal @}Controller<br/>
 *     public class Foo {<br/>
 *     <BLOCKQUOTE>// code</BLOCKQUOTE>
 *     }<br/>
 * </code>
 * <p>Here the name field is implicitly set to foo.</p>
 * <br/>
 * <code>
 *     {@literal @}Controller(name = "foo")<br/>
 *     public class Foo {<br/>
 *     <BLOCKQUOTE>// code</BLOCKQUOTE>
 *     }<br/>
 * </code>
 * <p>Here the name field is explicitly set to foo.</p>
 * @see Command
 * @see Argument
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Controller {

    /**
     * Specify a prefix for each declared <code>Command</code>.
     * @return the prefix.
     */
    String name() default "";
}
