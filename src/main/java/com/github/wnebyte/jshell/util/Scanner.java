package com.github.wnebyte.jshell.util;

import com.github.wnebyte.jshell.annotation.Command;
import org.reflections.Reflections;
import org.reflections.scanners.MethodAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;

import java.lang.reflect.Method;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class Scanner {

    private final Set<Method> scanned = new HashSet<>();

    public Scanner() {}

    public final void scanObjects(Set<Object> objects) {
        if (objects != null) {
            for (Object object : objects) {
                scanned.addAll(Arrays.stream(object.getClass().getDeclaredMethods())
                        .filter(method -> method.isAnnotationPresent(Command.class))
                        .collect(Collectors.toSet()));
            }
        }
    }

    public final void scanBundle(Bundle bundle) {
        if (bundle != null) {
            scanned.addAll(Arrays.stream(bundle.getOwner().getClass().getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(Command.class) &&
                            bundle.getMethodNames().contains(method.getName()))
                    .collect(Collectors.toSet()));
        }
    }

    public final void scanClasses(Set<Class<?>> classes) {
        if (classes != null) {
            for (Class<?> cl : classes) {
                scanned.addAll(Arrays.stream(cl.getDeclaredMethods())
                        .filter(method -> method.isAnnotationPresent(Command.class))
                        .collect(Collectors.toSet()));
            }
        }
    }

    public final void scanClass(final Class<?> cls) {
        if (cls != null) {
            scanned.addAll(Arrays.stream(cls.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(Command.class))
                    .collect(Collectors.toSet())
            );
        }
    }

    public final void scanURLS(Collection<String> urls) {
        scanned.addAll(new Reflections(new ConfigurationBuilder()
                .setUrls(toURL(urls))
                .setScanners(new MethodAnnotationsScanner()))
                .getMethodsAnnotatedWith(Command.class));
    }

    public final void removeIf(Predicate<Method> predicate) {
        scanned.removeIf(predicate);
    }

    public final Set<Method> getScanned() {
        return scanned;
    }

    private Collection<URL> toURL(Collection<String> collection) {
        List<URL> urls = new ArrayList<>();
        if (collection != null) {
            collection.forEach(string -> urls.addAll(ClasspathHelper.forPackage(string)));
        }
        return urls;
    }
}
