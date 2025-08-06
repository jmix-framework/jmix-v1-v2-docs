package com.company.demo.bean;

import com.company.demo.entity.Customer;
import com.company.demo.entity.CustomerGrade;
import io.jmix.core.*;
import io.jmix.core.querycondition.LogicalCondition;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.core.session.SessionData;
import jakarta.persistence.LockModeType;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

// tag::inject-dm[]
@Component
public class CustomerService {

    @Autowired
    private DataManager dataManager;
    // end::inject-dm[]

    // tag::unconstrained-dm[]
    @Autowired
    private UnconstrainedDataManager unconstrainedDataManager;

    public Customer loadByIdUnconstrained(UUID customerId) {
        return unconstrainedDataManager.load(Customer.class)
                .id(customerId)
                .one();
    }
    // end::unconstrained-dm[]

    // tag::session-data[]
    @Autowired
    private ObjectProvider<SessionData> sessionDataProvider;

    void setCustomerCodeInSession(String code) {
        sessionDataProvider.getObject().setAttribute("customerCode", code);
    }
    // end::session-data[]

    // tag::load-by-id[]
    Customer loadById(UUID customerId) {
        return dataManager.load(Customer.class) // <1>
                .id(customerId)                 // <2>
                .one();                         // <3>
    }
    // end::load-by-id[]

    // tag::load-by-generic-id[]
    Customer loadByGenericId(Id<Customer> customerId) {
        return dataManager.load(customerId).one();
    }
    // end::load-by-generic-id[]

    // tag::load-optional[]
    Customer loadOrCreate(UUID customerId) {
        return dataManager.load(Customer.class)
                .id(customerId)
                .optional() // <1>
                .orElse(dataManager.create(Customer.class));
    }
    // end::load-optional[]

    // tag::load-by-ids[]
    List<Customer> loadByIds(UUID id1, UUID id2) {
        return dataManager.load(Customer.class)
                .ids(id1, id2)
                .list();
    }
    // end::load-by-ids[]


    // tag::load-all[]
    List<Customer> loadAll() {
        return dataManager.load(Customer.class).all().list();
    }
    // end::load-all[]

    // tag::load-by-query[]
    List<Customer> loadByQuery() {
        return dataManager.load(Customer.class)
                .query("e.email like ?1 and e.grade = ?2", "%@company.com", CustomerGrade.PLATINUM)
                .list();
    }
    // end::load-by-query[]

    // tag::load-by-full-query[]
    List<Customer> loadByFullQuery() {
        return dataManager.load(Customer.class)
                .query("select c from sample_Customer c where c.email like :email and c.grade = :grade")
                .parameter("email", "%@company.com")
                .parameter("grade", CustomerGrade.PLATINUM)
                .list();
    }
    // end::load-by-full-query[]

    // tag::load-page-by-query[]
    List<Customer> loadPageByQuery(int offset, int limit) {
        return dataManager.load(Customer.class)
                .query("e.grade = ?1", CustomerGrade.BRONZE)
                .firstResult(offset)
                .maxResults(limit)
                .list();
    }
    // end::load-page-by-query[]

    // tag::load-by-conditions[]
    List<Customer> loadByConditions() {
        return dataManager.load(Customer.class)
                .condition(                                                      // <1>
                    LogicalCondition.and(                                        // <2>
                        PropertyCondition.contains("email", "@company.com"),     // <3>
                        PropertyCondition.equal("grade", CustomerGrade.PLATINUM) // <3>
                    )
                )
                .list();
    }
    // end::load-by-conditions[]

    // tag::load-by-condition[]
    List<Customer> loadByCondition() {
        return dataManager.load(Customer.class)
                .condition(PropertyCondition.contains("email", "@company.com"))
                .list();
    }
    // end::load-by-condition[]

    void emptyConditions() {
        // tag::empty-conditions[]
        dataManager.load(Customer.class)
                .condition(PropertyCondition.contains("email", null))
                .list();

        dataManager.load(Customer.class)
                .condition(PropertyCondition.contains("email", ""))
                .list();

        dataManager.load(Customer.class)
                .condition(PropertyCondition.inList("email", Collections.emptyList()))
                .list();

        dataManager.load(Customer.class)
                .all()
                .list();
        // end::empty-conditions[]
    }

    // tag::load-and-sort[]
    List<Customer> loadSorted() {
        return dataManager.load(Customer.class)
                .condition(PropertyCondition.contains("email", "@company.com"))
                .sort(Sort.by("name"))
                .list();
    }
    // end::load-and-sort[]

    // tag::load-by-query-sorted[]
    List<Customer> loadByQuerySorted() {
        return dataManager.load(Customer.class)
                .query("e.grade = ?1 order by e.name", CustomerGrade.BRONZE)
                .list();
    }
    // end::load-by-query-sorted[]

    // tag::using-locks[]
    List<Customer> loadAndLock() {
        return dataManager.load(Customer.class)
                .query("e.email like ?1", "%@company.com")
                .lockMode(LockModeType.PESSIMISTIC_WRITE)
                .list();
    }
    // end::using-locks[]

    // tag::save[]
    Customer saveCustomer(Customer entity) {
        return dataManager.save(entity);
    }
    // end::save[]

    // tag::save-all[]
    EntitySet saveCustomers(List<Customer> entities) {
        return dataManager.saveAll(entities);
    }
    // end::save-all[]

    // tag::save-without-reload[]
    void saveCustomerWithoutReload(Customer entity) {
        dataManager.saveWithoutReload(entity);
    }
    // end::save-without-reload[]

    // tag::remove[]
    void removeCustomer(Customer entity) {
        dataManager.remove(entity);
    }
    // end::remove[]

    // tag::remove-list[]
    void removeCustomers(List<Customer> entities) {
        dataManager.remove(entities);
    }
    // end::remove-list[]

    // tag::remove-by-id[]
    void removeCustomer(UUID customerId) {
        dataManager.remove(Id.of(customerId, Customer.class));
    }
    // end::remove-by-id[]

    public Customer updateCustomerGrade(Customer customer) {
        return customer;
    }

    // tag::transaction-template-inject[]
    @Autowired
    private TransactionTemplate transactionTemplate;
    // end::transaction-template-inject[]

    // tag::transaction-template-without-result[]
    public void createCustomer() {
        transactionTemplate.executeWithoutResult(status -> {
            Customer customer = dataManager.create(Customer.class);
            customer.setName("Alice");
            dataManager.save(customer);
        });
    }
    // end::transaction-template-without-result[]

    // tag::load-in-separate-transaction[]
    Customer loadCustomerInSeparateTransaction(UUID customerId) {
        return dataManager.load(Customer.class)
                .id(customerId)
                .joinTransaction(false)
                .one();
    }
    // end::load-in-separate-transaction[]

    // tag::save-in-separate-transaction[]
    void saveCustomerInSeparateTransaction(Customer entity) {
        SaveContext saveContext = new SaveContext().saving(entity)
                .setJoinTransaction(false);
        dataManager.save(saveContext);
    }
    // end::save-in-separate-transaction[]

}
