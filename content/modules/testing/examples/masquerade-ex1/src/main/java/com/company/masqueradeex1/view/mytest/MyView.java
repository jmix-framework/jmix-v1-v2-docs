package com.company.masqueradeex1.view.mytest;


import com.company.masqueradeex1.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

// tag::view-controller-id[]
@Route(value = "my-test-view", layout = MainView.class)
@ViewController(id = "MyView")
@ViewDescriptor(path = "my-test-view.xml")
public class MyView extends StandardView {

}
// end::view-controller-id[]