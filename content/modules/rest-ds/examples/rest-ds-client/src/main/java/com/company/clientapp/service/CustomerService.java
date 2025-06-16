// tag::service[]
package com.company.clientapp.service;

import com.company.clientapp.entity.Customer;
import io.jmix.restds.annotation.RemoteService;

import java.util.List;

@RemoteService(store = "serviceapp", remoteName = "customers")
public interface CustomerService {

    List<Customer> getCustomersByName(String name);
}
// end::service[]
