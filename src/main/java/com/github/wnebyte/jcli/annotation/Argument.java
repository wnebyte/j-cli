package com.github.wnebyte.jcli.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.github.wnebyte.jarguments.converter.TypeConverter;
import com.github.wnebyte.jcli.StubTypeConverter;

/**
 * Annotate the Parameters of your {@link Command} annotated Java Method with this annotation to explicitly set its
 * name, description, {@link Group}, and {@link TypeConverter}.<br>
 * Here are some examples of usage:<br><br>
 * <pre>
 *{@literal @}Command
 * public void foo(
 *        {@literal @}Argument
 *         String bar
 * ) {
 *     // code
 * }
 * </pre>
 * <p>Here the name field is implicitly set to "bar", or "arg0" depending on compiler options.
 * <br>
 * The description field is omitted, and the group field is implicitly set to {@link Group#REQUIRED}.
 * <br>
 * In this instance the annotation could have been omitted, and the same configuration
 * would have been achieved.</p>
 * <br>
 * <pre>
 *{@literal @}Command
 * public void foo(
 *        {@literal @}Argument(
 *                 name = "bar",
 *                 description = "demonstration bar",
 *                 group = Group.OPTIONAL
 *         )
 *         String bar
 * ) {
 *     // code
 * }
 * </pre>
 * <p>Here the name field is explicitly set to "bar".
 * <br>
 * The description field is set to "demonstration bar",
 * and the group field is explicitly set to {@link Group#OPTIONAL}.</p>
 * <br>
 * <pre>
 *{@literal @}Command
 * public void foo(
 *        {@literal @}Argument(
 *                 typeConverter = BarTypeConverter.class
 *         )
 *         Bar bar
 * ) {
 *     // code
 * }
 * </pre>
 * <p>A user-defined TypeConverter can be specified by setting the typeConverter field to a class
 * which implements the {@link TypeConverter} interface.
 * <br>
 * Built in support exists for primitive types, wrapper classes, and arrays where the component type is either a
 * primitive type, or a wrapper class.
 * <br>
 * This field only needs to be specified if the type of the Java Parameter is not one of the
 * aforementioned.
 * </p>
 * @see Command
 * @see Controller
 * @see Group
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface Argument {

    /**
     * <p>Specify a set of names for this Argument, separated with a comma character.</p>
     * Defaults to the name of the Java Parameter.
     * @return the name of this Argument.
     */
    String name() default "";

    /**
     * Specify a description for this Argument.
     * @return the description of this Argument.
     */
    String description() default "";

    /**
     * Specify a default value for this (Optional) Argument.
     * @return the default value of this (Optional) Argument.
     */
    String defaultValue() default "";

    String flagValue() default "";

    /**
     * <p>Specify a Group for this Argument.</p>
     * Defaults to {@link Group#REQUIRED}, unless the parameter is of type <code>boolean</code>,
     * then {@link Group#FLAG} will be set.
     * @return the Group of this Argument.
     */
    Group group() default Group.REQUIRED;

    /**
     * Specify a TypeConverter for this Argument.
     * @return the TypeConverter for this Argument.
     */
    Class<? extends TypeConverter> typeConverter() default StubTypeConverter.class;
}