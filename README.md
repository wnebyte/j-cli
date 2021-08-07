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
### The absolute minimum
Just annotate the Java Methods you'd like mapped as Commands.<br/>

    @Command 
    public void foo(
            int a, 
            int b
    ) {
        // code
    }
@Command is the only required annotation.<br/>
The resulting Command will have the same name as the annotated Java Method, <br/>
and the Arguments will have the same name as their respective Java Parameter,
if the proper [compiler options](#build) have been specified, otherwise 
they will receive the names arg0 and arg1.
    
Then construct and configure an instance of the Shell class.<br>
It's recommended that you supply an instance of a class which implements the IConsole interface 
to the Shell, so that the Shell can both read directly from and output text to the console. 

    Shell shell = new Shell(new ConfigurationBuilder()
            .setConsole(new Console()));

Then call run, which is a blocking method which continuously reads from the console
(if set), or call accept.

    shell.run(); 
    // or 
    shell.accept(input);

That's it!

### Basics+
    @Controller(name = "prefix") 
    public class Contoller {

    private final IConsole console;

    public Controller(IConsole console) {
        this.console = console;
    }
    
    @Command
    public void foo(
            int a,
            int b
    ) {
        console.println("out");
    }
    
You can give each Command declared in a particular class a prefix by supplying the @Controller annotation 
on the class level.<br/>
If you have configured the Shell with an instance of the IConsole interface 
(or an interface which extends the IConsole interface), the same instance can be injected into 
any object whose class declare(s) one or more non-static Commands, when reflectively instantiated by the Shell.<br/>
(Does not work on instances that have been directly passed to the Shell via setScanObjects(Objects...) 
seeing as they've already been instantiated). 

    @Command(name = "foo", description = "?")
    public void foo(
            @Argument(name = "*", type = Positional.class)
            String p,
            @Argument(name = "a", type = Required.class)
            String a, 
            @Argument(name = "b", type = Optional.class)
            String b,
            @Argument(typeConverter = PersonTypeConverter.class) 
            Person person
    ) {}
    
You can explicitly set the name for every Controller, Command, and Argument.
For the latter two you can also specify a description.<br>
There are three types of arguments [see documentation for details]().<br/>
You can also specify a user-defined TypeConverter to be used for a Type for which there is no built in 
native support.

    Shell shell = new Shell(new ConfigurationBuilder()
            .setConsole(new Console())
            .setUnknownCommandOutputFormatter(input -> 
                    "out")
            .setParseExceptionOutputFormatter(e -> 
                    "out")
            .setHelpOutputFormatter(command -> 
                    "out"));
                    
If you've configured the Shell with an IConsole implementation, you can set 
various formatter functions -- they will allow you to 
specify how certain output should be formatted.

    Shell shell = new Shell(new ConfigurationBuilder()
            .setUnknownCommandHandler(input -> 
                    System.out.println(input))
            .setParseExceptionHandler(e -> 
                    System.out.println(e))
            .setHelpHandler(command ->
                    System.out.println(command)));

If you do not wish to configure the Shell with an IConsole implementation, you can 
set handlers to handle these events for you.

    Shell shell = new Shell(new ConfigurationBuilder()
            .nullifyScanPackages()
            .setScanObjects(new MyClass(arg0, arg1), new MyOtherClass(arg2))
            .setScanClasses(FooOne.class, FooTwo.class));
    
By default, all packages on the class-path are scanned for @Command annotated Methods. 
By setting nullifyScanPackages - no packages are scanned. You can also directly pass 
instantiated objects to the Shell - 
to be scanned for and to be used to invoke any underlying annotated Methods, or specify that the Shell should scan a certain 
set of Classes.

## Build

### Gradle 
    compileJava {
        options.compilerArgs.add("-parameters")
    }
## Documentation

## Licence
