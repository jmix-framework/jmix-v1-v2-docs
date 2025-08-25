package com.company.demo;

import com.company.demo.entity.Customer;
import com.company.demo.entity.Order;
import com.company.demo.test_support.AuthenticatedAsAdmin;
import io.jmix.core.DataManager;
import io.jmix.core.security.SystemAuthenticator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(properties = {
        "logging.level.io.jmix = debug",
        "logging.level.eclipselink.logging.sql = debug"
})
@ExtendWith(AuthenticatedAsAdmin.class)
@ActiveProfiles("test")
public class RelatedEntityLoadingTest {

    @Autowired
    SystemAuthenticator authenticator;

    @Autowired
    DataManager dataManager;

    @Autowired
    DataSource dataSource;

    private UUID customerId;
    private UUID orderId;

    @BeforeEach
    void setUp() {
        authenticator.withSystem(() -> {
            Customer customer = dataManager.create(Customer.class);
            customer.setName("customer-1");
            customer.setRegion("UK");

            Order order = dataManager.create(Order.class);
            order.setCustomer(customer);
            order.setDate(LocalDate.now());
            order.setAmount(BigDecimal.TEN);

            dataManager.save(order, customer);

            customerId = customer.getId();
            orderId = order.getId();
            return null;
        });
    }

    @AfterEach
    void tearDown() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcTemplate.update("delete from ORDER_ where ID=?", orderId);
        jdbcTemplate.update("delete from CUSTOMER where ID=?", customerId);
    }

    @Test
    void rootIsNotLoaded() {
        authenticator.withUser(
                "test1",
                () -> {
                    Optional<Customer> optionalCustomer = dataManager.load(Customer.class).id(customerId).optional();
                    assertFalse(optionalCustomer.isPresent());
                    return null;
                }
        );
    }

    @Test
    void jpql_relatedEntityIsLoadedLazily() {
        authenticator.withUser(
                "test1",
                () -> {
                    Order order = dataManager.load(Order.class).id(orderId).one();
                    assertNotNull(order.getCustomer());
                    return null;
                }
        );
    }

    @Test
    void jpql_relatedEntityIsLoadedEagerly() {
        authenticator.withUser(
                "test1",
                () -> {
                    Order order = dataManager.load(Order.class).id(orderId).fetchPlanProperties("customer.name").one();
                    assertNotNull(order.getCustomer());
                    return null;
                }
        );
    }

    @Test
    void jpqlAndPredicate_relatedEntityIsNotLoadedLazily() {
        authenticator.withUser(
                "test2",
                () -> {
                    Order order = dataManager.load(Order.class).id(orderId).one();
                    assertNull(order.getCustomer());
                    return null;
                }
        );
    }

    @Test
    void jpqlAndPredicate_relatedEntityIsNotLoadedEagerly() {
        authenticator.withUser(
                "test2",
                () -> {
                    Order order = dataManager.load(Order.class).id(orderId).fetchPlanProperties("customer.name").one();
                    assertNull(order.getCustomer());
                    return null;
                }
        );
    }

    @Test
    void docExamples() {
        authenticator.withUser(
                "test1",
                () -> {
                    // tag::data-access-checks-1[]
                    // order is loaded only if it satisfies JPQL policies on the Order entity
                    Order order = dataManager.load(Order.class).id(orderId).one();
                    // related customer is loaded regardless of JPQL policies on the Customer entity
                    assert order.getCustomer() != null;
                    // end::data-access-checks-1[]
                    return null;
                }
        );

        authenticator.withUser(
                "test2",
                () -> {
                    // tag::data-access-checks-2[]
                    // order is loaded only if it satisfies constraints on the Order entity
                    Order order = dataManager.load(Order.class).id(orderId).one();
                    // related customer is not null only if it satisfies predicate policies on the Customer entity
                    if (order.getCustomer() != null) { /*...*/ }
                    // end::data-access-checks-2[]
                    return null;
                }
        );

        authenticator.withUser(
                "test1",
                () -> {
                    // tag::data-access-checks-3[]
                    // loading Order
                    Order order = dataManager.load(Order.class).id(orderId).one();
                    // related customer is loaded even if the user has no permission to read the Customer entity
                    assert order.getCustomer() != null;
                    // end::data-access-checks-3[]
                    return null;
                }
        );


    }
}
