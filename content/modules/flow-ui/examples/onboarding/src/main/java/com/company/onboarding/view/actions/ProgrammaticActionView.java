package com.company.onboarding.view.actions;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.kit.action.BaseAction;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "programmatic-action-view", layout = MainView.class)
@ViewController(id = "ProgrammaticActionView")
@ViewDescriptor(path = "programmatic-action-view.xml")
public class ProgrammaticActionView extends StandardView {

    // tag::action-button[]
    @ViewComponent
    private JmixButton actionButton;
    // end::action-button[]

    @Autowired
    private Notifications notifications;

    //tag::setAction[]
    @Subscribe
    public void onInit(final InitEvent event) {
        actionButton.setAction(
                new BaseAction("action")
                        .withText("Click me!")
                        .withHandler(actionPerformedEvent -> notifications.show("Action performed!")));
    }
    //end::setAction[]

}