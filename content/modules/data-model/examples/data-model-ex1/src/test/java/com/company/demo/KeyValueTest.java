package com.company.demo;

import com.company.demo.entity.Customer;
import com.company.demo.entity.Order;
import com.company.demo.test_support.AuthenticatedAsAdmin;
import io.jmix.core.DataManager;
import io.jmix.core.entity.KeyValueEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@ExtendWith(AuthenticatedAsAdmin.class)
public class KeyValueTest {

    @Autowired
    DataManager dataManager;
    private Customer customer1;
    private Customer customer2;
    private Order order1;
    private Order order2;

    @BeforeEach
    void setUp() {
        customer1 = dataManager.create(Customer.class);
        customer1.setName("cust 1");

        customer2 = dataManager.create(Customer.class);
        customer2.setName("cust 2");

        dataManager.save(customer1, customer2);

        order1 = dataManager.create(Order.class);
        order1.setNum("1");
        order1.setCustomer(customer1);
        order1.setAmount(BigDecimal.TEN);

        order2 = dataManager.create(Order.class);
        order2.setNum("2");
        order2.setCustomer(customer2);
        order2.setAmount(new BigDecimal("20"));

        dataManager.save(order1, order2);
    }

    @AfterEach
    void tearDown() {
        dataManager.remove(order1, order2);
        dataManager.remove(customer1, customer2);
    }

    @Test
    void test() {
        // tag::load[]
        List<KeyValueEntity> entities = dataManager.loadValues(
                    "select e.customer, sum(e.amount) from Order_ e group by e.customer")
                .properties("customer", "total")
                .list();
        // end::load[]
        assertEquals(2, entities.size());

        // tag::get-value[]
        for (KeyValueEntity entity : entities) {
            Customer customer = entity.getValue("customer");
            BigDecimal totalAmount = entity.getValue("total");
            // ...
        }
        // end::get-value[]
    }
}
