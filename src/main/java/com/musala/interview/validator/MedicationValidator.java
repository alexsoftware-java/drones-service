package com.musala.interview.validator;

import com.musala.interview.dto.MedicationDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

import static com.musala.interview.utils.Constants.MAX_MEDICINE_WEIGHT;

public class MedicationValidator implements ConstraintValidator<ValidMedication, MedicationDto> {
    private Pattern namePattern;
    private Pattern codePattern;

    @Override
    public void initialize(ValidMedication constraintAnnotation) {
        namePattern = Pattern.compile("^[\\w-]*$"); // only letters, numbers, ‘-‘, ‘_’
        codePattern = Pattern.compile("^[A-Z0-9_]*$"); // only upper case letters, underscore and numbers
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(MedicationDto requestDto, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(requestDto.getName()) || !StringUtils.hasText(requestDto.getCode())) return false;
        if (requestDto.getWeight() < 0 || requestDto.getWeight() > MAX_MEDICINE_WEIGHT) return false;
        return namePattern.matcher(requestDto.getName()).matches() && codePattern.matcher(requestDto.getCode()).matches();
    }
}
