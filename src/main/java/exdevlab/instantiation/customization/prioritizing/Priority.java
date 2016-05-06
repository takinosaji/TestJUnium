package exdevlab.instantiation.customization.prioritizing;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Priority {
    int Value();
}
