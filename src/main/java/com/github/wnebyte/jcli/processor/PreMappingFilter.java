package com.github.wnebyte.jcli.processor;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import com.github.wnebyte.jcli.exception.ConfigException;
import com.github.wnebyte.jcli.util.Annotations;
import com.github.wnebyte.jcli.exception.IllegalAnnotationException;
import static com.github.wnebyte.jcli.util.Reflections.isNested;
import static com.github.wnebyte.jcli.util.Reflections.isStatic;

public class PreMappingFilter implements Filter<Method> {

    @Override
    public boolean test(Method method) {
        final Class<?> cls = method.getDeclaringClass();

        if (Annotations.isNotAnnotated(method)) {
            throw new IllegalAnnotationException(
                    "Method has to be annotated with @Command."
            );
        }
        if (isNested(cls) && !isStatic(cls) && !isStatic(method)) {
            throw new ConfigException(
                    "Non-static, Command annotated Method: %s is declared inside a nested class that is not declared as static."
            );
        }
        for (Parameter param : method.getParameters()) {
            if (Annotations.isNotAnnotated(param)) {
                throw new IllegalAnnotationException(
                        "Method's Parameters have to be annotated with @Argument."
                );
            }
        }

        return true;
    }
}
