package core;

import java.util.function.Consumer;

public interface IConsole {

    String read();

    void println(String out);

    void println(int out);

    void println(double out);

    void println(float out);

    void println(char out);

    void println(long out);

    void println(boolean out);

    void println(char[] out);

    void println(Object out);

    void printerr(String out);

    void setOnTextAppended(Consumer<String> onTextAppended);
}
