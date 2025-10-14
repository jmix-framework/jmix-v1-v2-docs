package com.company.groupdatagridex1.repository;

import com.company.groupdatagridex1.entity.Customer;
import io.jmix.core.repository.JmixDataRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository1 extends JmixDataRepository<Customer, UUID> {
}