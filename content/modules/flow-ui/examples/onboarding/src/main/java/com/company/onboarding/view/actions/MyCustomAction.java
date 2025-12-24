package com.company.onboarding.view.actions;

import com.vaadin.flow.component.Component;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.action.ActionType;
import io.jmix.flowui.kit.action.BaseAction;
import org.springframework.beans.factory.annotation.Autowired;

@ActionType("myAction")
public class MyCustomAction extends BaseAction {

    @Autowired
    private Notifications notifications;

    public MyCustomAction(String id) {
        super(id);
        setText("Click me!");
    }

     @Override
     public void actionPerform (Component component) {
         notifications.create("Hello!")
                 .withType(Notifications.Type.SUCCESS)
                 .show();
     }
}
