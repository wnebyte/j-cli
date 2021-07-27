package sample;

import config.Shell;
import config.ConfigurationBuilder;
import convert.TypeConverter;
import convert.TypeConverterRepository;
import core.Console;

import java.util.Arrays;
import java.util.Map;

public class SampleTest {

    public static void main(String[] args) {
        Shell shell = new Shell(new ConfigurationBuilder()
                .setConsole(new Console())
                .setNullifyHelpCommands()
        );
        shell.run();
  
        }
    }
}
