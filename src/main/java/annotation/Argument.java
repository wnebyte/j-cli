package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import core.TypeConverter;
import util.ObjectTypeConverter;
import core.Required;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface Argument {

    String name() default "";

    String description() default "";

    Class<? extends core.Argument> type() default Required.class;

    Class<? extends TypeConverter> typeConverter() default ObjectTypeConverter.class;
}
