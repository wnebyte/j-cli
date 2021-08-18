package com.github.wnebyte.jshell.annotation;

/**
 * This enum represents a type of Command Argument.
 * @see Command
 * @see Argument
 */
public enum Type {
    /**
     * Must be present.
     */
    REQUIRED,
    /**
     * May be omitted.
     */
    OPTIONAL,
    /**
     * Nameless and must appear at the beginning of the input, in the order in which they are declared.
     */
    POSITIONAL
}