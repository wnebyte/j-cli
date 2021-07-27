package annotation;

import convert.TypeConverter;
import convert.ObjectTypeConverter;
import core.Required;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Argument {

    String name() default "";

    String description() default "";

    Class<? extends core.Argument> type() default Required.class;

    Class<? extends TypeConverter> typeConverter() default ObjectTypeConverter.class;
}
