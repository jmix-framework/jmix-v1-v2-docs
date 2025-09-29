package com.company.onboarding.view.layout.card;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.radiobuttongroup.JmixRadioButtonGroup;
import io.jmix.flowui.view.*;

@Route(value = "card-view", layout = MainView.class)
@ViewController(id = "CardView")
@ViewDescriptor(path = "card-view.xml")
public class CardView extends StandardView {
    @ViewComponent
    private JmixRadioButtonGroup<String> colorRBtn;

    @Subscribe
    public void onInit(final InitEvent event) {
        colorRBtn.setItems("Red", "Black", "White");
    }
}