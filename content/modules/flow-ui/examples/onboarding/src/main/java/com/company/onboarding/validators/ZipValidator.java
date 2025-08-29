package com.company.onboarding.validators;

import io.jmix.flowui.component.validation.Validator;
import io.jmix.flowui.exception.ValidationException;
import org.springframework.stereotype.Component;

// tag::ZipValidator[]
@Component
public class ZipValidator implements Validator<String> {
    @Override
    public void accept(String value) throws ValidationException {
        if (value != null && value.length() != 5)
            throw new ValidationException("Zip code must consist of 5 characters");
    }
}
// end::ZipValidator[]
