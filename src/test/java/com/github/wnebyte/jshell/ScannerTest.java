package com.github.wnebyte.jshell;

import com.github.wnebyte.jshell.annotation.Command;
import org.junit.Assert;
import org.junit.Test;
import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;
import com.github.wnebyte.jshell.util.Bundle;
import com.github.wnebyte.jshell.util.Scanner;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

public class ScannerTest {

    @Test
    public void test00() {
        Scanner scanner = new Scanner();
        scanner.scanClasses(new HashSet<Class<?>>(){{add(ScannerTest.class);}});
        Assert.assertEquals(3, scanner.getScanned().size());
        scanner = new Scanner();
        scanner.scanClasses(new HashSet<>());
        Assert.assertEquals(0, scanner.getScanned().size());
        scanner = new Scanner();
        scanner.scanClasses(null);
        Assert.assertEquals(0, scanner.getScanned().size());
    }

    @Test
    public void test01() {
        Scanner scanner = new Scanner();
        scanner.scanBundle(new Bundle(this, "foo", "foo1"));
        Assert.assertEquals(2, scanner.getScanned().size());
        scanner = new Scanner();
        scanner.scanBundle(new Bundle(this, (String[]) null));
        Assert.assertEquals(0, scanner.getScanned().size());
    }

    @Command()
    public void foo() {}

    @Command()
    public void foo1() {}

    @Command()
    public void foo2() {}

    @Test
    public void test03() {
        Package.getPackages();
    }

    public Set<String> findAllPackages() {
        List<ClassLoader> classLoaderList = new LinkedList<>();
        classLoaderList.add(ClasspathHelper.contextClassLoader());
        classLoaderList.add(ClasspathHelper.staticClassLoader());
        classLoaderList.add(ClassLoader.getSystemClassLoader());
        Reflections reflections = new Reflections(new ConfigurationBuilder()
                .setScanners(new SubTypesScanner(false), new ResourcesScanner())
                .setUrls(ClasspathHelper.forClassLoader(classLoaderList.toArray(new ClassLoader[0])))
                .filterInputsBy(new FilterBuilder().include(FilterBuilder.prefix(".*")))
        );
        Set<Class<?>> classes = reflections.getSubTypesOf(Object.class);
        Set<String> packageNameSet = new TreeSet<String>();
        for (Class<?> classInstance : classes) {
            packageNameSet.add(classInstance.getPackage().getName());
        }
        return packageNameSet;
    }
}
