package com.product.restful.util;

import org.springframework.stereotype.Component;

import javax.validation.*;
import java.util.Set;

@Component
public class ValidationUtil {

    private final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
    private final Validator validator = validatorFactory.getValidator();

    public void validate(Object object) {
        Set<ConstraintViolation<Object>> violations = validator.validate(object);
        if (violations.size() != 0) {
            throw new ConstraintViolationException(violations);
        }
    }
}
