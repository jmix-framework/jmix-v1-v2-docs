package com.company.masqueradeex1.view.fragments;


import com.company.masqueradeex1.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "fragments-view", layout = MainView.class)
@ViewController(id = "FragmentsView")
@ViewDescriptor(path = "fragments-view.xml")
public class FragmentsView extends StandardView {
}