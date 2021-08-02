package core;

import java.util.Scanner;
import java.util.function.Consumer;

import static java.lang.System.in;

public class ExtendedConsole implements IExtendedConsole {

    private static final Scanner SC = new Scanner(in);

    @Override
    public String read() {
        if (SC.hasNextLine()) {
            return SC.nextLine();
        }
        return null;
    }

    @Override
    public void println(String out) {
        System.out.println(out);
    }

    @Override
    public void println(int out) {
        System.out.println(out);
    }

    @Override
    public void println(double out) {
        System.out.println(out);
    }

    @Override
    public void println(float out) {
        System.out.println(out);
    }

    @Override
    public void println(char out) {
        System.out.println(out);
    }

    @Override
    public void println(long out) {
        System.out.println(out);
    }

    @Override
    public void println(boolean out) {
        System.out.println(out);
    }

    @Override
    public void println(char[] out) {
        System.out.println(out);
    }

    @Override
    public void println(Object out) {
        System.out.println(out);
    }

    @Override
    public void printerr(String out) {
        System.err.println(out);
    }

    @Override
    public void setOnInputReceived(Consumer<String> onInputReceived) {

    }
}
