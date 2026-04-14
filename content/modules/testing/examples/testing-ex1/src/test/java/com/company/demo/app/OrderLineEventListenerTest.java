package com.company.demo.app;

import com.company.demo.entity.Order;
import com.company.demo.entity.OrderLine;
import com.company.demo.entity.Product;
import com.company.demo.test_support.AuthenticatedAsAdmin;
import io.jmix.core.DataManager;
import io.jmix.core.Id;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

// tag::order-line-event-listener-test[]
@SpringBootTest
@ExtendWith(AuthenticatedAsAdmin.class)
public class OrderLineEventListenerTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    DataSource dataSource;
    private Product mac;
    private Product ipad;

    @BeforeEach
    void initProducts() {
        mac = dataManager.create(Product.class);
        mac.setName("MacBook Pro");
        mac.setPrice(BigDecimal.valueOf(2500));

        ipad = dataManager.create(Product.class);
        ipad.setName("iPad");
        ipad.setPrice(BigDecimal.valueOf(1000));

        dataManager.save(mac, ipad);
    }

    @Test
    void given_orderWithTwoOrderLines_expect_amountIsSumOfOrderLineValues() {

        // given
        Order order = dataManager.create(Order.class);
        order.setDate(LocalDate.now());
        dataManager.save(order);

        // and
        OrderLine twoMacs = dataManager.create(OrderLine.class);
        twoMacs.setOrder(order);
        twoMacs.setProduct(mac);
        twoMacs.setQuantity(2.0);

        // and
        OrderLine threeIpads = dataManager.create(OrderLine.class);
        threeIpads.setOrder(order);
        threeIpads.setProduct(ipad);
        threeIpads.setQuantity(3.0);

        // when
        dataManager.save(twoMacs, threeIpads);

        Order reloadedOrder = dataManager.load(Id.of(order)).one();

        // then
        assertThat(reloadedOrder.getAmount())
                .isEqualByComparingTo(BigDecimal.valueOf(3 * 1000 + 2 * 2500));
    }

    // ...
    // end::order-line-event-listener-test[]

    @AfterEach
    void tearDown() {
        JdbcTemplate jdbc = new JdbcTemplate(dataSource);
        jdbc.execute("delete from ORDER_LINE");
        jdbc.execute("delete from ORDER_");
        jdbc.execute("delete from PRODUCT");
    }

}
