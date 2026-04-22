package com.company.onboarding.view.component.textfield;

import com.company.onboarding.util.DateUtils;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.SupportsStatusChangeHandler;
import io.jmix.flowui.component.SupportsTypedValue;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.exception.ValidationException;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@Route(value = "TextFieldView", layout = MainView.class)
@ViewController("TextFieldView")
@ViewDescriptor("text-field-view.xml")
public class TextFieldView extends StandardView {
    // tag::autowired-notifications[]
    @Autowired
    protected Notifications notifications;

    // end::autowired-notifications[]
    // tag::infoLabel[]
    @ViewComponent
    private Div infoLabel;

    // end::infoLabel[]
    // tag::typedValueChangeEvent[]
    @Subscribe("nameField")
    protected void onNameFieldTypedValueChange(
            SupportsTypedValue.TypedValueChangeEvent<TypedTextField<String>, String> event) {
        notifications
                .show("Before: " + event.getOldValue() +
                        ". After: " + event.getValue());
    }
    // end::typedValueChangeEvent[]
    // tag::validator[]
    @Install(to = "zipField", subject = "validator")
    protected void zipFieldValidator(Integer value) {
        if (value != null && String.valueOf(value).length() != 6)
            throw new ValidationException("Zip must be of 6 digits length");
    }
    // end::validator[]
    // tag::statusChangeHandler[]
    @Install(to = "negativeField", subject = "statusChangeHandler")
    private void negativeFieldStatusChangeHandler(
            SupportsStatusChangeHandler.StatusContext<TypedTextField<String>>
                                                              statusContext) {
        infoLabel.setVisible(true); // <1>
        infoLabel.setText(statusContext.getDescription()); // <2>
    }
    // end::statusChangeHandler[]

    // tag::use-utils[]
    @ViewComponent
    private TypedTextField<String> textField;

    @Subscribe
    public void onInit(final InitEvent event) {
        String formattedDate = DateUtils.formatDate(LocalDate.now());
        textField.setValue(formattedDate);
    }
// end::use-utils[]
}