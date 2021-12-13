package com.github.wnebyte.jcli.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.github.wnebyte.jarguments.converter.TypeConverter;
import com.github.wnebyte.jcli.StubTypeConverter;

/**
 * Annotate the Parameters of your {@link Command} annotated Java Method with this annotation to explicitly set their
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
 * <p>Here the name field is implicitly set to [ "bar" ], or [ "arg0" ] depending on compiler options.
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
 *                  name = "-b, --b"
 *        )
 *         boolean bar
 * ) {
 *     // code
 * }
 * </pre>
 * <p>Here the name field is explicitly set to [ "-b", "--b" ], and
 * the description field is omitted. The group field is forcefully set to {@link Group#FLAG} due to the
 * fact that the Parameter is of Type <code>boolean</code>.<br>
 * <b>Note</b> that {@link Group#REQUIRED} is the default group for every other Type.</p>
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
 * <p>A user-defined {@link TypeConverter} can be specified by assigning to the typeConverter field a class
 * which implements the interface (<b>Note</b> that said class is required to have a no-args Constructor).
 * <br>
 * Built in support exists for primitive types, wrapper classes, and arrays where the component type is either a
 * primitive type, or a wrapper class.
 * <br>
 * This field only needs to be specified if the type of the Java Parameter is not one of the
 * aforementioned, and limitations exists for types that have one or more parameterized types.
 * </p>
 * @see Command
 * @see Controller
 * @see Group
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface Argument {

    /**
     * <p>Specify a set of names for this Argument, separated by a comma character.</p>
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
     * Specify a default value for this (Optional/Flag) Argument.
     * @return the default value of this Argument.
     */
    String defaultValue() default "";

    /**
     * Specify a flag value for this (Flag) Argument.
     * @return the flag value of this Argument.
     */
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
     * @return the TypeConverter belonging to this Argument.
     */
    Class<? extends TypeConverter> typeConverter() default StubTypeConverter.class;
}