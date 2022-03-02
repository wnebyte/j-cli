# j-cli

java-library

## Table of Contents

- [About](#about)
- [Sample](#sample)
  - [Command](#command)
  - [Argument](#argument)
  - [Controller](#controller)
  - [Group](#group)
  - [CLI](#cli)
  - [Configuration]()
- [Build](#build)
- [Documentation](#documentation)
- [Licence](#licence)

## About

This library enables a user to quickly configure a command-line interface around an 
arbitrary Java application.<br> 

## Sample

### Command

Annotate any Java Method with the @Command annotation, and it will be mapped to a <code>Command</code>.<br>
Each <code>Command</code> has an optional prefix, one or more distinct names, an optional description, 
and an optional enumeration of Arguments.<br>
Here are some examples of usage:<br>

#### Example 1:
  
    @Command
    public void foo() {
        // code
    }
  
<p>Here the name field is implicitly set to the name of the method, [ "foo" ].
The description is omitted, as is the enumeration of Arguments.</p>

#### Example 2:
    
    @Command(name = "foo, -foo", description = "bar")
    public void foo() {
        // code
    }
  
<p>Here the name field is explicitly set to [ "foo", "-foo" ].
The description field is set to "bar", and the enumeration of Arguments is omitted.</p>

### Argument

Annotate the Parameters of any @Command annotated Java Methods with the @Argument annotation to explicitly set the
name, description, group, and typeConverter properties of the <code>Argument</code>.<br>
Here are some examples of usage:<br>

#### Example 1:

    @Command
    public void foo(
            @Argument
            String bar
    ) {
        // code
    }

<p>Here the name field is implicitly set to [ "bar" ], or [ "arg0" ] depending on compiler options.
<br>
The description field is omitted, and the group field is implicitly set to <code>Group.REQUIRED</code>.
<br>
In this example the annotation could have been omitted, and the same configuration
would have been achieved.</p>

#### Example 2:

    @Command
    public void foo(
            @Argument(name = "-b, --b", description = "bar")
            boolean bar
    ) {
        // code
    }

<p>Here the name field is explicitly set to [ "-b", "--b" ], and
the description field is set to "bar". The Group field is forcefully set to <code>Group.FLAG</code> due to the
fact that the Parameter is of type <code>boolean</code>.<br>
<b>Note</b> that <code>Group.REQUIRED</code> is the default group for every other type.</p>

#### Example 3:

    @Command
    public void foo(
            @Argument(typeConverter = BarTypeConverter.class)
            Bar bar
    ) {
        // code
    }  

<p>A user-defined <code>TypeConverter</code> can be specified by assigning to the typeConverter field a class
which implements the <code>TypeConverter</code> interface and has a no-args constructor.
<br>
Built in support exists for primitive types, wrapper classes, and arrays where the component type is either a
primitive type, or a wrapper class.
<br>
This field only needs to be specified if the type of the Java Parameter is not one of the
aforementioned. Limitations exists for types that have one or more parameterized types.</p>

### Controller

Annotate your <code>Class</code> with the @Controller annotation if it declares any @Command
annotated Java Methods, to give each declared <code>Command</code> an optional prefix, and to control
the life-cycle of the Objects on which your (non-static) Commands are executed.
<br>
Here are some examples of usages:
<br>

#### Example 1:

    @Controller(name = ""foo)
    public class Foo {
        // code
    }

<p>You can give each declared <code>Command</code> a prefix by specifying a
name on the Controller level.<br>
Here the name field is set to "foo".<br>
<b>Note</b> that static Commands will also receive the assigned prefix.</p>
  
#### Example 2:
  
    @Controller(Scope.SINGLETON)
    public class Foo {
        // code
    }  

<p>You can also specify how instances of your <code>Class</code> are to be constructed and (re)used.<br>
By specifying a value of type <code>Scope.SINGLETON</code> on the Controller level,
you're specifying that one instance of your <code>Class</code> should be used for all subsequent <code>Command</code> invocations.<br>
If instead a value of type <code>Scope.TRANSIENT</code> is specified, a new instance will be constructed
prior to each invocation.<br>
<b>Note</b> that your class will only be instantiated if it declares at least one non-static @Command annotated
Java Method.</p>

### Group

coming soon

### CLI

coming soon

### Configuration

coming soon

## Build

### Gradle 

    compileJava {
        options.compilerArgs.add("-parameters")
    }
    
## Documentation
coming soon

## Licence
tbd
