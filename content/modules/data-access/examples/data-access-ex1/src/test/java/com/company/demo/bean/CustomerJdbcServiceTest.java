package com.company.demo.bean;

import com.company.demo.entity.*;
import com.company.demo.test_support.AuthenticatedAsAdmin;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(AuthenticatedAsAdmin.class)
class CustomerJdbcServiceTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    DataSource dataSource;

    @Autowired
    CustomerJdbcService customerJdbcService;

    Customer customer1;
    Order order1;

    @BeforeEach
    void setUp() {
        customer1 = dataManager.create(Customer.class);
        customer1.setName("Alice");
        customer1.setEmail("alice@company.com");
        customer1.setGrade(CustomerGrade.GOLD);

        Product product1 = dataManager.create(Product.class);
        product1.setName("MacBook Pro");
        product1.setPrice(BigDecimal.valueOf(2500));

        order1 = dataManager.create(Order.class);
        order1.setCustomer(customer1);
        order1.setDate(LocalDate.now());
        order1.setAmount(BigDecimal.valueOf(5000));

        OrderLine orderLine1 = dataManager.create(OrderLine.class);
        orderLine1.setOrder(order1);
        orderLine1.setProduct(product1);
        orderLine1.setQuantity(2.0);

        dataManager.save(customer1, order1, orderLine1, product1);
    }

    @AfterEach
    void tearDown() {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        jdbc.execute("delete from ORDER_LINE");
        jdbc.execute("delete from ORDER_");
        jdbc.execute("delete from PRODUCT");
        jdbc.execute("delete from CUSTOMER_GRADE_CHANGE");
        jdbc.execute("delete from CUSTOMER");
    }

    @Test
    void test_getCustomerAmounts() {
        Map<String, BigDecimal> map = customerJdbcService.getCustomerAmounts(CustomerGrade.GOLD);
        assertThat(map).containsKey("Alice");
        assertThat(map.get("Alice")).isEqualByComparingTo("5000");
    }

    @Test
    void test_getCustomerAmountsByJdbcClient() {
        Map<String, BigDecimal> map = customerJdbcService.getCustomerAmountsByJdbcClient(CustomerGrade.GOLD);
        assertThat(map).containsKey("Alice");
        assertThat(map.get("Alice")).isEqualByComparingTo("5000");
    }

    @Test
    @Disabled // cannot run on HSQLDB
    void test_callStoredProcedure() {
        CustomerJdbcService.CustomerStats stats = customerJdbcService.callStoredProcedure(customer1.getId());
        assertThat(stats.totalOrders()).isEqualTo(1);
        assertThat(stats.totalAmount()).isEqualByComparingTo("5000");
    }
}