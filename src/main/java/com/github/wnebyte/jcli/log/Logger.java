package com.github.wnebyte.jcli.log;

import com.github.wnebyte.jcli.io.IWriter;
import com.github.wnebyte.jcli.io.Writer;

public class Logger implements ILogger {

    private final IWriter info = new Writer();

    private final IWriter warn = info;

    private final IWriter debug = info;

    @Override
    public void log(LogLevel logLevel, String msg) {
        switch (logLevel) {
            case INFO:
                info(msg);
                break;
            case WARN:
                warn(msg);
                break;
            case DEBUG:
                debug(msg);
                break;
        }
    }

    @Override
    public void info(String msg) {
        info.println(String.format("[%s]: %s", LogLevel.INFO, msg));
    }

    @Override
    public void warn(String msg) {
        warn.printerr(String.format("[%s]: %s", LogLevel.WARN, msg));
    }

    @Override
    public void debug(String msg) {
        debug.println(String.format("[%s]: %s", LogLevel.DEBUG, msg));
    }
}