package com.company.demo.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.util.UUID;

@JmixEntity
@Table(name = "CUSTOMER_ACCESS", indexes = {
        @Index(name = "IDX_CUSTOMER_ACCESS_CUSTOMER", columnList = "CUSTOMER_ID"),
        @Index(name = "IDX_CUSTOMER_ACCESS_EMPLOYEE", columnList = "EMPLOYEE_ID")
})
@Entity
public class CustomerAccess {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @JoinColumn(name = "CUSTOMER_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Customer customer;

    @JoinColumn(name = "EMPLOYEE_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

}