package com.company.onboarding.view.layout.hbox;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.UiComponents;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "HboxView", layout = MainView.class)
@ViewController("HboxView")
@ViewDescriptor("hbox-view.xml")
public class HboxView extends StandardView {

    // tag::slots[]
    @ViewComponent
    private HorizontalLayout horizonsBox;

    @Autowired
    private UiComponents uiComponents;

    @Subscribe
    public void onInit(final InitEvent event) {
        horizonsBox.addToStart(createTextField("Start component"));
        horizonsBox.addToMiddle(createTextField("Middle component"));
        horizonsBox.addToEnd(createTextField("End component"));
    }

    private TypedTextField<String> createTextField(String label) {
        TypedTextField<String> textField = uiComponents.create(TypedTextField.class);
        textField.setLabel(label);
        return textField;
    }
    // end::slots[]
}