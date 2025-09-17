package com.company.onboarding.view.settings;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "settings", layout = MainView.class)
@ViewController(id = "SettingsView")
@ViewDescriptor(path = "settings-view.xml")
public class SettingsView extends StandardView {
}