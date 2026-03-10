package com.company.demo.view.dynamicattributesfragment;


import com.company.demo.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "dynamic-attributes-fragment-view", layout = MainView.class)
@ViewController(id = "DynamicAttributesFragmentView")
@ViewDescriptor(path = "dynamic-attributes-fragment-view.xml")
public class DynamicAttributesFragmentView extends StandardView {
}