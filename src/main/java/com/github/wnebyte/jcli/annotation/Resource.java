package com.github.wnebyte.jcli.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate your <code>Field</code> and/or <code>Constructor</code> with this annotation to enable the injection
 * of pre-registered Objects into any reflectively instantiated instances of the declaring <code>Class</code>.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.CONSTRUCTOR})
public @interface Resource {
}
