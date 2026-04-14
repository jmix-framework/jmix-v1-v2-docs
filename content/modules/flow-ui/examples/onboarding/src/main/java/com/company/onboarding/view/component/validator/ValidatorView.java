package com.company.onboarding.view.component.validator;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.AbstractField;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.datepicker.TypedDatePicker;
import io.jmix.flowui.component.datetimepicker.TypedDateTimePicker;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.component.timepicker.TypedTimePicker;
import io.jmix.flowui.component.validation.*;
import io.jmix.flowui.exception.ValidationException;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.math.BigInteger;

@Route(value = "ValidatorView", layout = MainView.class)
@ViewController("ValidatorView")
@ViewDescriptor("validator-view.xml")
public class ValidatorView extends StandardView {
    @Autowired
    private ApplicationContext applicationContext;
    @ViewComponent
    private TypedTextField<BigInteger> integerField;
    @ViewComponent
    private TypedTextField<String> zipField;
    @ViewComponent
    private TypedTextField<BigDecimal> numberField;
    @ViewComponent
    private TypedTextField<BigDecimal> decimalField;
    @ViewComponent
    private TypedTextField digitsField;
    @ViewComponent
    private TypedTextField<Double> doubleMaxField;
    @ViewComponent
    private TypedTextField<Double> doubleMinField;
    @ViewComponent
    private TypedTextField<String> emailField;
    @ViewComponent
    private TypedTimePicker futureField;
    @ViewComponent
    private TypedTimePicker timeField;
    @ViewComponent
    private TypedTextField<Integer> maxField;
    @ViewComponent
    private TypedTextField<Integer> minField;
    @ViewComponent
    private TypedTextField<Integer> negativeOrZeroField;
    @ViewComponent
    private TypedTextField<Integer> negativeField;
    @ViewComponent
    private TypedTextField notBlankField;
    @ViewComponent
    private TypedTextField notEmptyField;
    @ViewComponent
    private TypedTextField notNullField;
    @ViewComponent
    private TypedDatePicker pastOrPresentField;
    @ViewComponent
    private TypedDatePicker pastField;
    @ViewComponent
    private TypedTextField<Integer> positiveOrZeroField;
    @ViewComponent
    private TypedTextField<Integer> positiveField;
    @ViewComponent
    private TypedTextField regexpField;
    @ViewComponent
    private TypedTextField sizeField;

    @Subscribe
    public void onInit(final InitEvent event) {
        // tag::validator[]
        DecimalMaxValidator maxValidator = applicationContext
                .getBean(DecimalMaxValidator.class, new BigDecimal(1000));
        numberField.addValidator(maxValidator);
        // end::validator[]

        addCustomValidator();
        addDecimalMaxValidator();
        addDecimalMinValidator();
        addDigitsValidator();
        addDoubleMaxValidator();
        addDoubleMinValidator();
        addEmailValidator();
        addFutureOrPresentValidator();
        addFutureValidator();
        addMaxValidator();
        addMinValidator();
        addNegativeOrZeroValidator();
        addNegativeValidator();
        addNotBlankValidator();
        addNotEmptyValidator();
        addNotNullValidator();
        addPastOrPresentValidator();
        addPastValidator();
        addPositiveOrZeroValidator();
        addPositiveValidator();
        addRegexpValidator();
        addSizeValidator();
    }

    private void addCustomValidator(){
        // tag::addCustomValidator[]
        zipField.addValidator(value -> {
            if (value != null && value.length() != 5)
                throw new ValidationException("Zip code must consist of 5 characters");
        });
        // end::addCustomValidator[]
    }

    private void addDecimalMaxValidator(){
        // tag::addDecimalMaxValidator[]
        DecimalMaxValidator maxValidator = applicationContext
                .getBean(DecimalMaxValidator.class, new BigDecimal(1000));
        numberField.addValidator(maxValidator);
        // end::addDecimalMaxValidator[]
    }

    private void addDecimalMinValidator(){
        // tag::addDecimalMinValidator[]
        DecimalMinValidator minValidator = applicationContext
                .getBean(DecimalMinValidator.class, new BigDecimal(100));
        decimalField.addValidator(minValidator);
        // end::addDecimalMinValidator[]
    }

    private void addDigitsValidator(){
        // tag::addDigitsValidator[]
        DigitsValidator digitsValidator = applicationContext
                .getBean(DigitsValidator.class, 3, 2);
        digitsField.addValidator(digitsValidator);
        // end::addDigitsValidator[]
    }
    private void addDoubleMaxValidator(){
        // tag::addDoubleMaxValidator[]
        DoubleMaxValidator maxValidator = applicationContext
                .getBean(DoubleMaxValidator.class, 1000.0);
        doubleMaxField.addValidator(maxValidator);
        // end::addDoubleMaxValidator[]
    }

