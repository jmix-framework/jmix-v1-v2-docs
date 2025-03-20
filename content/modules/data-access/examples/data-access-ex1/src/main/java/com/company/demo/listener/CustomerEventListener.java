package com.company.demo.listener;

import com.company.demo.bean.EncryptionService;
import com.company.demo.entity.Customer;
import com.company.demo.entity.CustomerGrade;
import com.company.demo.entity.CustomerGradeChange;
import com.company.demo.entity.SentEmail;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.SaveContext;
import io.jmix.core.event.EntityChangedEvent;
import io.jmix.core.event.EntityLoadingEvent;
import io.jmix.core.event.EntitySavingEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

// tag::listener-bean[]
@Component
public class CustomerEventListener {

// end::listener-bean[]

    // tag::logger[]
    private static final Logger log = LoggerFactory.getLogger(CustomerEventListener.class);

    // end::logger[]

    // tag::encryption[]
    @Autowired
    private EncryptionService encryptionService;

    @EventListener
    void onCustomerSaving(EntitySavingEvent<Customer> event) {
        Customer customer = event.getEntity();
        String encrypted = encryptionService.encrypt(customer.getSensitiveData());
        customer.setEncryptedData(encrypted);
    }

    @EventListener
    void onCustomerLoading(EntityLoadingEvent<Customer> event) {
        Customer customer = event.getEntity();
        String sensitive = encryptionService.decrypt(customer.getEncryptedData());
        customer.setSensitiveData(sensitive);
    }
    // end::encryption[]

    // tag::data-manager[]
    @Autowired
    private DataManager dataManager;

    // end::data-manager[]

    // tag::before-commit[]
    @EventListener
    void onCustomerChangedBeforeCommit(EntityChangedEvent<Customer> event) {
        if (event.getType() != EntityChangedEvent.Type.DELETED  // <1>
                && event.getChanges().isChanged("grade")) {     // <2>

            registerGradeChange(
                    event.getEntityId(),                        // <3>
                    event.getChanges().getOldValue("grade")     // <4>
            );
        }
    }

    private void registerGradeChange(Id<Customer> customerId, CustomerGrade oldGrade) {
        Customer customer = dataManager.load(customerId).one(); // <5>

        CustomerGradeChange gradeChange = dataManager.create(CustomerGradeChange.class);
        gradeChange.setCustomer(customer);
        gradeChange.setOldGrade(oldGrade);
        gradeChange.setNewGrade(customer.getGrade());
        dataManager.save(gradeChange);
    }
    // end::before-commit[]

    // tag::after-commit[]
    @TransactionalEventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW) // <1>
    void onCustomerChangedAfterCommit(EntityChangedEvent<Customer> event) {
        try {
            if (event.getType() != EntityChangedEvent.Type.DELETED
                    && event.getChanges().isChanged("grade")) {

                Customer customer = dataManager.load(event.getEntityId()).one();

                emailCustomerTheirNewGrade(customer.getEmail(), customer.getGrade());
            }
        } catch (Exception e) {
            log.error("Error handling Customer changes after commit", e);
        }
    }
    // end::after-commit[]

    private void emailCustomerTheirNewGrade(String customerEmail, CustomerGrade grade) {
        SentEmail entity = dataManager.create(SentEmail.class);
        entity.setEmailedTo(customerEmail);
        dataManager.save(entity);
    }
}
