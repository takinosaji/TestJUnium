package exdevlab.instantiation.customization;

import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Repeatable(Customizations.class)
public @interface Customize {
    Class Customizer();
}
