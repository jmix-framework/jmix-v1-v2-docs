package com.company.onboarding.view.component.markdown;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.StandardView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;

@Route(value = "markdown-view", layout = MainView.class)
@ViewController(id = "MarkdownView")
@ViewDescriptor(path = "markdown-view.xml")
public class MarkdownView extends StandardView {
}