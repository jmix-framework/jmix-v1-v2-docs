package com.company.onboarding.view.component.switchcomponent;

import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "switch-view", layout = MainView.class)
@ViewController(id = "SwitchView")
@ViewDescriptor(path = "switch-view.xml")
public class SwitchView extends StandardView {
}