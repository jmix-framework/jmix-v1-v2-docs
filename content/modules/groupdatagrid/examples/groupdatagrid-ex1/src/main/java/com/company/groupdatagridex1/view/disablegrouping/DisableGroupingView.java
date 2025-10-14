package com.company.groupdatagridex1.view.disablegrouping;


import com.company.groupdatagridex1.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "disable-grouping-view", layout = MainView.class)
@ViewController(id = "DisableGroupingView")
@ViewDescriptor(path = "disable-grouping-view.xml")
public class DisableGroupingView extends StandardView {
}