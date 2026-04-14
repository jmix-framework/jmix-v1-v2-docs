package com.company.demo.security;

import com.company.demo.entity.Order;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;


// tag::role[]
@RowLevelRole(name = "Can see orders of active customers",
        code = "active-customers-role")
public interface ActiveCustomersRole {
    // end::role[]

    //tag::with-join[]
    @JpqlRowLevelPolicy(entityClass = Order.class,
            join = "join {E}.customer c",
            where = "c.active = TRUE")
    void order();
    //end::with-join[]

    //tag::with-path-expression[]
    @JpqlRowLevelPolicy(entityClass = Order.class,
            where = "{E}.customer.active = TRUE")
    void order1();
    //end::with-path-expression[]

    // tag::role[]
}
// end::role[]