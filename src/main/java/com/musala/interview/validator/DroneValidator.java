package com.musala.interview.validator;

import com.musala.interview.dto.DroneDto;
import com.musala.interview.dto.Model;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.util.StringUtils;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.musala.interview.dto.Model;

import static com.musala.interview.utils.Constants.*;

public class DroneValidator implements ConstraintValidator<ValidDrone, DroneDto> {
    @Override
    public void initialize(ValidDrone constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(DroneDto requestDto, ConstraintValidatorContext context) {
        if (!StringUtils.hasText(requestDto.getSn())) return false;
        if (requestDto.getSn().length() > DRONE_MAX_SN_LENGTH || requestDto.getSn().length() < DRONE_MIN_SN_LENGTH) return false;
        return requestDto.getWeightLimit() >= 0 && requestDto.getWeightLimit() <= MAX_DRONE_CAPACITY;
    }
}
