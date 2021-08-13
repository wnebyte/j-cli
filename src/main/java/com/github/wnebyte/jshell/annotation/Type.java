package com.github.wnebyte.jshell.annotation;

/**
 * This enum represents a Command-Argument Type.<br/>
 * <li>REQUIRED: must be present.</li>
 * <li>OPTIONAL: may be present, or may be omitted.</li>
 * <li>POSITIONAL: nameless and must appear at the beginning of the set of Arguments, in the
 * declared order.</li>
 * @see Command
 * @see Argument
 */
public enum Type {
    REQUIRED,
    OPTIONAL,
    POSITIONAL;
}
