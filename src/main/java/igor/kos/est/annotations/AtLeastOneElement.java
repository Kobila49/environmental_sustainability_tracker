package igor.kos.est.annotations;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AtLeastOneElementValidator.class)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface AtLeastOneElement {
    String message() default "List must contain at least one element";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
