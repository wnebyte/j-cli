package com.github.wnebyte.jshell;

import com.github.wnebyte.jshell.util.ArgumentSplitter;
import org.junit.Test;

public class ArgumentSplitterTest {

    @Test
    public void test00() {
        String content = new ArgumentSplitter()
                .setName("name")
                .setValue("name hello")
                .split()
                .get();
        System.out.println(content);
    }
}
