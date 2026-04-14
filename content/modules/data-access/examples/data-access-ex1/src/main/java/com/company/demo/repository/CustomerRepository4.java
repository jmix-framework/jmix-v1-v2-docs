package com.company.demo.repository;

import com.company.demo.entity.Customer;
import io.jmix.core.repository.JmixDataRepository;
import io.jmix.core.repository.QueryHints;
import io.jmix.dynattr.DynAttrQueryHints;
import jakarta.persistence.QueryHint;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository4 extends JmixDataRepository<Customer, UUID> {

    // tag::dynamic-attribute-query-hint[]
    @QueryHints({@QueryHint(name = DynAttrQueryHints.LOAD_DYN_ATTR, value = "true")})
    List<Customer> findByEmail(String email);
    // end::dynamic-attribute-query-hint[]

}