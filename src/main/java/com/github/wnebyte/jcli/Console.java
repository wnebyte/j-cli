package com.github.wnebyte.jcli;

import java.util.Scanner;
import static java.lang.System.in;

public class Console implements IConsole {

    private final Scanner scanner = new Scanner(in);

    @Override
    public String read() {
        if (scanner.hasNextLine()) {
            return scanner.nextLine();
        }
        return null;
    }

    /**
     * {@inheritDoc}
     * @param out the output to be appended to the console.
     */
    @Override
    public void print(String out) {
        System.out.print(out);
    }

    /**
     * {@inheritDoc}
     * @param out the output to be appended to the console.
     */
    @Override
    public void print(int out) {
        System.out.print(out);
    }

    /**
     * {@inheritDoc}
     * @param out the output to be appended to the console.
     */
    @Override
    public void print(double out) {
        System.out.print(out);
    }

    /**
     * {@inheritDoc}
     * @param out the output to be appended to the console.
     */
    @Override
    public void print(float out) {
        System.out.print(out);
    }

    /**
     * {@inheritDoc}
     * @param out the output to be appended to the console.
     */
    @Override
    public void print(char out) {
        System.out.print(out);
    }

    /**
     * {@inheritDoc}
     * @param out the output to be appended to the console.
     */
    @Override
    public void print(long out) {
        System.out.print(out);
    }

    /**
     * {@inheritDoc}
     * @param out the output to be appended to the console.
     */
    @Override
    public void print(boolean out) {
        System.out.print(out);
    }

    /**
     * {@inheritDoc}
     * @param out the output to be appended to the console.
     */
    @Override
    public void print(char[] out) {
        System.out.print(out);
    }

    /**
     * {@inheritDoc}
     * @param out the output to be appended to the console.
     */
    @Override
    public void print(Object out) {
        System.out.print(out);
    }

    /**
     * {@inheritDoc}
     * @param out the output to be appended to the console.
     */
    @Override
    public void println(String out) {
        System.out.println(out);
    }

    /**
     * {@inheritDoc}
     * @param out the output to be appended to the console.
     */
    @Override
    public void println(int out) {
        System.out.println(out);
    }

    /**
     * {@inheritDoc}
     * @param out the output to be appended to the console.
     */
    @Override
    public void println(double out) {
        System.out.println(out);
    }

    /**
     * {@inheritDoc}
     * @param out the output to be appended to the console.
     */
    @Override
    public void println(float out) {
        System.out.println(out);
    }

    /**
     * {@inheritDoc}
     * @param out the output to be appended to the console.
     */
    @Override
    public void println(char out) {
        System.out.println(out);
    }

    /**
     * {@inheritDoc}
     * @param out the output to be appended to the console.
     */
    @Override
    public void println(long out) {
        System.out.println(out);
    }

    /**
     * {@inheritDoc}
     * @param out the output to be appended to the console.
     */
    @Override
    public void println(boolean out) {
        System.out.println(out);
    }

    /**
     * {@inheritDoc}
     * @param out the output to be appended to the console.
     */
    @Override
    public void println(char[] out) {
        System.out.println(out);
    }

    /**
     * {@inheritDoc}
     * @param out the output to be appended to the console.
     */
    @Override
    public void println(Object out) {
        System.out.println(out);
    }

    /**
     * {@inheritDoc}
     * @param out output to be appended to the console.
     */
    @Override
    public void printerr(String out) {
        System.err.println(out);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void clear() {
        System.out.println("\n\n\n\n\n\n\n\n\n\n");
    }
}