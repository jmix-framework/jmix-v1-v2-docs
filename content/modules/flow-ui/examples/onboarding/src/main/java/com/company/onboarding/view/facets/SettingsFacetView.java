package com.company.onboarding.view.facets;


import com.company.onboarding.view.main.MainView;

import com.vaadin.flow.router.Route;
import io.jmix.flowui.component.checkbox.JmixCheckbox;
import io.jmix.flowui.facet.SettingsFacet;
import io.jmix.flowui.facet.ViewSettingsFacet;
import io.jmix.flowui.facet.settings.ViewSettings;
import io.jmix.flowui.view.*;

import java.util.Optional;

@Route(value = "settings-facet-view", layout = MainView.class)
@ViewController("SettingsFacetView")
@ViewDescriptor("settings-facet-view.xml")
public class SettingsFacetView extends StandardView {
    // tag::checkbox[]
    @ViewComponent
    private JmixCheckbox checkbox;

    // end::checkbox[]
    // tag::settings[]
    @ViewComponent
    private ViewSettingsFacet settings;

    // end::settings[]

    // tag::applySettingsDelegate[]
    @Install(to = "settings", subject = "applySettingsDelegate")
    private void settingsApplySettingsDelegate(final SettingsFacet.SettingsContext<ViewSettings> settingsContext) {
        settings.applySettings();
        Optional<Boolean> value = settingsContext.getSettings().getBoolean("checkbox", "value");
        checkbox.setValue(value.orElse(Boolean.FALSE));
    }
    // end::applySettingsDelegate[]

    // tag::saveSettingsDelegate[]
    @Install(to = "settings", subject = "saveSettingsDelegate")
    private void settingsSaveSettingsDelegate(final SettingsFacet.SettingsContext<ViewSettings> settingsContext) {
        settingsContext.getSettings().put("testCheckbox", "value", checkbox.getValue());
        settings.saveSettings();
    }
    // end::saveSettingsDelegate[]
}