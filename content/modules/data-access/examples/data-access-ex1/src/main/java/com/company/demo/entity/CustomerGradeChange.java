package com.company.demo.entity;

import io.jmix.core.DeletePolicy;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.OnDeleteInverse;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;
import java.util.UUID;

@JmixEntity
@Table(name = "CUSTOMER_GRADE_CHANGE")
@Entity(name = "sample_CustomerGradeChange")
public class CustomerGradeChange {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @CreatedDate
    @Column(name = "CREATED_DATE")
    private Date createdDate;

    @CreatedBy
    @Column(name = "CREATED_BY")
    private String createdBy;

    @OnDeleteInverse(DeletePolicy.CASCADE)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CUSTOMER_ID")
    private Customer customer;

    @Column(name = "OLD_GRADE")
    private String oldGrade;

    @Column(name = "NEW_GRADE")
    private String newGrade;

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public CustomerGrade getNewGrade() {
        return newGrade == null ? null : CustomerGrade.fromId(newGrade);
    }

    public void setNewGrade(CustomerGrade newGrade) {
        this.newGrade = newGrade == null ? null : newGrade.getId();
    }

    public CustomerGrade getOldGrade() {
        return oldGrade == null ? null : CustomerGrade.fromId(oldGrade);
    }

    public void setOldGrade(CustomerGrade oldGrade) {
        this.oldGrade = oldGrade == null ? null : oldGrade.getId();
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