package com.github.wnebyte.jcli.annotation;

public enum Scope {
    /**
     * Transient objects are always different; a new instance is provided to every controller and every service.
     */
    TRANSIENT,
    /**
     * Scoped objects are the same within a request, but different across different requests.
     */
    SCOPED,
    /**
     * Singleton objects are the same for every object and request.
     */
    SINGLETON
}
