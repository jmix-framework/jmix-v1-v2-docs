package com.company.onboarding.validators;

import io.jmix.flowui.component.validation.AbstractValidator;
import io.jmix.flowui.exception.ValidationException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

// tag::validator[]
@Component
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
public class PostcodeValidator extends AbstractValidator<String> {

    private static final String POSTCODE_REGEX =
            "^[A-Z]{1,2}[0-9][0-9A-Z]?\\s?[0-9][A-Z]{2}$";

    @Override
    public void accept(String value) throws ValidationException {
        if (value != null && !value.matches(POSTCODE_REGEX)) {
            String errorMessage = getTemplateErrorMessage(message, Map.of("value", value));
            throw new ValidationException(errorMessage);
        }
    }
}
// end::validator[]
