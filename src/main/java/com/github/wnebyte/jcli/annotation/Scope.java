package com.github.wnebyte.jcli.annotation;

public enum Scope {
    /**
     * Transient objects are always different; a new instance is provided to every controller.
     */
    TRANSIENT,
    /**
     * Singleton objects are the same for every subsequent invocation.
     */
    SINGLETON
}