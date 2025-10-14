package com.company.groupdatagridex1.view.basicgroupgrid;


import com.company.groupdatagridex1.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "basic-group-grid-view", layout = MainView.class)
@ViewController(id = "BasicGroupGridView")
@ViewDescriptor(path = "basic-group-grid-view.xml")
public class BasicGroupGridView extends StandardView {
}