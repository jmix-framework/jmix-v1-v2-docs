package com.company.onboarding.view.customer;

// tag::imports[]
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import io.jmix.flowui.fragment.Fragment;
import io.jmix.flowui.fragment.FragmentDescriptor;
import io.jmix.flowui.view.Subscribe;

// end::imports[]
// tag::fragment-java[]
@FragmentDescriptor("customer-list-fragment.xml")
public class CustomerListFragment extends Fragment<VerticalLayout> {

    @Subscribe
    public void onReady(final ReadyEvent event) {
        getFragmentData().loadAll(); // <1>
    }
}
// end::fragment-java[]