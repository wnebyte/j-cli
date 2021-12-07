package com.github.wnebyte.jcli.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import com.github.wnebyte.jcli.annotation.Command;

public class MethodScanner implements IMethodScanner {

    /* ----------- Fields ----------- */

    private static final Class<? extends Annotation> DEFAULT_ANNOTATION = Command.class;

    /**
     * The annotation to scan for.
     */
    private final Class<? extends Annotation> annotation;

    /**
     * The set used to store discovered methods.
     */
    private final Set<Method> methods;

    /* ----------- Constructors ----------- */

    public MethodScanner() {
        this(DEFAULT_ANNOTATION);
    }

    public MethodScanner(Class<? extends Annotation> annotation) {
        this.methods = new HashSet<>();
        this.annotation = annotation != null ? annotation : DEFAULT_ANNOTATION;
    }

    /* ----------- Static Methods ----------- */

    public static Collection<URL> toUrlCollection(Collection<String> c, ClassLoader... classLoaders) {
        List<URL> urls = new ArrayList<>();
        if (c != null) {
            c.forEach(s -> urls.addAll(ClasspathHelper.forPackage(s, classLoaders)));
        }
        return urls;
    }

    public static Collection<URL> toUrlCollection(Collection<String> c) {
        return toUrlCollection(
                c,
                Thread.currentThread().getContextClassLoader(),
                Reflections.class.getClassLoader()
        );
    }

    public static Set<Method> getDeclaredAnnotatedMethodsOfClass(Class<?> cls, Class<? extends Annotation> annotation) {
        return Arrays.stream(cls.getDeclaredMethods())
                .filter(method -> method.isAnnotationPresent(annotation))
                .collect(Collectors.toSet());
    }

    public static Set<Method> getAnnotatedMethodsOfClass(Class<?> cls, Class<? extends Annotation> annotation) {
        return Arrays.stream(cls.getMethods())
                .filter(method -> method.isAnnotationPresent(annotation))
                .collect(Collectors.toSet());
    }

    /* ----------- Implementations ----------- */

    @Override
    public void scanObject(Object object) {
        if (object != null) {
            methods.addAll(getDeclaredAnnotatedMethodsOfClass(object.getClass(), annotation));
        }
    }

    @Override
    public void scanObjects(Set<Object> objects) {
        if (objects != null) {
            objects.forEach(this::scanObject);
        }
    }

    @Override
    public void scanMethod(Method method) {
        if (method != null) {
            if (method.isAnnotationPresent(annotation)) {
                methods.add(method);
            }
        }
    }

    @Override
    public void scanMethods(Set<Method> methods) {
        if (methods != null) {
            methods.forEach(this::scanMethod);
        }
    }

    @Override
    public void scanClass(Class<?> cls) {
        if (cls != null) {
            methods.addAll(getDeclaredAnnotatedMethodsOfClass(cls, annotation));
        }
    }

    @Override
    public void scanClasses(Set<Class<?>> classes) {
        if (classes != null) {
            classes.forEach(this::scanClass);
        }
    }

    @Override
    public void scanUrl(String url) {
       if (url != null) {
           scanUrls(Collections.singleton(url));
       }
    }

    @Override
    public void scanUrls(Set<String> urls) {
        if (urls != null) {
            methods.addAll(
                    new Reflections(new ConfigurationBuilder()
                            .setUrls(toUrlCollection(urls))
                            .setScanners(new MethodAnnotationsScanner()))
                            .getMethodsAnnotatedWith(annotation)
            );
        }
    }

    @Override
    public void removedScannedElementIf(Predicate<Method> p) {
        methods.removeIf(p);
    }

    @Override
    public Set<Method> getScannedElements() {
        return methods;
    }
}