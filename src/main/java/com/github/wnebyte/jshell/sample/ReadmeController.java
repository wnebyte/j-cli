package com.github.wnebyte.jshell.sample;

import com.github.wnebyte.jshell.annotation.Argument;
import com.github.wnebyte.jshell.annotation.Command;
import com.github.wnebyte.jshell.annotation.Type;

public class ReadmeController {

    @Command
    public void foo(
            @Argument(
                    name = "-a",
                    description = "any char sequence not containing a whitespace or a quotation character, \nor " +
                            "a char sequence starting and ending with \" not containing any additional \""
            )
            String a,
            @Argument(
                    name = "-b",
                    description = "array",
                    type = Type.POSITIONAL
            )
            String[] b
    ) {

    }
}
