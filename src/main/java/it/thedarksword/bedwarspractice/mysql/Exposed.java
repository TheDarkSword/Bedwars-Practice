package it.thedarksword.bedwarspractice.mysql;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Exposed {
    String name() default "";
    boolean exposed() default true;
    boolean readOnly() default false;
    boolean hasDefault() default false;
}
