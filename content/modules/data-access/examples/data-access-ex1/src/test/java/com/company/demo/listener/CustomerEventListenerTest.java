package com.company.demo.listener;

import com.company.demo.entity.*;
import com.company.demo.test_support.AuthenticatedAsAdmin;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import io.jmix.core.Sort;
import io.jmix.core.querycondition.PropertyCondition;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(AuthenticatedAsAdmin.class)
public class CustomerEventListenerTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    DataSource dataSource;

    @AfterEach
    void tearDown() {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        jdbc.execute("delete from CUSTOMER_GRADE_CHANGE");
        jdbc.execute("delete from CUSTOMER");
        jdbc.execute("delete from SAMPLE_SENT_EMAIL");
    }

    @Test
    void testSavingAndLoading() {
        Customer customer = dataManager.create(Customer.class);
        customer.setName("Gomer");
        customer.setSensitiveData("something");

        Customer savedCustomer = dataManager.save(customer);
        assertEquals("{encrypted}something", savedCustomer.getEncryptedData());

        Customer loadedCustomer = dataManager.load(Id.of(customer)).one();
        assertEquals("{encrypted}something", loadedCustomer.getEncryptedData());
        assertEquals("something", loadedCustomer.getSensitiveData());
    }

    @Test
    void testChanged() {
        Customer customer = dataManager.create(Customer.class);
        customer.setName("Gomer");
        customer.setEmail("gomer@simpsons.com");
        customer.setGrade(CustomerGrade.BRONZE);
        Customer customer1 = dataManager.save(customer);

        List<CustomerGradeChange> changes = dataManager.load(CustomerGradeChange.class)
                .condition(PropertyCondition.equal("customer", customer))
                .sort(Sort.by("createdDate"))
                .list();
        assertEquals(1, changes.size());
        assertNull(changes.get(0).getOldGrade());
        assertEquals(CustomerGrade.BRONZE, changes.get(0).getNewGrade());

        customer1.setGrade(CustomerGrade.GOLD);
        Customer customer2 = dataManager.save(customer1);

        changes = dataManager.load(CustomerGradeChange.class)
                .condition(PropertyCondition.equal("customer", customer))
                .sort(Sort.by("createdDate"))
                .list();
        assertEquals(2, changes.size());
        assertEquals(CustomerGrade.BRONZE, changes.get(1).getOldGrade());
        assertEquals(CustomerGrade.GOLD, changes.get(1).getNewGrade());

        List<SentEmail> emails = dataManager.load(SentEmail.class)
                .condition(PropertyCondition.equal("emailedTo", customer.getEmail()))
                .sort(Sort.by("createdDate"))
                .list();
        assertEquals(2, emails.size());
    }
}
