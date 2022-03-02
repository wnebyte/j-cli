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

This library enables the user to quickly configure a command-line interface around an 
arbitrary Java application.<br> 

## Sample

### Command

Annotate any Java Method with the @Command annotation, and it can each be initialized and invoked via an
instance of <code>CLI</code> at runtime.<br>
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

Annotate the Parameter of any @Command annotated Java Method with the @Argument annotation to explicitly set its
name, description, Group, and TypeConverter.<br>
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
The description field is omitted, and the group field is implicitly set to Group.REQUIRED.
<br>
In this instance the annotation could have been omitted, and the same configuration
would have been achieved.</p>

#### Example 2:

    @Command
    public void foo(
            @Argument(name = "-b, --b")
            boolean bar
    ) {
        // code
    }

<p>Here the name field is explicitly set to [ "-b", "--b" ], and
the description field is omitted. The Group field is forcefully set to Group.FLAG due to the
fact that the Parameter is of type <code>boolean</code>.<br>
<b>Note</b> that Group.REQUIRED is the default group for every other type.</p>

#### Example 3:

    @Command
    public void foo(
            @Argument(typeConverter = BarTypeConverter.class)
            Bar bar
    ) {
        // code
    }  

<p>A user-defined TypeConverter can be specified by assigning to the typeConverter field a class
which implements the interface and has a no-args constructor.
<br>
Built in support exists for primitive types, wrapper classes, and arrays where the component type is either a
primitive type, or a wrapper class.
<br>
This field only needs to be specified if the type of the Java Parameter is not one of the
aforementioned. Limitations exists for types that have one or more parameterized types.
</p>

### Controller
coming soon

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
By setting the -parameters compiler arg, 
the names of any local parameters found in any java files will be saved, 
and therefore will be accessible at runtime.
## Documentation
coming soon

## Licence
tbd
