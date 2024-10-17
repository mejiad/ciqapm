package com.evoltech.ciqapm.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Constraint(validatedBy = EtapaValidator.class)
@Documented
public @interface CheckEtapaDates {
    String message() default "La fecha de termino debe ser mayor a la de inicio";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
