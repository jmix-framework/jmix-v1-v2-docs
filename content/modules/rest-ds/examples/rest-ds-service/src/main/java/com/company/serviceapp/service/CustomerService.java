// tag::service[]
package com.company.serviceapp.service;

import com.company.serviceapp.entity.Customer;
import io.jmix.core.DataManager;
import io.jmix.rest.annotation.RestMethod;
import io.jmix.rest.annotation.RestService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
// end::service[]

import java.util.UUID;

// tag::service[]

@RestService("customers")
public class CustomerService {

    @Autowired
    private DataManager dataManager;

    @RestMethod
    public List<Customer> getCustomersByName(String name) {
        return dataManager.load(Customer.class)
                .query("e.name = ?1", name).list();
    }
// end::service[]

    // tag::update-service[]
    @RestMethod
    public void updateCustomerName(UUID customerId, String name) {
        Customer customer = dataManager.load(Customer.class).id(customerId).one();
        customer.setName(name);
        dataManager.save(customer);
    }
    // end::update-service[]

// tag::service[]
}
// end::service[]
