# j-shell
java library

## Table of Contents
- [About](#about)
- [Sample](#sample)
  - [The absolute minimum](#the-absolute-minimum)
  - [Annotations](#annotations)
  - [Shell](#shell)  
- [Build](#build)
- [Documentation](#documentation)
- [Licence](#licence)

## About
This java-library allows for the easy setup of a 
Shell around an arbitrary Java Application, 
it works by mapping annotated Java Methods 
to runtime accessible Command Objects.<br>

## Sample
### The absolute minimum
Just annotate the Java Methods you'd like mapped to Commands 
with the @Command annotation, <br/>
which is the only required annotation.<br/>

    @Command 
    public void foo(
            int a, 
            int b
    ) {
        // code
    }
The resulting Command Object will have the same name as the annotated Java Method, <br/>
and its Argument Objects will have the same names as their respective Java Parameters<br/>
(that is if the proper [compiler options](#build) have been set, otherwise 
their names will default to arg0 and arg1).
    
Then construct and configure an instance of the Shell class.<br>

    Shell shell = new Shell(new Configuration()
            .setConsole(new Console())); // optional, but recommended

Then call run -- which is a blocking method which continuously reads from the console
(if set), <br/>
or call accept -- which accepts a single String to be matched against a known Command.

    shell.run(); 
    // or 
    shell.accept(input);

That's it!

### Annotations
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
any object whose class declare(s) one or more non-static @Command annotated Java Methods, when reflectively instantiated by the Shell, 
by declaring the proper constructor<br/>
(Does not work on instances that have been directly passed to the Shell via setScanObjects(Objects...) 
seeing as they've already been instantiated). 

    @Command(name = "foo", description = "?")
    public void foo(
            @Argument(name = "*", type = Type.POSITIONAL)
            String p,
            @Argument(name = "a", type = Type.REQUIRED)
            String a, 
            @Argument(name = "b", type = TYPE.OPTIONAL)
            String b,
            @Argument(typeConverter = PersonTypeConverter.class) 
            Person person
    ) {}
    
You can explicitly set the name for every Controller, Command, and Argument.
For the latter two you can also specify a description.<br>
You can also specify a user-defined TypeConverter to be used with a ParameterType 
for which there is no built in support (there is built in support for primitives, wrapper classes, 
and arrays where the component type is either a primitive or a wrapper class).

### Shell

    Shell shell = new Shell(new ConfigurationBuilder()
            .setConsole(new Console())
            .setUnknownCommandOutputFormatter(input -> 
                    "out")
            .setParseExceptionOutputFormatter(e -> 
                    "out")
            .setHelpOutputFormatter(command -> 
                    "out"));
                    
If you've configured the Shell with an IConsole implementation, you can set 
various formatter functions -- they allow you to 
specify how certain output should be formatted.

    Shell shell = new Shell(new ConfigurationBuilder()
            .setUnknownCommandHandler(input -> 
                    System.out.println(input))
            .setParseExceptionHandler(e -> 
                    System.out.println(e))
            .setHelpHandler(command ->
                    System.out.println(command)));

If you do not wish to configure the Shell with an IConsole implementation, you can 
set handlers to handle these events for you instead.

    Shell shell = new Shell(new ConfigurationBuilder()
            .nullifyScanPackages()
            .setScanObjects(new MyClass(arg0, arg1), new MyOtherClass(arg2))
            .setScanClasses(FooOne.class, FooTwo.class));
    
By default, the Shell scans all the packages on the class-path for @Command annotated Java Methods.<br/>
You can tell the Shell to not scan any packages by calling nullifyScanPackages. <br/>
You can also directly pass 
instantiated objects to the Shell,
to be scanned for and to be used when invoking any underlying Java Methods, 
or you can specify that the Shell should scan a certain 
set of Classes for annotated Java Methods.

## Build

### Gradle 
    compileJava {
        options.compilerArgs.add("-parameters")
    }
By setting the -parameters compiler arg, 
the names of any local parameters found in any java files will be saved, 
and therefore will be accessible at runtime.
## Documentation
coming soon

## Licence
tbd
