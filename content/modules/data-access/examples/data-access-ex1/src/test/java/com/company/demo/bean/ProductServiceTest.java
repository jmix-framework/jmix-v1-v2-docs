package com.company.demo.bean;

import com.company.demo.entity.Product;
import com.company.demo.test_support.AuthenticatedAsAdmin;
import io.jmix.core.DataManager;
import io.jmix.core.entity.KeyValueEntity;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.jdbc.JdbcTestUtils;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(AuthenticatedAsAdmin.class)
public class ProductServiceTest {

    @Autowired
    DataManager dataManager;
    @Autowired
    JdbcTemplate jdbc;

    @Autowired
    ProductService productService;

    private Product p1;
    private Product p2;
    private Product p3;
    private Product p4;

    @BeforeEach
    void setUp() {
        cleanup();

        p1 = dataManager.create(Product.class);
        p1.setName("p1");
        p1.setTags(List.of("t11", "t12"));
        p2 = dataManager.create(Product.class);
        p2.setName("p2");
        p2.setTags(List.of("t21", "t22"));
        p3 = dataManager.create(Product.class);
        p3.setName("p3");
        p3.setTags(List.of("a1", "a2"));
        p4 = dataManager.create(Product.class);
        p4.setName("p4");

        dataManager.saveWithoutReload(p1, p2, p3, p4);
    }

    @AfterEach
    void cleanup() {
        JdbcTestUtils.deleteFromTables(jdbc, "PRODUCT_TAGS", "PRODUCT");
    }

    @Test
    void testQueryWithEquals() {
        List<Product> list = productService.getProductsByTag("t11");
        assertThat(list).containsExactly(p1);
    }

    @Test
    void testQueryWithLike() {
        List<Product> list = productService.getProductsByTagLike("t");
        assertThat(list).containsOnly(p1, p2);
    }

    @Test
    void testQueryByEmpty() {
        List<Product> list = productService.getProductsWithoutTags();
        assertThat(list).containsExactly(p4);
    }

    @Test
    void testLoadValues() {
        List<KeyValueEntity> kvEntities = productService.getProductNameTagPairs(p1);
        assertThat(kvEntities).allMatch(kvEntity ->
                kvEntity.getValue("name") instanceof String && kvEntity.getValue("tag") instanceof String);
    }

    @Test
    void testLoadValue() {
        List<String> tags = productService.getProductTags(p1);
        assertThat(tags).containsOnly("t11", "t12");
    }

    @Test
    void testCondition() {
        List<Product> products = productService.getProductsByTagUsingCondition("t11");
        assertThat(products).containsExactly(p1);
    }

    @Test
    void testQueryByEmptyUsingCondition() {
        List<Product> list = productService.getProductsWithoutTagsUsingCondition();
        assertThat(list).containsExactly(p4);
    }
}
