package com.github.wnebyte.jcli.sample;

import com.github.wnebyte.jcli.annotation.Command;
import com.github.wnebyte.jcli.annotation.Controller;
import com.github.wnebyte.jcli.annotation.Resource;
import com.github.wnebyte.jcli.annotation.Scope;
import com.github.wnebyte.jcli.io.IConsole;

import java.util.Scanner;

@Controller(Scope.SINGLETON)
public class InputController {

    @Resource
    private IConsole console;

    // Note: works
    @Command
    public void cmd() {
        Scanner scanner = new Scanner(console.in());
        console.out().println("[y/n]");
        String input = scanner.nextLine();
        console.out().println("read: " + input + "\n");
    }
}
