package com.company.demo.bean;

import com.company.demo.entity.Foo;
import com.company.demo.test_support.AuthenticatedAsAdmin;
import io.jmix.core.DataManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(AuthenticatedAsAdmin.class)
class FooJdbcServiceTest {

    @Autowired
    DataManager dataManager;

    @Autowired
    FooJdbcService fooJdbcService;

    Foo foo1;

    @BeforeEach
    void setUp() {
        foo1 = dataManager.create(Foo.class);
        foo1.setName("foo1");
        dataManager.save(foo1);
    }

    @AfterEach
    void tearDown() {
        dataManager.remove(foo1);
    }

    @Test
    void test_loadFooNames() {
        List<String> names = fooJdbcService.loadFooNames();
        assertThat(names).containsExactly("foo1");
    }

    @Test
    void test_loadFooNamesByJdbcClient() {
        List<String> names = fooJdbcService.loadFooNamesByJdbcClient();
        assertThat(names).containsExactly("foo1");
    }
}