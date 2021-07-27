package util;

import annotation.Command;
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

    public final void scan(Set<Object> objects) {
        if (objects != null) {
            objects.forEach(object -> scanned.addAll(Arrays.stream(object.getClass().getMethods())
                    .filter(method -> method.isAnnotationPresent(Command.class)).collect(Collectors.toList())));
        }
    }

    public final void scan(Collection<String> urls) {
        scanned.addAll(new Reflections(new ConfigurationBuilder()
                .setUrls(toURL(urls))
                .setScanners(new MethodAnnotationsScanner()))
                .getMethodsAnnotatedWith(Command.class));
    }

    public final void removeIf(Predicate<Method> predicate) {
        scanned.removeIf(predicate);
    }

    private Collection<URL> toURL(Collection<String> collection) {
        List<URL> urls = new ArrayList<>();
        if (collection != null) {
            collection.forEach(string -> urls.addAll(ClasspathHelper.forPackage(string)));
        }
        return urls;
    }

    public final Set<Method> getScanned() {
        return scanned;
    }
}
