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

/**
 * This class declares methods for scanning for {@linkplain Command} annotated Java Methods.
 */
public final class Scanner {

    private final Set<Method> scanned = new HashSet<>();

    /**
     * Constructs a new instance.
     */
    public Scanner() {}

    /**
     * Scans the specified Set for {@link Command} annotated Java Methods.
     * @param objects the Objects whose Classes are to be scanned.
     */
    public final void scanObjects(final Set<Object> objects) {
        if (objects != null) {
            for (Object object : objects) {
                scanned.addAll(Arrays.stream(object.getClass().getDeclaredMethods())
                        .filter(method -> method.isAnnotationPresent(Command.class))
                        .collect(Collectors.toSet()));
            }
        }
    }

    /**
     * Scans the specified Bundle for {@link Command} annotated Java Methods.
     * @param bundle the Bundle to be scanned.
     */
    public final void scanBundle(final Bundle bundle) {
        if (bundle != null) {
            scanned.addAll(Arrays.stream(bundle.getOwner().getClass().getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(Command.class) &&
                            bundle.getMethodNames().contains(method.getName()))
                    .collect(Collectors.toSet()));
        }
    }

    /**
     * Scans the specified Classes for {@link Command} annotated Java Methods.
     * @param classes the Classes to be scanned.
     */
    public final void scanClasses(final Set<Class<?>> classes) {
        if (classes != null) {
            for (Class<?> cl : classes) {
                scanned.addAll(Arrays.stream(cl.getDeclaredMethods())
                        .filter(method -> method.isAnnotationPresent(Command.class))
                        .collect(Collectors.toSet()));
            }
        }
    }

    /**
     * Scans the specified Class for {@link Command} annotated Java Methods.
     * @param cls the Class to be scanned.
     */
    public final void scanClass(final Class<?> cls) {
        if (cls != null) {
            scanned.addAll(Arrays.stream(cls.getDeclaredMethods())
                    .filter(method -> method.isAnnotationPresent(Command.class))
                    .collect(Collectors.toSet())
            );
        }
    }

    /**
     * Scans the specified URLs for {@link Command} annotated Java Methods.
     * @param urls the URLs to be scanned.
     */
    public final void scanURLS(final Collection<String> urls) {
        scanned.addAll(new Reflections(new ConfigurationBuilder()
                .setUrls(toURL(urls))
                .setScanners(new MethodAnnotationsScanner()))
                .getMethodsAnnotatedWith(Command.class));
    }

    /**
     * Removes any scanned Methods for which the specified predicate matches.
     * @param predicate to test each scanned Method on.
     */
    public final void removeIf(final Predicate<Method> predicate) {
        scanned.removeIf(predicate);
    }

    /**
     * @return the scanned Methods.
     */
    public final Set<Method> getScanned() {
        return scanned;
    }

    /**
     * Transforms the specified String Collection into a URL Collection.
     * @param collection to be transformed.
     * @return a URL Collection.
     */
    private Collection<URL> toURL(final Collection<String> collection) {
        List<URL> urls = new ArrayList<>();
        if (collection != null) {
            collection.forEach(string -> urls.addAll(ClasspathHelper.forPackage(string)));
        }
        return urls;
    }
}