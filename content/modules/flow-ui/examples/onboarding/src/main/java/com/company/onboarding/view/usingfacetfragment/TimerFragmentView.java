package com.company.onboarding.view.usingfacetfragment;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "timer-fragment-view", layout = MainView.class)
@ViewController(id = "TimerFragmentView")
@ViewDescriptor(path = "timer-fragment-view.xml")
public class TimerFragmentView extends StandardView {
}