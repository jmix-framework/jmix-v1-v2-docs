package com.company.demo.listener;

// tag::import-encryption-service[]
import com.company.demo.bean.EncryptionService;
// end::import-encryption-service[]
// tag::import-customer[]
import com.company.demo.entity.Customer;
// end::import-customer[]
// tag::import-customer-grade[]
import com.company.demo.entity.CustomerGrade;
// end::import-customer-grade[]
// tag::import-customer-grade-change[]
import com.company.demo.entity.CustomerGradeChange;
// end::import-customer-grade-change[]
import com.company.demo.entity.SentEmail;
// tag::import-data-manager[]
import io.jmix.core.DataManager;
// end::import-data-manager[]
// tag::import-id[]
import io.jmix.core.Id;
// end::import-id[]
// tag::import-entity-changed-event[]
import io.jmix.core.event.EntityChangedEvent;
// end::import-entity-changed-event[]
// tag::import-entity-loading-event[]
import io.jmix.core.event.EntityLoadingEvent;
// end::import-entity-loading-event[]
// tag::import-entity-saving-event[]
import io.jmix.core.event.EntitySavingEvent;
// end::import-entity-saving-event[]
// tag::import-logger[]
import org.slf4j.Logger;
// end::import-logger[]
// tag::import-logger-factory[]
import org.slf4j.LoggerFactory;
// end::import-logger-factory[]
// tag::import-autowired[]
import org.springframework.beans.factory.annotation.Autowired;
// end::import-autowired[]
// tag::import-event-listener[]
import org.springframework.context.event.EventListener;
// end::import-event-listener[]
// tag::import-component[]
import org.springframework.stereotype.Component;
// end::import-component[]
import org.springframework.transaction.annotation.Propagation;
// tag::import-transactional[]
import org.springframework.transaction.annotation.Transactional;
// end::import-transactional[]
// tag::import-transactional-event-listener[]
import org.springframework.transaction.event.TransactionalEventListener;
// end::import-transactional-event-listener[]

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