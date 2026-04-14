package com.company.appsettingsex1.entity;

import io.jmix.appsettings.defaults.AppSettingsDefault;
import io.jmix.appsettings.entity.AppSettingsEntity;
import io.jmix.core.metamodel.annotation.JmixEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

// tag::settings-entity[]
@JmixEntity
@Table(name = "CUSTOMER_SETTINGS")
@Entity
public class CustomerSettings extends AppSettingsEntity {

    @AppSettingsDefault("B")
    @Column(name = "DEFAULT_GRADE")
    private String defaultGrade;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "NOTIFICATION_TEXT")
    private String notificationText;

    // getters and setters
    // end::settings-entity[]

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setDefaultGrade(CustomerGrade defaultGrade) {
        this.defaultGrade = defaultGrade == null ? null : defaultGrade.getId();
    }

    public CustomerGrade getDefaultGrade() {
        return defaultGrade == null ? null : CustomerGrade.fromId(defaultGrade);
    }

    public String getNotificationText() {
        return notificationText;
    }

    public void setNotificationText(String notificationText) {
        this.notificationText = notificationText;
    }

}