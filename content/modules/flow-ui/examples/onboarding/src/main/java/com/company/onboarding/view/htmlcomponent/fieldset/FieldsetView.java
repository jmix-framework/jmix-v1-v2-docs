package com.company.onboarding.view.htmlcomponent.fieldset;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "fieldset-view", layout = MainView.class)
@ViewController(id = "FieldsetView")
@ViewDescriptor(path = "fieldset-view.xml")
public class FieldsetView extends StandardView {
}