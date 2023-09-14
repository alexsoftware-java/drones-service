package com.musala.interview.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {MedicationValidator.class})
public @interface ValidMedication {
    String message() default """
            Medication request validation failed!
            Please check: name (allowed only letters, numbers, ‘-‘, ‘_’)',
            code (allowed only upper case letters, underscore and numbers), max weight=500, max image size=10MB""";
    Class<? extends Payload>[] payload() default {};
    Class<?>[] groups() default {};
}
