package com.github.wnebyte.jshell.annotation;

/**
 * This enum represents a Command-Argument Type.<br/>
 * <li>REQUIRED: <b>must be present</b> </li>
 * <li>OPTIONAL: <b>may be omitted</b></li>
 * <li>POSITIONAL: <b>nameless and must appear at the beginning of the input,
 * in the order in which they are declared</b></li>
 * @see Command
 * @see Argument
 */
public enum Type {
    REQUIRED,
    OPTIONAL,
    POSITIONAL
}
