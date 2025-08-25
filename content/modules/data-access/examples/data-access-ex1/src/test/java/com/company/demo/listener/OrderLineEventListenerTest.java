package com.company.demo.listener;

import com.company.demo.entity.Order;
import com.company.demo.entity.OrderLine;
import com.company.demo.entity.Product;
import com.company.demo.test_support.AuthenticatedAsAdmin;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(AuthenticatedAsAdmin.class)
public class OrderLineEventListenerTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    DataSource dataSource;

    @AfterEach
    void tearDown() {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        jdbc.execute("delete from ORDER_LINE");
        jdbc.execute("delete from ORDER_");
        jdbc.execute("delete from PRODUCT");
    }

    @Test
    void test() {
        Product product1 = dataManager.create(Product.class);
        product1.setName("MacBook Pro");
        product1.setPrice(BigDecimal.valueOf(2500));

        Product product2 = dataManager.create(Product.class);
        product2.setName("iPad");
        product2.setPrice(BigDecimal.valueOf(1000));

        Order order1 = dataManager.create(Order.class);
        order1.setDate(LocalDate.now());

        dataManager.save(order1, product1, product2);

        OrderLine orderLine1 = dataManager.create(OrderLine.class);
        orderLine1.setOrder(order1);
        orderLine1.setProduct(product1);
        orderLine1.setQuantity(2.0);

        dataManager.save(orderLine1);

        assertEquals(0, BigDecimal.valueOf(5000).compareTo(dataManager.load(Id.of(order1)).one().getAmount()));

        OrderLine orderLine2 = dataManager.create(OrderLine.class);
        orderLine2.setOrder(order1);
        orderLine2.setProduct(product2);
        orderLine2.setQuantity(3.0);

        dataManager.save(orderLine2);

        assertEquals(0, BigDecimal.valueOf(8000).compareTo(dataManager.load(Id.of(order1)).one().getAmount()));
    }


}
