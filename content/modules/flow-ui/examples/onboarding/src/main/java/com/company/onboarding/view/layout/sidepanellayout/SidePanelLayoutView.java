package com.company.onboarding.view.layout.sidepanellayout;


import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.sidepanellayout.SidePanelLayout;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;

@Route(value = "side-panel-layout-view", layout = MainView.class)
@ViewController(id = "SidePanelLayoutView")
@ViewDescriptor(path = "side-panel-layout-view.xml")
public class SidePanelLayoutView extends StandardView {


    // tag::toggle-panel[]
    @ViewComponent
    private SidePanelLayout sidePanelLayout;

    @Subscribe(id = "toggleButton", subject = "clickListener")
    public void onToggleButtonClick(final ClickEvent<JmixButton> event) {
        sidePanelLayout.toggleSidePanel();
    }
    // end::toggle-panel[]
}