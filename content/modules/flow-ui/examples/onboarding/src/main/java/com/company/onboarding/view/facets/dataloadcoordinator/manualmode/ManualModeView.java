package com.company.onboarding.view.facets.dataloadcoordinator.manualmode;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "manual-mode-view", layout = MainView.class)
@ViewController(id = "ManualModeView")
@ViewDescriptor(path = "manual-mode-view.xml")
public class ManualModeView extends StandardView {
}