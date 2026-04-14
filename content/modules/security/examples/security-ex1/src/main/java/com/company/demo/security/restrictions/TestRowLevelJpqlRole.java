package com.company.demo.security.restrictions;

import com.company.demo.entity.Customer;
import com.company.demo.entity.Order;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

@RowLevelRole(
        name = "Can see Orders with amount < 1000, Can see Customers of their region",
        code = "test-row-level-jpql-role")
public interface TestRowLevelJpqlRole {

    @JpqlRowLevelPolicy(
            entityClass = Customer.class,
            where = "{E}.region = :current_user_region")
    void customer();

    @JpqlRowLevelPolicy(
            entityClass = Order.class,
            where = "{E}.amount < 1000")
    void order();
}
