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
        codePattern = Pattern.compile("^[A-Z\\d_]*$"); // only upper case letters, underscore and numbers
        ConstraintValidator.super.initialize(constraintAnnotation);
    }
    /**
     * @param requestDto object to validate
     * @param context context in which the constraint is evaluated
     * @return true, only if name and code is not empty. Medication weight is > 0 and not more when max drone weight capacity limit.
     * Name and Code are checking by regexp as well.
     */
    @Override
    public boolean isValid(MedicationDto requestDto, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(requestDto.getName()) || !StringUtils.hasText(requestDto.getCode())) return false;
        if (requestDto.getWeight() < 0 || requestDto.getWeight() > MAX_MEDICINE_WEIGHT) return false;
        return namePattern.matcher(requestDto.getName()).matches() && codePattern.matcher(requestDto.getCode()).matches();
    }
}
