# j-shell
java library

## Table of Contents
- [About](#about)
- [Sample](#sample)
- [Build](#build)
- [Documentation](#documentation)
- [Licence](#licence)

## About
This java-library allows for the easy setup of a 
Shell around an arbitrary Java Application, by mapping annotated Java Methods 
to runtime accessible Command Objects.

## Sample
### Basics
Just annotate the Java Methods you'd like mapped as Commands.<br/>

    @Command 
    public void foo(
            int a, 
            int b
    ) {
        // code
    }
Command is the only required annotation.<br/>
The resulting Command will have the same name as the annotated Java Method, <br/>
and it's Arguments will have the same names as their respective Java Parameters,
if the proper [compiler options](#build) have been specified, otherwise 
they will receive the names arg0 and arg1.
    
Then construct and configure an instance of the Shell class.

    Shell shell = new Shell(new ConfigurationBuilder()
            .setConsole(new Console()) // optional, but recommended.
    );

Then call run (blocking), or accept.

    shell.run(); 
    // or 
    shell.accept(input);

### @Controller and Injection
    @Controller(name = "prefix") 
    public class Contoller {

    private final IConsole console;

    public Controller(IConsole console) {
        this.console = console;
    }
    
    @Command(name = "foo") 
    public void foo(
            @Argument(name = "-a")
            int a, 
            @Argument(name = "-b")
            int b
    ) {
        console.println("-a: " + a + ", -b: " + b);
    }
    
You can give each Command declared in a class a prefix by supplying the @Controller annotation 
on the class level.<br/>
If you configured the Shell with an instance of the IConsole interface 
(or an extended IConsole interface) the same instance can be injected into 
any instances whose class declare(s) one or more non-static Commands.<br/>
(Does not work on instances that were directly passed to the Shell via setScanObjects(...)).
    
    
### @Argument
    @Command
    public void foo(
            @Argument(type = Positional.class)
            String[] a,
            @Argument(type = Required.class)
            String b, 
            @Argument(type = Optional.class)
            String c
    ) {
      // code
    }
- Positional arguments are identified by their position and are nameless, and have to 
appear at the beginning of the Command, in the order in which they are declared. 

## Build

### Gradle 
    compileJava {
        options.compilerArgs.add("-parameters")
    }
## Documentation

## Licence
