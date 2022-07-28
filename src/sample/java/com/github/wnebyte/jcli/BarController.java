package com.github.wnebyte.jcli;

import com.github.wnebyte.jcli.annotation.Argument;
import com.github.wnebyte.jcli.annotation.Command;
import com.github.wnebyte.jcli.annotation.Controller;

@Controller("bar")
public class BarController {

    @Command(description = "throws a RuntimeException")
    public void dab(
            @Argument(required = true, choices = {"hello", "test"})
            String s
    ) {
        throw new RuntimeException(
                "You didn't say the magic word!"
        );
    }

    @Command(description = "throws a checked Exception")
    public void eab() throws Exception {
        throw new Exception(
                "You didn't say the magic word!"
        );
    }

    @Command(description = "sleeps main-thread for 5s")
    public void aab() {
        try {
            System.out.println("sleep now...");
            Thread.sleep(5000);
            System.out.println("sleep end...");
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Command
    public void cab() {

    }

    @Command
    public void bab() {

    }
}
