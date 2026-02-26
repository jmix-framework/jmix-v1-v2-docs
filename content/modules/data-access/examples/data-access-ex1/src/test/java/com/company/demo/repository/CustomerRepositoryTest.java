package com.company.demo.repository;

import com.google.common.base.Strings;
import com.company.demo.entity.Customer;
import com.company.demo.entity.CustomerGrade;
import io.jmix.core.DataManager;
import io.jmix.core.EntityStates;
import io.jmix.core.FetchPlan;
import io.jmix.core.FetchPlans;
import io.jmix.core.entity.KeyValueEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class CustomerRepositoryTest {

    // tag::inject[]
    @Autowired
    private CustomerRepository customerRepository;
    // end::inject[]

    @Autowired
    private CustomerRepository2 customerRepository2;

    @Autowired
    DataManager dataManager;

    @Autowired
    DataSource dataSource;

    @Autowired
    FetchPlans fetchPlans;

    @Autowired
    EntityStates entityStates;

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
    void testLoad_ContainingIgnoreCase() {
        List<Customer> customers = customerRepository.findByEmailContainingIgnoreCase("@company.COM");
        assertThat(customers).size().isEqualTo(2);
    }

    @Test
    void testLoad_PagingAndSorting() {
        Page<Customer> page = customerRepository.findByEmailContainingIgnoreCase(
                "@MAIL.COM",
                PageRequest.of(0, 10, Sort.by("name")));
        assertThat(page).size().isEqualTo(10);
    }

    @Test
    void testLoad_FindAllWithFetchPlan() {
        PageRequest pageRequest = PageRequest.of(0, 10, Sort.by("name"));
        FetchPlan fetchPlan = fetchPlans.builder(Customer.class).addAll("name", "encryptedData").build();
        Page<Customer> page = customerRepository.findAll(pageRequest, fetchPlan);

        assertThat(page).size().isEqualTo(10);

        Customer customer = page.getContent().get(0);
        assertThat(entityStates.isLoaded(customer, "name")).isTrue();
        assertThat(entityStates.isLoaded(customer, "encryptedData")).isTrue();
        assertThat(entityStates.isLoaded(customer, "email")).isFalse();
    }

    @Test
    void testLoad_QueryWithFetchPlan() {
        FetchPlan fetchPlan = fetchPlans.builder(Customer.class).addAll("name", "encryptedData").build();
        List<Customer> list = customerRepository.findByEmailContainingIgnoreCase("@MAIL.COM", fetchPlan);

        assertThat(list).isNotEmpty();

        Customer customer = list.get(0);
        assertThat(entityStates.isLoaded(customer, "name")).isTrue();
        assertThat(entityStates.isLoaded(customer, "encryptedData")).isTrue();
        assertThat(entityStates.isLoaded(customer, "email")).isFalse();
    }

    @Test
    void testLoad_QueryWithFetchPlan2() {
        List<Customer> list = customerRepository2.findByEmail("alice@company.com");

        assertThat(list).isNotEmpty();

        Customer customer = list.get(0);
        assertThat(entityStates.isLoaded(customer, "name")).isTrue();
        assertThat(entityStates.isLoaded(customer, "encryptedData")).isTrue();
        assertThat(entityStates.isLoaded(customer, "email")).isFalse();
    }

    @Test
    void testLoad_ExplicitQuery() {
        List<Customer> customers = customerRepository.findCustomersByEmail("(?i)%@company.COM");
        assertThat(customers).size().isEqualTo(2);
    }

    @Test
    void testQueryWithScalars_ListOfKeyValueEntities() {
        List<KeyValueEntity> keyValueEntities = customerRepository2.getCountGroupByGrade();
        assertThat(keyValueEntities).isNotEmpty();
        KeyValueEntity keyValueEntity = keyValueEntities.get(0);
        assertThat((Object) keyValueEntity.getValue("grade")).isInstanceOf(String.class);
        assertThat((Object) keyValueEntity.getValue("count")).isInstanceOf(Long.class);
    }

    @Test
    void testQueryWithScalars_Long() {
        Long count = customerRepository2.getCountByGrade(CustomerGrade.GOLD);
        assertThat(count).isGreaterThan(0L);
    }

    @Test
    void testQueryWithScalars_ListOfString() {
        List<String> names = customerRepository2.getAllNames();
        assertThat(names).isNotEmpty();
        assertThat(names.get(0)).isInstanceOf(String.class);
    }

    @Test
    void testQueryWithScalars_ListOfEnum() {
        List<CustomerGrade> grades = customerRepository2.getAllGrades();
        assertThat(grades).isNotEmpty();
        assertThat(grades.get(0)).isInstanceOf(CustomerGrade.class);
    }
}
