package com.company.clientapp.view.customer;

import com.company.clientapp.entity.Customer;
import com.company.clientapp.service.CustomerService;
import com.company.clientapp.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.flowui.view.EditedEntityContainer;
import io.jmix.flowui.view.StandardDetailView;
import io.jmix.flowui.view.ViewController;
import io.jmix.flowui.view.ViewDescriptor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Route(value = "customers/:id", layout = MainView.class)
@ViewController("Customer.detail")
@ViewDescriptor("customer-detail-view.xml")
@EditedEntityContainer("customerDc")
public class CustomerDetailView extends StandardDetailView<Customer> {

    // tag::service[]
    @Autowired
    private CustomerService customerService;

    private void processCustomers(String name) {
        List<Customer> customers = customerService.getCustomersByName(name);
        // ...
    }
    // end::service[]
}
