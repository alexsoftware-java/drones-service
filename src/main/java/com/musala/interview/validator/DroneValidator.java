package com.musala.interview.validator;

import com.musala.interview.dto.DroneDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

import static com.musala.interview.utils.Constants.*;

public class DroneValidator implements ConstraintValidator<ValidDrone, DroneDto> {
    @Override
    public void initialize(ValidDrone constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * @param requestDto object to validate
     * @param context context in which the constraint is evaluated
     * @return true, only if drone serial number length match the rule from config. And weight limit is > 0 and not more when max capacity limit.
     */
    @Override
    public boolean isValid(DroneDto requestDto, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(requestDto.getSerialNumber())) return false;
        if (requestDto.getSerialNumber().length() > DRONE_MAX_SN_LENGTH || requestDto.getSerialNumber().length() < DRONE_MIN_SN_LENGTH) return false;
        return requestDto.getWeightLimit() >= 0 && requestDto.getWeightLimit() <= MAX_DRONE_CAPACITY;
    }
}
