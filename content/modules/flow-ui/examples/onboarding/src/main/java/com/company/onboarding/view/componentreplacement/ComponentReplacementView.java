package com.company.onboarding.view.componentreplacement;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Notifications;
import io.jmix.flowui.component.datepicker.TypedDatePicker;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;

@Route(value = "component-replacement-view", layout = MainView.class)
@ViewController(id = "ComponentReplacementView")
@ViewDescriptor(path = "component-replacement-view.xml")
public class ComponentReplacementView extends StandardView {

    @Autowired
    private Notifications notifications;
    @ViewComponent
    private TypedDatePicker<LocalDate> datePicker;

    @Subscribe(id = "button", subject = "clickListener")
    public void onButtonClick(final ClickEvent<JmixButton> event) {
        notifications.show(String.valueOf(datePicker.getTypedValue()));
    }
}