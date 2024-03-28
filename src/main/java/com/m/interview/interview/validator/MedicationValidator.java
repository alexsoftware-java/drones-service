package com.m.interview.interview.validator;

import com.m.interview.interview.dto.MedicationDto;
import com.m.interview.interview.utils.Constants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

public class MedicationValidator implements ConstraintValidator<ValidMedication, MedicationDto> {
    private Pattern namePattern;
    private Pattern codePattern;

    @Override
    public void initialize(ValidMedication constraintAnnotation) {
        namePattern = Pattern.compile("^[\\w-]*$"); // only letters, numbers, ‘-‘, ‘_’
        codePattern = Pattern.compile("^[A-Z\\d_]*$"); // only upper case letters, underscore and numbers
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    /**
     * @param requestDto object to validate
     * @param context context in which the constraint is evaluated
     * @return true, only if name and code are not empty. Medication weight is > 0 and not more than max drone weight capacity limit.
     * Name and code are checked by regexp as well.
     */
    @Override
    public boolean isValid(MedicationDto requestDto, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(requestDto.getName()) || !StringUtils.hasText(requestDto.getCode())) return false;
        if (requestDto.getWeight() < 0 || requestDto.getWeight() > Constants.MAX_MEDICINE_WEIGHT) return false;
        return namePattern.matcher(requestDto.getName()).matches() && codePattern.matcher(requestDto.getCode()).matches();
    }
}
