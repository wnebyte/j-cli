package core;

import java.util.function.Consumer;

/**
 * This interface represents a console and declares operations for printing and reading to and from it.
 */
public interface IConsole {

    /**
     * @return input read from the console.
     */
    String read();

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
     * Registers a Consumer to be called when new input is appended to the console.
     * @param onInputReceived the Consumer to be registered.
     */
    void setOnInputReceived(Consumer<String> onInputReceived);
}
