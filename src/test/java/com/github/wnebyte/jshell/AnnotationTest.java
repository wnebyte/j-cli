package com.github.wnebyte.jshell;

import com.github.wnebyte.jshell.annotation.Argument;
import com.github.wnebyte.jshell.annotation.Command;
import com.github.wnebyte.jshell.exception.config.IllegalAnnotationException;
import org.junit.Test;
import com.github.wnebyte.jshell.util.Bundle;


public class AnnotationTest {


    @Command()
    private void foo(
            @Argument(name = "a")
            int a,
            @Argument(name = "a")
            int b
    ) {

    }
}