    private void addDoubleMinValidator(){
        // tag::addDoubleMinValidator[]
        DoubleMinValidator minValidator = applicationContext
                .getBean(DoubleMinValidator.class, 100.0);
        doubleMinField.addValidator(minValidator);
        // end::addDoubleMinValidator[]
    }

    private void addEmailValidator(){
        // tag::addEmailValidator[]
        EmailValidator emailValidator = applicationContext
                .getBean(EmailValidator.class);
        emailField.addValidator(emailValidator);
        // end::addEmailValidator[]
    }

    private void addFutureOrPresentValidator(){
        // tag::addFutureOrPresentValidator[]
        FutureOrPresentValidator futureOrPresentValidator = applicationContext
                .getBean(FutureOrPresentValidator.class);
        futureField.addValidator(futureOrPresentValidator);
        // end::addFutureOrPresentValidator[]
    }

    private void addFutureValidator(){
        // tag::addFutureValidator[]
        FutureValidator futureValidator = applicationContext
                .getBean(FutureValidator.class);
        timeField.addValidator(futureValidator);
        // end::addFutureValidator[]
    }

    private void addMaxValidator(){
        // tag::addMaxValidator[]
        MaxValidator maxValidator = applicationContext
                .getBean(MaxValidator.class, 20500);
        maxField.addValidator(maxValidator);
        // end::addMaxValidator[]
    }

    private void addMinValidator(){
        // tag::addMinValidator[]
        MinValidator minValidator = applicationContext
                .getBean(MinValidator.class, 30);
        minField.addValidator(minValidator);
        // end::addMinValidator[]
    }

    private void addNegativeOrZeroValidator(){
        // tag::addNegativeOrZeroValidator[]
        NegativeOrZeroValidator negativeOrZeroValidator = applicationContext
                .getBean(NegativeOrZeroValidator.class);
        negativeOrZeroField.addValidator(negativeOrZeroValidator);
        // end::addNegativeOrZeroValidator[]
    }

    private void addNegativeValidator(){
        // tag::addNegativeValidator[]
        NegativeValidator negativeValidator = applicationContext
                .getBean(NegativeValidator.class);
        negativeField.addValidator(negativeValidator);
        // end::addNegativeValidator[]
    }

    private void addNotBlankValidator(){
        // tag::addNotBlankValidator[]
        NotBlankValidator notBlankValidator = applicationContext
                .getBean(NotBlankValidator.class);
        notBlankField.addValidator(notBlankValidator);
        // end::addNotBlankValidator[]
    }

    private void addNotEmptyValidator(){
        // tag::addNotEmptyValidator[]
        NotEmptyValidator notEmptyValidator = applicationContext
                .getBean(NotEmptyValidator.class);
        notEmptyField.addValidator(notEmptyValidator);
        // end::addNotEmptyValidator[]
    }

    private void addNotNullValidator(){
        // tag::addNotNullValidator[]
        NotNullValidator notNullValidator = applicationContext
                .getBean(NotNullValidator.class);
        notNullField.addValidator(notNullValidator);
        // end::addNotNullValidator[]
    }
    private void addPastOrPresentValidator(){
        // tag::addPastOrPresentValidator[]
        PastOrPresentValidator pastOrPresentValidator = applicationContext
                .getBean(PastOrPresentValidator.class);
        pastOrPresentField.addValidator(pastOrPresentValidator);
        // end::addPastOrPresentValidator[]
    }

    private void addPastValidator(){
        // tag::addPastValidator[]
        PastValidator pastValidator = applicationContext
                .getBean(PastValidator.class);
        pastField.addValidator(pastValidator);
        // end::addPastValidator[]
    }

    private void addPositiveOrZeroValidator(){
        // tag::addPositiveOrZeroValidator[]
        PositiveOrZeroValidator positiveOrZeroValidator = applicationContext
                .getBean(PositiveOrZeroValidator.class);
        positiveOrZeroField.addValidator(positiveOrZeroValidator);
        // end::addPositiveOrZeroValidator[]
    }

    private void addPositiveValidator(){
        // tag::addPositiveValidator[]
        PositiveValidator positiveValidator = applicationContext
                .getBean(PositiveValidator.class);
        positiveField.addValidator(positiveValidator);
        // end::addPositiveValidator[]
    }

    private void addRegexpValidator(){
        // tag::addRegexpValidator[]
        RegexpValidator regexpValidator = applicationContext
                .getBean(RegexpValidator.class,"[a-z]*");
        regexpField.addValidator(regexpValidator);
        // end::addRegexpValidator[]
    }

    private void addSizeValidator(){
        // tag::addSizeValidator[]
        SizeValidator sizeValidator = applicationContext
                .getBean(SizeValidator.class);
        sizeField.addValidator(sizeValidator);
        // end::addSizeValidator[]
    }
}