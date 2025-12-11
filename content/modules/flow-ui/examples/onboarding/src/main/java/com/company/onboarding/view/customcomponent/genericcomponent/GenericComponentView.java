package com.company.onboarding.view.customcomponent.genericcomponent;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "generic-component-view", layout = MainView.class)
@ViewController(id = "GenericComponentView")
@ViewDescriptor(path = "generic-component-view.xml")
public class GenericComponentView extends StandardView {
}