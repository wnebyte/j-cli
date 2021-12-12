package com.github.wnebyte.jcli;

/**
 * This interface represents a console and declares operations for printing and reading to and from it.
 */
public interface IConsole {

    /**
     * @return input read from the console.
     */
    String read();

    /**
     * Prints the specified String.
     * @param out the output to be appended to the console.
     */
    void print(String out);

    /**
     * Prints the specified int.
     * @param out the output to be appended to the console.
     */
    void print(int out);

    /**
     * Prints the specified double.
     * @param out the output to be appended to the console.
     */
    void print(double out);

    /**
     * Prints the specified float.
     * @param out the output to be appended to the console.
     */
    void print(float out);

    /**
     * Prints the specified char.
     * @param out the output to be appended to the console.
     */
    void print(char out);

    /**
     * Prints the specified long.
     * @param out the output to be appended to the console.
     */
    void print(long out);

    /**
     * Prints the specified boolean.
     * @param out the output to be appended to the console.
     */
    void print(boolean out);

    /**
     * Prints the specified char[].
     * @param out the output to be appended to the console.
     */
    void print(char[] out);

    /**
     * Prints the specified Object.
     * @param out the output to be appended to the console.
     */
    void print(Object out);

    /**
     * Prints the specified String on a new line.
     * @param out output to be appended to the console.
     */
    void println(String out);

    /**
     * Prints the specified int on a new line.
     * @param out output to be appended to the console.
     */
    void println(int out);

    /**
     * Prints the specified double on a new line.
     * @param out output to be appended to the console.
     */
    void println(double out);

    /**
     * Prints the specified float on a new line.
     * @param out output to be appended to the console.
     */
    void println(float out);

    /**
     * Prints the specified char on a new line.
     * @param out output to be appended to the console.
     */
    void println(char out);

    /**
     * Prints the specified long on a new line.
     * @param out output to be appended to the console.
     */
    void println(long out);

    /**
     * Prints the specified boolean on a new line.
     * @param out output to be appended to the console.
     */
    void println(boolean out);

    /**
     * Prints the specified char[] on a new line.
     * @param out output to be appended to the console.
     */
    void println(char[] out);

    /**
     * Prints the specified Object on a new line.
     * @param out output to be appended to the console.
     */
    void println(Object out);

    /**
     * Prints the specified error String on a new line.
     * @param out output to be appended to the console.
     */
    void printerr(String out);

    /**
     * Clears the console of text.
     */
    void clear();
}
