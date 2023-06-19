package java8masterclass.groupexercise;

import java.lang.annotation.Repeatable;

@Repeatable(value= Options.class)
public @interface Option {
    String  name() default "";
}
