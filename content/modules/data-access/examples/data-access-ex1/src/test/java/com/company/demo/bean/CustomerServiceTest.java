package com.company.demo.bean;

import com.company.demo.test_support.AuthenticatedAsAdmin;
import com.google.common.base.Strings;
import com.company.demo.entity.Customer;
import com.company.demo.entity.CustomerGrade;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(AuthenticatedAsAdmin.class)
public class CustomerServiceTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    CustomerService customerService;

    @Autowired
    DataSource dataSource;

    Customer customer1;
    Customer customer2;

    @BeforeEach
    void setUp() {
        customer1 = dataManager.create(Customer.class);
        customer1.setName("Alice");
        customer1.setEmail("alice@company.com");
        customer1.setGrade(CustomerGrade.PLATINUM);

        customer2 = dataManager.create(Customer.class);
        customer2.setName("Bob");
        customer2.setEmail("bob@company.com");
        customer2.setGrade(CustomerGrade.GOLD);

        dataManager.save(customer1, customer2);

        for (int i = 1; i <= 50; i++) {
            Customer customer = dataManager.create(Customer.class);
            customer.setName("cust-" + Strings.padStart(String.valueOf(i), 2, '0'));
            customer.setEmail("cust-" + i + "@mail.com");
            customer.setGrade(i > 40 ? CustomerGrade.PLATINUM : (i > 30 ? CustomerGrade.GOLD : CustomerGrade.BRONZE));
            dataManager.save(customer);
        }
    }

    @AfterEach
    void tearDown() {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        jdbc.execute("delete from CUSTOMER_GRADE_CHANGE");
        jdbc.execute("delete from CUSTOMER");
    }

    @Test
    void testLoad() {
        Customer customer = customerService.loadById(customer1.getId());
        assertEquals(customer1, customer);

        customer = customerService.loadByGenericId(Id.of(customer1));
        assertEquals(customer1, customer);

        customer = customerService.loadOrCreate(customer1.getId());
        assertEquals(customer1, customer);

        customer = customerService.loadOrCreate(UUID.randomUUID());
        assertNotEquals(customer1, customer);
    }

    @Test
    void testLoadList() {
        List<Customer> customers;

        customers = customerService.loadByIds(customer1.getId(), customer2.getId());
        assertEquals(2, customers.size());

        customers = customerService.loadAll();
        assertEquals(52, customers.size());

        customers = customerService.loadByQuery();
        assertEquals(1, customers.size());

        customers = customerService.loadByCondition();
        assertEquals(2, customers.size());

        customers = customerService.loadByConditions();
        assertEquals(1, customers.size());

        customers = customerService.loadByFullQuery();
        assertEquals(1, customers.size());

        customers = customerService.loadPageByQuery(0, 10);
        assertEquals(10, customers.size());

        customers = customerService.loadSorted();
        assertEquals(2, customers.size());
        assertEquals(customer1, customers.get(0));
        assertEquals(customer2, customers.get(1));
    }

    @Test
    void testSave() {
        customer1.setGrade(CustomerGrade.BRONZE);
        Customer customer = customerService.saveCustomer(customer1);
        assertEquals(CustomerGrade.BRONZE, customer.getGrade());
        assertNotSame(customer1, customer);

        Customer newCustomer = dataManager.create(Customer.class);
        newCustomer.setName("Gomer");
        newCustomer.setGrade(CustomerGrade.GOLD);
        customer = customerService.saveCustomer(newCustomer);
        assertNotSame(newCustomer, customer); // we always reload entity after save
    }

    @Test
    void testRemove() {
        customerService.removeCustomer(customer1);
        assertFalse(dataManager.load(Customer.class).id(customer1.getId()).optional().isPresent());

        customerService.removeCustomer(customer2.getId());
        assertFalse(dataManager.load(Customer.class).id(customer2.getId()).optional().isPresent());
    }

    @Test
    void testRemoveList() {
        customerService.removeCustomers(Arrays.asList(customer1, customer2));
        assertFalse(dataManager.load(Customer.class).id(customer1.getId()).optional().isPresent());
        assertFalse(dataManager.load(Customer.class).id(customer2.getId()).optional().isPresent());
    }
}
