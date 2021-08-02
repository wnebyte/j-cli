package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import core.Required;
import core.Optional;
import core.Positional;
import core.TypeConverter;
import util.ObjectTypeConverter;

/**
 * Annotate the Parameters of your {@link Command} annotated Java Methods with this annotation to explicitly set their
 * name, description, type, and type converter.<br/>
 * Here are some examples of usage:<br><br/>
 * <code>
 *     {@literal @}Command<br/>
 *     public void foo({@literal @}Argument String argument) {<br/>
 *     <BLOCKQUOTE>// code</BLOCKQUOTE>
 *     }<br/>
 * </code>
 * <p>Here the name field is implicitly set to argument, or arg0 depending on compiler options.
 * <br/>
 * The description field is omitted, and the type field is implicitly set to Required.class.
 * <br/>
 * Required Arguments have to be present as an Argument when a Command is specified.
 * <br/>
 * In this instance the Argument annotation could have been omitted, and the same configuration
 * would have been achieved.</p>
 * <br/>
 * <code>
 *     {@literal @}Command<br/>
 *     public void foo({@literal @}Argument(name = "argument", description = "desc",
 *     type = Optional.class) String argument) {<br/>
 *     <BLOCKQUOTE>// code</BLOCKQUOTE>
 *     }<br/>
 * </code>
 * <p>Here the name field is explicitly set to argument.
 * <br/>
 * The description field is set to desc, and the type field is explicitly set to Optional.class.
 * <br/>
 * Optional Arguments may, or may not be present as an Argument when a Command is specified.</p>
 * <br/>
 * <code>
 *     {@literal @}Command<br/>
 *     public void foo({@literal @}Argument(type = Positional.class) String argument) {<br/>
 *     <BLOCKQUOTE>// code</BLOCKQUOTE>
 *     }<br/>
 * </code>
 * <p>Here the name field is implicitly set to argument, or arg0 depending on compiler options, but the
 * name field is always ignored when the type field is set to Positional.class.
 * <br/>
 * Positional Arguments are identified by their position, and have to appear at the beginning of their Command's
 * Arguments, in the order in which they are declared in their respective Java Methods.</p>
 * <br/>
 * <code>
 *     {@literal @}Command<br/>
 *     public void foo({@literal @}Argument(typeConverter = FooTypeConverter.class) Foo foo) {<br/>
 *     <BLOCKQUOTE>// code</BLOCKQUOTE>
 *     }<br/>
 * </code>
 * <p>A user-defined TypeConverter can be specified by setting the typeConverter field to a class
 * which implements the {@link TypeConverter} interface.
 * <br/>
 * Built in support for primitive types, wrapper classes, and arrays where the component type is either a
 * primitive type, or a wrapper class exists.
 * <br/>
 * The typeConverter field only needs to be specified if the type of the Java Parameter is not one of the
 * aforementioned.
 * </p>
 * @see Command
 * @see Controller
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Argument {

    String DEFAULT_NAME = "";

    String DEFAULT_DESCRIPTION = "";

    /**
     * Specify a name for the <code>Argument</code>.
     * <br/>
     * Defaults to the name of the Java Parameter, or arg0+ depending on compiler options.
     * <br/>
     * Normalization will remove the following characters: ...
     * @return the name of the <code>Argument</code>.
     */
    String name() default DEFAULT_NAME;

    /**
     * Specify a description for the <code>Argument</code>.
     * @return the description of the <code>Argument</code>.
     */
    String description() default DEFAULT_DESCRIPTION;

    /**
     * Specify a type for the <code>Argument</code>.
     * <br/>
     * Defaults to Required.class.
     * @return the type of the <code>Argument</code>.
     * @see Required
     * @see Optional
     * @see Positional
     */
    Class<? extends core.Argument> type() default Required.class;

    /**
     * Specify a typeConverter for the <code>Argument</code>.
     * @return the typeConverter of the <code>Argument</code>.
     */
    Class<? extends TypeConverter> typeConverter() default ObjectTypeConverter.class;
}
