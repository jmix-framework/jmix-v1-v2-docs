package com.company.onboarding.view.htmlcomponent.iframe;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "iframe-view", layout = MainView.class)
@ViewController(id = "IframeView")
@ViewDescriptor(path = "iframe-view.xml")
public class IframeView extends StandardView {

}