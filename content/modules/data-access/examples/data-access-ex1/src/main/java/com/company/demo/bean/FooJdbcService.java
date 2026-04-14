package com.company.demo.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;

@Component
public class FooJdbcService {

    // tag::inject-ds[]
    @Autowired
    @Qualifier("db1DataSource")
    private DataSource db1DataSource;

    // end::inject-ds[]

    // tag::jdbc-template[]
    public List<String> loadFooNames() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(db1DataSource);
        return jdbcTemplate.queryForList("select NAME from SAMPLE_FOO", String.class);
    }
    // end::jdbc-template[]

    // tag::jdbc-client[]
    public List<String> loadFooNamesByJdbcClient() {
        JdbcClient jdbcClient = JdbcClient.create(db1DataSource);
        return jdbcClient.sql("select NAME from SAMPLE_FOO").query(String.class).list();
    }
    // end::jdbc-client[]
}
