package com.company.onboarding.view.usingfacetfragment;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "url-query-parameters-fragment-view", layout = MainView.class)
@ViewController(id = "UrlQueryParametersFragmentView")
@ViewDescriptor(path = "url-query-parameters-fragment-view.xml")
public class UrlQueryParametersFragmentView extends StandardView {
}