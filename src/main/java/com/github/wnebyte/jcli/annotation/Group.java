package com.github.wnebyte.jcli.annotation;

/**
 * This enum represents the Group of an Argument belonging to a Command.
 * <ul>
 *    <li>{@linkplain Group#REQUIRED}</li>
 *    <li>{@linkplain Group#OPTIONAL}</li>
 *    <li>{@linkplain Group#FLAG}</li>
 *    <li>{@linkplain Group#POSITIONAL}</li>
 * </ul>
 * @see Command
 * @see Argument
 */
public enum Group {
    /**
     * A Required Argument has the following properties: <br>
     * <ol>
     *     <li>Has to be present when a Command is specified for the Command to match.</li>
     *     <li>Has no fixed position.</li>
     *     <li>Has a name.</li>
     *     <li>Is initializable by the inclusion of its name together with a value.</li>
     * </ol>
     */
    REQUIRED,
    /**
     * An Optional Argument has the following properties: <br>
     * <ol>
     *     <li>Does not have to be present when a Command is specified for the Command to match.</li>
     *     <li>Has no fixed position.</li>
     *     <li>Has a name.</li>
     *     <li>Has a default value.</li>
     *     <li>Is initializable by the inclusion or omission of its name together with a value.</li>
     * </ol>
     */
    OPTIONAL,
    /**
     * A Flag Argument has the following properties: <br>
     * <ol>
     *     <li>Does not have to be present when a Command is specified for the Command to match.</li>
     *     <li>Has no fixed position.</li>
     *     <li>Has a name.</li>
     *     <li>Has a default value.</li>
     *     <li>Is initializable by the inclusion or omission of its name.</li>
     * </ol>
     */
    FLAG,
    /**
     * A Positional Argument has the following properties: <br>
     * <ol>
     *     <li>Has to be present when a Command is specified for the Command to match.</li>
     *     <li>Has a fixed position at the start of the Command.<br>
     *     If your Command has multiple Positional Arguments, they have to appear one after another in
     *     the relative order in which they are declared,
     *     starting at the first Argument position.</li>
     *     <li>Has no name.</li>
     *     <li>Is initializable by the inclusion of a value at the Argument's fixed position.</li>
     * </ol>
     */
    POSITIONAL
}