package flights;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface AttributeQueryable {
    boolean filter() default false;
    boolean order() default false;
    boolean minMax() default false;
}
