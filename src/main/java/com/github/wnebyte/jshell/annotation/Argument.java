package com.github.wnebyte.jshell.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import com.github.wnebyte.jshell.TypeConverter;
import com.github.wnebyte.jshell.util.ObjectTypeConverter;

/**
 * Annotate the Parameters of your {@link Command} annotated Java Method with this annotation to explicitly set their
 * name, description, {@link Type}, and {@link TypeConverter}.<br>
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
 * The description field is omitted, and the type field is implicitly set to {@link Type#REQUIRED}.
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
 *                 type = Type.OPTIONAL
 *         )
 *         String bar
 * ) {
 *     // code
 * }
 * </pre>
 * <p>Here the name field is explicitly set to "bar".
 * <br>
 * The description field is set to "demonstration bar",
 * and the type field is explicitly set to {@link Type#OPTIONAL}.</p>
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
 * The field only needs to be specified if the type of the Java Parameter is not one of the
 * aforementioned.
 * </p>
 * @see Command
 * @see Controller
 * @see Type
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface Argument {

    String DEFAULT_NAME = "";

    String DEFAULT_DESCRIPTION = "";

    /**
     * <p>Specify a name for this Argument.</p>
     * Defaults to the name of annotated Java Parameter.
     * @return the name of this Argument.
     */
    String name() default DEFAULT_NAME;

    /**
     * Specify a description for this Argument.
     * @return the description of this Argument.
     */
    String description() default DEFAULT_DESCRIPTION;

    /**
     * <p>Specify a Type for this Argument.</p>
     * Defaults to {@link Type#REQUIRED}.
     * @return the Type of this Argument.
     */
    Type type() default Type.REQUIRED;

    /**
     * Specify a TypeConverter for this Argument.
     * @return the TypeConverter for this Argument.
     */
    Class<? extends TypeConverter> typeConverter() default ObjectTypeConverter.class;
}
