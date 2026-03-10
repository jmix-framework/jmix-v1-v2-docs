package com.company.onboarding.view.usingfacetfragment;

// tag::view[]
import com.company.onboarding.view.fragments.settingsfragment.SettingsFragment;
import com.company.onboarding.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.Fragments;
import io.jmix.flowui.component.details.JmixDetails;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "settings-fragment-view", layout = MainView.class)
@ViewController(id = "SettingsFragmentView")
@ViewDescriptor(path = "settings-fragment-view.xml")
public class SettingsFragmentView extends StandardView {
    @ViewComponent
    private JmixDetails javaFacetDetails;
    @Autowired
    private Fragments fragments; // <1>

    @Subscribe
    public void onInit(final InitEvent event) {
        SettingsFragment javaFragment = fragments.create(this, SettingsFragment.class, "javaFragment"); // <2>
        javaFacetDetails.add(javaFragment); // <3>
    }
}
// end::view[]