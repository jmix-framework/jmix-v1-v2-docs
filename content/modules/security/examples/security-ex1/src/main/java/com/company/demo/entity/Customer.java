package com.company.demo.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.*;

import java.util.List;
import java.util.UUID;

@JmixEntity
@Table(name = "CUSTOMER")
@Entity(name = "Customer")
public class Customer {
    @JmixGeneratedValue
    @Column(name = "ID", nullable = false)
    @Id
    private UUID id;

    @Column(name = "VERSION", nullable = false)
    @Version
    private Integer version;

    @InstanceName
    @Column(name = "NAME")
    private String name;

    @Column(name = "REGION")
    private String region;

    @Composition
    @OneToMany(mappedBy = "customer")
    private List<CustomerDetail> details;

    @Column(name = "ACTIVE")
    private Boolean active;

    @Column(name = "CONFIDENTIAL_INFO")
    @Lob
    private String confidentialInfo;

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getConfidentialInfo() {
        return confidentialInfo;
    }

    public void setConfidentialInfo(String confidentialInfo) {
        this.confidentialInfo = confidentialInfo;
    }

    public List<CustomerDetail> getDetails() {
        return details;
    }

    public void setDetails(List<CustomerDetail> details) {
        this.details = details;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}