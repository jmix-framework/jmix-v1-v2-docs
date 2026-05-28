package com.company.onboarding.entity;

import io.jmix.core.FileRef;
import io.jmix.core.HasTimeZone;
import io.jmix.core.annotation.Secret;
import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.SystemLevel;
import io.jmix.core.metamodel.annotation.Composition;
import io.jmix.core.metamodel.annotation.DependsOnProperties;
import io.jmix.core.metamodel.annotation.InstanceName;
import io.jmix.core.metamodel.annotation.JmixEntity;
import io.jmix.security.authentication.JmixUserDetails;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import org.springframework.security.core.GrantedAuthority;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

// tag::user-entity[]
@JmixEntity
@Entity
@Table(name = "USER_", indexes = {
        @Index(name = "IDX_USER__ON_USERNAME", columnList = "USERNAME", unique = true),
        @Index(name = "IDX_USER__DEPARTMENT", columnList = "DEPARTMENT_ID")
})
public class User implements JmixUserDetails, HasTimeZone {

    /* other attributes */

    //end::user-entity[]
    @Id
    @Column(name = "ID", nullable = false)
    @JmixGeneratedValue
    private UUID id;

    // tag::user-hobby-link[]
    @JoinTable(name = "USER_HOBBY_LINK",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "HOBBY_ID"))
    @ManyToMany
    private List<Hobby> userHobbies;
// end::user-hobby-link[]

    @OneToMany(mappedBy = "user")
    private List<Hobby> hobbies;

    // tag::document[]
    @Column(name = "DOCUMENT")
    private byte[] document;
    // end::document[]

    @Version
    @Column(name = "VERSION", nullable = false)
    private Integer version;

    @Column(name = "USERNAME", nullable = false)
    protected String username;

    @Secret
    @SystemLevel
    @Column(name = "PASSWORD")
    protected String password;

    @Column(name = "PASSWORD_EXPIRATION")
    private LocalDateTime passwordExpiration;

    @Column(name = "FIRST_NAME")
    protected String firstName;

    @Column(name = "LAST_NAME")
    protected String lastName;

    @Email
    @Column(name = "EMAIL")
    protected String email;

    @Column(name = "ACTIVE")
    protected Boolean active = true;

    @Column(name = "TIME_ZONE_ID")
    protected String timeZoneId;

    @JoinColumn(name = "DEPARTMENT_ID")
    @ManyToOne(fetch = FetchType.LAZY)
    private Department department;

    @Column(name = "JOINING_DATE")
    private LocalDate joiningDate;

    // tag::picture[]
    @Column(name = "PICTURE", length = 1024)
    private FileRef picture;

    // end::picture[]
    @Column(name = "ONBOARDING_STATUS")
    private Integer onboardingStatus;

    @OrderBy("sortValue")
    @Composition
    @OneToMany(mappedBy = "user")
    private List<UserStep> steps;

    @Transient
    protected Collection<? extends GrantedAuthority> authorities;

    public List<Hobby> getUserHobbies() {
        return userHobbies;
    }

    public void setUserHobbies(List<Hobby> userHobbies) {
        this.userHobbies = userHobbies;
    }

    public List<Hobby> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<Hobby> hobbies) {
        this.hobbies = hobbies;
    }

    public byte[] getDocument() {
        return document;
    }

    public void setDocument(byte[] document) {
        this.document = document;
    }

    public LocalDateTime getPasswordExpiration() {
        return passwordExpiration;
    }

    public void setPasswordExpiration(LocalDateTime passwordExpiration) {
        this.passwordExpiration = passwordExpiration;
    }

    public List<UserStep> getSteps() {
        return steps;
    }

    public void setSteps(List<UserStep> steps) {
        this.steps = steps;
    }

    public OnboardingStatus getOnboardingStatus() {
        return onboardingStatus == null ? null : OnboardingStatus.fromId(onboardingStatus);
    }

    public void setOnboardingStatus(OnboardingStatus onboardingStatus) {
        this.onboardingStatus = onboardingStatus == null ? null : onboardingStatus.getId();
    }

    public FileRef getPicture() {
        return picture;
    }

    public void setPicture(FileRef picture) {
        this.picture = picture;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities != null ? authorities : Collections.emptyList();
    }

    @Override
    public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Boolean.TRUE.equals(active);
    }

    @InstanceName
    @DependsOnProperties({"firstName", "lastName", "username"})
    public String getDisplayName() {
        return String.format("%s %s [%s]", (firstName != null ? firstName : ""),
                (lastName != null ? lastName : ""), username).trim();
    }

    @Override
    public String getTimeZoneId() {
        return timeZoneId;
    }

    public void setTimeZoneId(String timeZoneId) {
        this.timeZoneId = timeZoneId;
    }
    // tag::user-entity[]
}
// end::user-entity[]