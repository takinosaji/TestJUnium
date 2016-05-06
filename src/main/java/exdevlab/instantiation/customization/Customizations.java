package exdevlab.instantiation.customization;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Customizations {
    Customize[] value();
}
