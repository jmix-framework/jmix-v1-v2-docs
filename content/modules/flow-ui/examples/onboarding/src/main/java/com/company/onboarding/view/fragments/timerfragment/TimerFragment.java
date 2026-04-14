package com.company.onboarding.view.fragments.timerfragment;

// tag::fragment-java[]
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.jmix.flowui.component.textfield.TypedTextField;
import io.jmix.flowui.facet.Timer;
import io.jmix.flowui.fragment.Fragment;
import io.jmix.flowui.fragment.FragmentDescriptor;
import io.jmix.flowui.view.Subscribe;
import io.jmix.flowui.view.ViewComponent;

@FragmentDescriptor("timer-fragment.xml")
public class TimerFragment extends Fragment<VerticalLayout> {
    @ViewComponent
    private TypedTextField<String> displayField;

    private int tick;

    @Subscribe("timer")
    public void onTimerTimerAction(final Timer.TimerActionEvent event) {
        displayField.setValue("Timer tick: " + tick++);
    }
}
// end::fragment-java[]