package com.company.demo.bean;

import com.company.demo.entity.Customer;
import com.company.demo.entity.Order;
import com.company.demo.entity.Product;
import io.jmix.core.*;
import io.jmix.core.entity.KeyValueEntity;
import io.jmix.core.querycondition.PropertyCondition;
import io.jmix.data.PersistenceHints;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class OrderService {

    @Autowired
    private DataManager dataManager;

    @Autowired
    private TransactionTemplate transactionTemplate;

    // tag::transaction-template[]
    @Autowired
    private PlatformTransactionManager transactionManager;

    // joins existing transaction
    public TransactionTemplate getTransactionTemplate() {
        return new TransactionTemplate(transactionManager);
    }

    // always creates new transaction
    public TransactionTemplate getRequiresNewTransactionTemplate() {
        TransactionTemplate tt = new TransactionTemplate(transactionManager);
        tt.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        return tt;
    }
    // end::transaction-template[]

    // tag::load-by-query[]
    List<Order> loadOrdersByProduct(String productName) {
        return dataManager.load(Order.class)
                .query("from sample_Order o, sample_OrderLine l " +
                        "where l.order = o and l.product.name = ?1", productName)
                .list();
    }
    // end::load-by-query[]

    // tag::load-values[]
    String getCustomerPurchases(LocalDate fromDate) {
        List<KeyValueEntity> kvEntities = dataManager.loadValues(
                "select o.customer, sum(o.amount) from sample_Order o " +
                        "where o.date >= :date group by o.customer")
                .store("main")                      // <1>
                .properties("customer", "sum")      // <2>
                .parameter("date", fromDate)
                .list();

        StringBuilder sb = new StringBuilder();
        for (KeyValueEntity kvEntity : kvEntities) {
            Customer customer = kvEntity.getValue("customer");  // <3>
            BigDecimal sum = kvEntity.getValue("sum");          // <3>
            sb.append(customer.getName()).append(" : ").append(sum).append("\n");
        }
        return sb.toString();
    }
    // end::load-values[]

    // tag::load-value[]
    BigDecimal getTotal(LocalDate toDate) {
        return dataManager.loadValue(
                    "select sum(o.amount) from sample_Order o where o.date >= :date",
                    BigDecimal.class    // <1>
                )
                .store("main")          // <2>
                .parameter("date", toDate)
                .one();
    }
    // end::load-value[]

    // tag::load-by-condition[]
    List<Order> loadByCondition() {
        return dataManager.load(Order.class)
                .condition(PropertyCondition.contains("customer.email", "@company.com"))
                .list();
    }
    // end::load-by-condition[]

    // tag::load-sorted[]
    List<Order> loadSorted() {
        return dataManager.load(Order.class)
                .all()
                .sort(Sort.by("customer.name"))
                .list();
    }
    // end::load-sorted[]

    // tag::save-multiple[]
    Order createOrderWithCustomer() {
        Customer customer = dataManager.create(Customer.class);
        customer.setName("Alice");

        Order order = dataManager.create(Order.class);
        order.setCustomer(customer);

        EntitySet savedEntities = dataManager.save(order, customer); // <1>

        return savedEntities.get(order); // <2>
    }
    // end::save-multiple[]

    // tag::save-context[]
    EntitySet saveUsingContext(List<Customer> entities) {
        SaveContext saveContext = new SaveContext();
        for (Customer entity : entities) {
            saveContext.saving(entity);
        }
        return dataManager.save(saveContext);
    }
    // end::save-context[]

    // tag::save-discard[]
    void saveAndReturnNothing(List<Customer> entities) {
        // create SaveContext and set its 'discardSaved' property
        SaveContext saveContext = new SaveContext().setDiscardSaved(true);
        for (Customer entity : entities) {
            saveContext.saving(entity);
        }
        dataManager.save(saveContext);
    }
    // end::save-discard[]

    // tag::save-unconstrained[]
    void saveByUnconstrainedDataManager(List<Customer> entities) {
        // use 'UnconstrainedDataManager' which bypasses security
        dataManager.unconstrained().saveWithoutReload(entities);
    }
    // end::save-unconstrained[]

    // tag::save-batches[]
    void saveInBatches(List<Customer> entities) {
        // save by 100 instances
        for (int i = 0; i < entities.size(); i += 100) {
            int endIndex = Math.min(i + 100, entities.size());
            List<Customer> batch = entities.subList(i, endIndex);
            dataManager.saveWithoutReload(batch);
        }
    }
    // end::save-batches[]

    // tag::remove-multiple[]
    void removeOrderWithCustomer(Order order) {
        dataManager.remove(order, order.getCustomer());
    }
    // end::remove-multiple[]

    // tag::hard-delete[]
    void hardDelete(Product product) {
        dataManager.save(
                new SaveContext()
                        .removing(product)
                        .setHint(PersistenceHints.SOFT_DELETION, false)
        );
    }
    // end::hard-delete[]

    // tag::lazy-loading[]
    String getCustomerName(Id<Order> orderId) {
        Order order = dataManager.load(orderId).one();
        return order.getCustomer().getName(); // <1>
    }

    List<String> getProductNames(Id<Order> orderId) {
        Order order = dataManager.load(orderId).one();
        return order.getLines().stream() // <2>
                .map(orderLine -> orderLine.getProduct().getName()) // <3>
                .collect(Collectors.toList());
    }
    // end::lazy-loading[]

    // tag::transaction-template-execute[]
    public UUID createOrderAndReturnId() {
        return transactionTemplate.execute(status -> {
            Customer customer = dataManager.create(Customer.class);
            customer.setName("Alice");
            customer = dataManager.save(customer);

            Order order = dataManager.create(Order.class);
            order.setCustomer(customer);

            order = dataManager.save(order);
            return order.getId();
        });
    }
    // end::transaction-template-execute[]

    // tag::transaction-template-without-result[]
    public void createOrder() {
        transactionTemplate.executeWithoutResult(status -> {
            Customer customer = dataManager.create(Customer.class);
            customer.setName("Alice");
            customer = dataManager.save(customer);

            Order order = dataManager.create(Order.class);
            order.setCustomer(customer);

            dataManager.save(order);
        });
    }
    // end::transaction-template-without-result[]

    public BigDecimal calculateDiscount(Order order) {
        return order.getAmount().subtract(order.getAmount().multiply(BigDecimal.valueOf(0.2)));
    }
}
