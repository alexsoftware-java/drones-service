package com.m.interview.interview.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class EnumValidator implements ConstraintValidator<ValidEnum, Enum<?>> {
    private Pattern pattern;
    @Override
    public void initialize(ValidEnum annotation) {
        try {
            pattern = Pattern.compile(annotation.allowedValues());
        } catch (PatternSyntaxException e) {
            throw new IllegalArgumentException("Given regex is invalid", e);
        }
    }
    /**
     * @param value object to validate, subclass of Enum
     * @param context context in which the constraint is evaluated
     * @return true, only if given Enum value matches the regexp from annotation parameter
     */
    @Override
    public boolean isValid(Enum<?> value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        Matcher m = pattern.matcher(value.name());
        return m.matches();
    }
}