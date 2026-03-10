package com.company.onboarding.view.usingfacetfragment;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "data-load-coordinator-fragment-view", layout = MainView.class)
@ViewController(id = "DataLoadCoordinatorFragmentView")
@ViewDescriptor(path = "data-load-coordinator-fragment-view.xml")
public class DataLoadCoordinatorFragmentView extends StandardView {
}