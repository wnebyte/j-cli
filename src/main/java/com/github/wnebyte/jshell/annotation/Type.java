package com.github.wnebyte.jshell.annotation;

/**
 * This enum represents the Type of an Argument belonging to a Command.
 * <ul>
 *    <li>{@linkplain Type#REQUIRED}</li>
 *    <li>{@linkplain Type#OPTIONAL}</li>
 *    <li>{@linkplain Type#POSITIONAL}</li>
 * </ul>
 * @see Command
 * @see Argument
 */
public enum Type {
    /**
     * A Required Argument has the following properties: <br>
     * <ol>
     *     <li>Has to be present when the Command is specified for the Command to match.</li>
     *     <li>Has no fixed position.</li>
     *     <li>Named.</li>
     * </ol>
     */
    REQUIRED,
    /**
     * An Optional Argument has the following properties: <br>
     * <ol>
     *     <li>Does not have to be present when the Command is specified for the Command to match.</li>
     *     <li>Has no fixed position.</li>
     *     <li>Named.</li>
     *     <li>If the associated TypeConverter is of Type boolean,
     *     the Argument is specified through the inclusion/exclusion of its name.</li>
     *     <li>Has a default value.</li>
     * </ol>
     */
    OPTIONAL,
    /**
     * A Positional Argument has the following properties: <br>
     * <ol>
     *     <li>Has to be present when the Command is specified for the Command to match.</li>
     *     <li>Has a fixed position at the start of the Command.<br>
     *     If your Command has multiple Positional Arguments, they have to appear one after another in
     *     the relative order in which they are declared,
     *     starting at the first Argument position.</li>
     *     <li>Nameless.</li>
     * </ol>
     */
    POSITIONAL
}