package com.company.demo.repository;

import com.company.demo.entity.Customer;
import com.company.demo.entity.CustomerGrade;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.core.repository.FetchPlan;
import io.jmix.core.repository.JmixDataRepository;
import io.jmix.core.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository2 extends JmixDataRepository<Customer, UUID> {

    // tag::fetch-plan[]
    @FetchPlan("customer-minimal")
    List<Customer> findByEmail(String email);
    // end::fetch-plan[]

    // tag::query-multiple-fields[]
    @Query(
            value = "select c.grade, count(c) from sample_Customer c group by c.grade",
            properties = {"grade", "count"}
    )
    List<KeyValueEntity> getCountGroupByGrade();
    // end::query-multiple-fields[]

    // tag::query-single-value[]
    @Query(value = "select count(c) from sample_Customer c where c.grade = ?1")
    Long getCountByGrade(CustomerGrade grade);
    // end::query-single-value[]

    // tag::query-single-field[]
    @Query(value = "select distinct c.grade from sample_Customer c")
    List<CustomerGrade> getAllGrades();
    // end::query-single-field[]

    @Query(value = "select distinct c.name from sample_Customer c")
    List<String> getAllNames();

}
