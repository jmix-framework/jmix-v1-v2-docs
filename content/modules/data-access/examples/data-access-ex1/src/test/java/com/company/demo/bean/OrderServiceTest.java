package com.company.demo.bean;

import com.company.demo.entity.Customer;
import com.company.demo.entity.Order;
import com.company.demo.entity.OrderLine;
import com.company.demo.entity.Product;
import com.company.demo.test_support.AuthenticatedAsAdmin;
import io.jmix.core.*;
import io.jmix.data.PersistenceHints;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.support.TransactionTemplate;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import javax.sql.DataSource;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(AuthenticatedAsAdmin.class)
public class OrderServiceTest {

    private static final Logger log = LoggerFactory.getLogger(OrderServiceTest.class);

    @Autowired
    DataManager dataManager;

    @Autowired
    EntityStates entityStates;

    @Autowired
    DataSource dataSource;

    @Autowired
    OrderService orderService;

    @PersistenceContext
    EntityManager entityManager;

    @Autowired
    PlatformTransactionManager transactionManager;

    // tag::fetch-plans[]
    @Autowired
    private FetchPlans fetchPlans;

    // end::fetch-plans[]

    // tag::fetch-plan-repo[]
    @Autowired
    private FetchPlanRepository fetchPlanRepository;

    // end::fetch-plan-repo[]

    private Order order1;

    @BeforeEach
    void setUp() {
        Customer customer1 = dataManager.create(Customer.class);
        customer1.setName("Alice");
        customer1.setEmail("alice@company.com");

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
    void testQuery() {
        List<Order> orders = orderService.loadOrdersByProduct("MacBook Pro");
        assertEquals(1, orders.size());
    }

    @Test
    void testLoadValues() {
        String str = orderService.getCustomerPurchases(LocalDate.now());
        assertEquals("Alice : 5000.00", str);
    }

    @Test
    void testLoadValue() {
        BigDecimal total = orderService.getTotal(LocalDate.now());
        assertEquals(total.compareTo(BigDecimal.valueOf(5000.00)), 0);
    }

    @Test
    void testLoadByCondition() {
        List<Order> orders = orderService.loadByCondition();
        assertEquals(1, orders.size());
    }

    @Test
    void testLoadSorted() {
        List<Order> orders = orderService.loadSorted();
        assertEquals(1, orders.size());
    }

    @Test
    void testCreateOrderWithCustomer() {
        Order order = orderService.createOrderWithCustomer();
        assertNotNull(order.getCustomer());
    }

    @Test
    void testRemoveOrderWithCustomer() {
        Order order = orderService.createOrderWithCustomer();
        assertNotNull(order.getCustomer());

        orderService.removeOrderWithCustomer(order);

        assertFalse(dataManager.load(Order.class).id(order.getId()).optional().isPresent());
        assertFalse(dataManager.load(Customer.class).id(order.getCustomer().getId()).optional().isPresent());
    }

    @Test
    void testHardDelete() {
        Product product1 = dataManager.create(Product.class);
        product1.setName("MacBook Pro");
        dataManager.save(product1);

        orderService.hardDelete(product1);

        assertFalse(dataManager.load(Id.of(product1)).hint(PersistenceHints.SOFT_DELETION, false).optional().isPresent());
    }

    @Test
    void testLazyLoading() {
        String customerName = orderService.getCustomerName(Id.of(order1));
        assertEquals("Alice", customerName);

        List<String> productNames = orderService.getProductNames(Id.of(order1));
        assertEquals(Collections.singletonList("MacBook Pro"), productNames);

        Order order = dataManager.load(Id.of(order1)).one();
        OrderLine orderLine = order.getLines().iterator().next();
        assertSame(order, orderLine.getOrder());
    }

    @Test
    void testLazyLoadingThroughEntityManagerDoesNotWork() {
        TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

        Order order = transactionTemplate.execute(status -> entityManager.find(Order.class, order1.getId()));
        try {
            order.getCustomer().getName();
            fail();
        } catch (Exception e) {
            assertTrue(e instanceof IllegalStateException);
        }
    }

    @Test
    void testFetchPlan_shared() {
        UUID orderId = order1.getId();

        // tag::fetch-plan[]
        Order order = dataManager.load(Order.class).id(orderId).fetchPlan("full").one();
        // end::fetch-plan[]
    }

    @Test
    void testFetchPlan_shared_repo() {
        UUID orderId = order1.getId();

        loadOrder(orderId);
    }

    // tag::fetch-plan-repo-use[]
    private Order loadOrder(UUID orderId) {
        FetchPlan fetchPlan = fetchPlanRepository.getFetchPlan(Order.class, "full");
        return dataManager.load(Order.class).id(orderId).fetchPlan(fetchPlan).one();
    }
    // end::fetch-plan-repo-use[]

    @Test
    void testFetchPlan_FetchPlanBuilder() {
        List<Order> orders = loadOrders();
        assertEquals(1, orders.size());
        Order order = orders.get(0);
        assertEquals(order1, order);
        assertTrue(entityStates.isLoaded(order, "number"));
        assertTrue(entityStates.isLoaded(order, "customer"));
        Customer customer = order.getCustomer();
        assertTrue(entityStates.isLoaded(customer, "name"));
    }

    // tag::fetch-plan-builder[]
    private List<Order> loadOrders() {
        FetchPlan fetchPlan = fetchPlans.builder(Order.class)
                .addFetchPlan(FetchPlan.BASE)
                .add("customer")
                .build();

        return dataManager.load(Order.class).all().fetchPlan(fetchPlan).list();
    }
    // end::fetch-plan-builder[]

    @Test
    void testFetchPlan_FetchPlanBuilder_DataManager() {
        // tag::fetch-plan-builder-dm[]
        List<Order> orders = dataManager.load(Order.class)
                .all()
                .fetchPlan(fpb -> fpb.addFetchPlan(FetchPlan.BASE).add("customer"))
                .list();
        // end::fetch-plan-builder-dm[]

        Order o1 = orders.get(0);
        assertEquals(0, o1.getAmount().compareTo(BigDecimal.valueOf(5000)));
        assertEquals("alice@company.com", o1.getCustomer().getEmail());
    }

    @Test
    void testFetchPlanAndLazyLoading() {
        UUID orderId = order1.getId();
        log.info("load with fetch plan");
        Order order = dataManager.load(Order.class).id(orderId).fetchPlan(FetchPlan.INSTANCE_NAME).one();
        log.info("load lazily");
        assertEquals("Alice", order.getCustomer().getName());
    }
}
