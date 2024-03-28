package com.m.interview.interview.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {DroneValidator.class})
public @interface ValidDrone {
    String message() default """
            - serial number (100 characters max);
            - model (Lightweight, Middleweight, Cruiserweight, Heavyweight);
            - weight limit (500gr max);""";
    Class<? extends Payload>[] payload() default {};
    Class<?>[] groups() default {};
}
