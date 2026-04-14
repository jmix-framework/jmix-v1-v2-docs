package com.company.appsettingsex1.view.customer;

import com.company.appsettingsex1.entity.Customer;
import com.company.appsettingsex1.entity.CustomerGrade;
import com.company.appsettingsex1.entity.CustomerSettings;
import com.company.appsettingsex1.view.main.MainView;
import com.vaadin.flow.router.Route;
import io.jmix.appsettings.AppSettings;
import io.jmix.flowui.view.*;
import org.springframework.beans.factory.annotation.Autowired;

@Route(value = "customers/:id", layout = MainView.class)
@ViewController(id = "Customer.detail")
@ViewDescriptor(path = "customer-detail-view.xml")
@EditedEntityContainer("customerDc")
public class CustomerDetailView extends StandardDetailView<Customer> {

    // tag::app-settings[]
    @Autowired
    private AppSettings appSettings;

    @Subscribe
    public void onInitEntity(InitEntityEvent<Customer> event) {
        CustomerSettings customerSettings = appSettings.load(CustomerSettings.class);
        CustomerGrade defaultGrade = customerSettings.getDefaultGrade();

        Customer customer = event.getEntity();
        customer.setCountry(customerSettings.getCountry());
        customer.setGrade(defaultGrade);
    }
    // end::app-settings[]

}