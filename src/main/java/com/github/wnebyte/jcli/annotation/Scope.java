package com.github.wnebyte.jcli.annotation;

/**
 * This enum represents the Scope of a Controller.
 * <ul>
 *     <li>{@linkplain Scope#TRANSIENT}</li>
 *     <li>{@linkplain Scope#SINGLETON}</li>
 * </ul>
 * @see Controller
 */
public enum Scope {
    /**
     * Transient objects are always different; a new instance is provided before every invocation.
     */
    TRANSIENT,
    /**
     * Singleton objects are the same for every invocation.
     */
    SINGLETON
}