package com.company.onboarding.view.profile;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "profile", layout = MainView.class)
@ViewController(id = "ProfileView")
@ViewDescriptor(path = "profile-view.xml")
public class ProfileView extends StandardView {
}