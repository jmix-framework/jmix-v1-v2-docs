package com.company.onboarding.view.facets.dataloadcoordinator.automaticmode;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "automatic-mode-view", layout = MainView.class)
@ViewController(id = "AutomaticModeView")
@ViewDescriptor(path = "automatic-mode-view.xml")
public class AutomaticModeView extends StandardView {
}