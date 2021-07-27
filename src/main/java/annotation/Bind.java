package annotation;

public @interface Bind {

    String[] include() default "";

    String[] exclude() default "";
}
