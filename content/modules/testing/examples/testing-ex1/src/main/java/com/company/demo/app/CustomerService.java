package com.company.demo.app;

import com.company.demo.entity.Customer;
import io.jmix.core.DataManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
// tag::customer-service[]
@Component
public class CustomerService {

    @Autowired
    private DataManager dataManager;

    public Optional<Customer> findByEmail(String email) {
        return dataManager.load(Customer.class)
                .query("select c from Customer c where c.email = :email")
                .parameter("email", email)
                .optional();
    }
}
// end::customer-service[]
