package com.github.wnebyte.jcli.log;

public interface ILogger {

    void log(LogLevel logLevel, String msg);

    void info(String msg);

    void warn(String msg);

    void debug(String msg);
}
