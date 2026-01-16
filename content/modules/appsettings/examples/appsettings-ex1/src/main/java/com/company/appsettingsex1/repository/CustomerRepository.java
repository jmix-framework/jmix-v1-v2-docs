package com.company.appsettingsex1.repository;

import com.company.appsettingsex1.entity.Customer;
import io.jmix.core.repository.JmixDataRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CustomerRepository extends JmixDataRepository<Customer, UUID> {
}