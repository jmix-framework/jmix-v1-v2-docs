package com.company.onboarding.view.step;

import com.company.onboarding.entity.Step;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.KeyModifier;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.kit.component.KeyCombination;
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.view.*;

@Route(value = "steps/:id", layout = MainView.class)
@ViewController("Step.detail")
@ViewDescriptor("step-detail-view.xml")
@EditedEntityContainer("stepDc")
public class StepDetailView extends StandardDetailView<Step> {

    // tag::keyboard-shortcuts[]
    @ViewComponent
    private JmixButton saveAndCloseBtn;
    @ViewComponent
    private JmixButton closeBtn;

    @Subscribe
    public void onInit(final InitEvent event) {
        saveAndCloseBtn.addFocusShortcut(
                Key.ENTER, KeyModifier.META);

        closeBtn.setShortcutCombination(
                KeyCombination.create(Key.ESCAPE, KeyModifier.SHIFT));
    }
    // end::keyboard-shortcuts[]

}