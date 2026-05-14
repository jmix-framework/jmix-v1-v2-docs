package com.company.onboarding.view.customer;

// tag::imports[]
import com.company.onboarding.entity.Customer;
import com.vaadin.flow.component.ClickEvent;
// end::imports[]
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
// tag::imports[]
import com.vaadin.flow.data.renderer.Renderer;
import io.jmix.core.LoadContext;
// end::imports[]
import io.jmix.flowui.fragment.Fragment;
// tag::imports[]
import io.jmix.flowui.kit.component.button.JmixButton;
import io.jmix.flowui.model.CollectionContainer;
// end::imports[]
// tag::imports[]
import io.jmix.flowui.view.*;
import java.util.List;
// end::imports[]
// tag::class-declaration[]

// class declaration and annotations omitted

// end::class-declaration[]
public class CustomerListFragment2 extends Fragment<VerticalLayout> {

    // tag::autowire[]
    @ViewComponent
    public JmixButton button; // <1>

    @ViewComponent
    public CollectionContainer<Customer> collectionDc; // <2>

    @Subscribe
    public void onReady(ReadyEvent event) { // <3>
        // ...
    }

    @Subscribe(value = "button", subject = "clickListener")
    public void onButtonClick(ClickEvent<JmixButton> event) { // <4>
        // ...
    }

    @Install(to = "collectionDl", target = Target.DATA_LOADER)
    public List<Customer> collectionDlLoadDelegate(LoadContext<Customer> loadContext) { // <5>
        return loadCustomers(loadContext);
    }

    @Supply(to = "dataGrid.name", subject = "renderer")
    public Renderer<Customer> dataGridNameRenderer() { // <6>
        return createRenderer();
    }
    // end::autowire[]

    // tag::host[]
    @Subscribe(target = Target.HOST_CONTROLLER)
    public void onHostInit(View.InitEvent event) {
        // ...
    }

    @Subscribe(target = Target.HOST_CONTROLLER)
    public void onHostBeforeShow(View.BeforeShowEvent event) {
        // ...
    }

    @Subscribe(target = Target.HOST_CONTROLLER)
    public void onHostReady(View.ReadyEvent event) {
        // ...
    }
    // end::host[]

    private List<Customer> loadCustomers(LoadContext<Customer> loadContext) {
        return null;
    }

    private Renderer<Customer> createRenderer() {
        return null;
    }
}