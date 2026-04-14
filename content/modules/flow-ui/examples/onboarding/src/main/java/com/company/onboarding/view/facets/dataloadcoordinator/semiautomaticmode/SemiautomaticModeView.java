package com.company.onboarding.view.facets.dataloadcoordinator.semiautomaticmode;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "semiautomatic-mode-view", layout = MainView.class)
@ViewController(id = "SemiautomaticModeView")
@ViewDescriptor(path = "semiautomatic-mode-view.xml")
public class SemiautomaticModeView extends StandardView {
}