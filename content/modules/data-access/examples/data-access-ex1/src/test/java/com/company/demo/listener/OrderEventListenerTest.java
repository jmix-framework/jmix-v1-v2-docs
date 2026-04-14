package com.company.demo.listener;

import com.company.demo.entity.Order;
import com.company.demo.test_support.AuthenticatedAsAdmin;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(AuthenticatedAsAdmin.class)
public class OrderEventListenerTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    DataSource dataSource;

    @AfterEach
    void tearDown() {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        jdbc.execute("delete from ORDER_");
    }

    @Test
    void test() {
        Order order1 = dataManager.create(Order.class);
        order1.setDate(LocalDate.now());
        Order savedOrder = dataManager.save(order1);

        assertTrue(Integer.parseInt(savedOrder.getNumber()) > 0);
    }
}
