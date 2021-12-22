package com.github.wnebyte.jcli.annotation;

/**
 * This enum represents the Group of an Argument.
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
     * Has the following properties: <br>
     * <ol>
     *     <li>Has to be included when a Command is specified for the Command to match and execute.</li>
     *     <li>Has no fixed position.</li>
     *     <li>Has a name.</li>
     *     <li>Is initialized by including its name together with a value.</li>
     * </ol>
     */
    REQUIRED,
    /**
     * Has the following properties: <br>
     * <ol>
     *     <li>Does not have to be included when a Command is specified for the Command to match and execute.</li>
     *     <li>Has no fixed position.</li>
     *     <li>Has a name.</li>
     *     <li>Has a default value.</li>
     *     <li>Is initialized by including its name together with a value, or by omission.</li>
     * </ol>
     */
    OPTIONAL,
    /**
     * Has the following properties: <br>
     * <ol>
     *     <li>Does not have to be included when a Command is specified for the Command to match and execute.</li>
     *     <li>Has no fixed position.</li>
     *     <li>Has a name.</li>
     *     <li>Has a default value.</li>
     *     <li>Is initialized by including its name, or by omission.</li>
     * </ol>
     */
    FLAG,
    /**
     * Has the following properties: <br>
     * <ol>
     *     <li>Has to be included when a Command is specified for the Command to match and execute.</li>
     *     <li>Has a fixed relative position.<br>
     *     <li>Has no name.</li>
     *     <li>Is initialized by including a value at the Argument's fixed relative position.</li>
     * </ol>
     */
    POSITIONAL
}