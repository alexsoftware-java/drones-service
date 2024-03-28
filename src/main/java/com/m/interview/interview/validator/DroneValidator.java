package com.m.interview.interview.validator;

import com.m.interview.interview.dto.DroneDto;
import com.m.interview.interview.utils.Constants;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

public class DroneValidator implements ConstraintValidator<ValidDrone, DroneDto> {
    @Override
    public void initialize(ValidDrone constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    /**
     * @param requestDto object to validate
     * @param context context in which the constraint is evaluated
     * @return true, only if: drone serial number length matches the rule from config, weight limit is > 0 and not more than max capacity limit.
     */
    @Override
    public boolean isValid(DroneDto requestDto, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(requestDto.getSerialNumber())) return false;
        if (requestDto.getSerialNumber().length() > Constants.DRONE_MAX_SN_LENGTH || requestDto.getSerialNumber().length() < Constants.DRONE_MIN_SN_LENGTH) return false;
        return requestDto.getWeightLimit() >= 0 && requestDto.getWeightLimit() <= Constants.MAX_DRONE_CAPACITY;
    }
}
