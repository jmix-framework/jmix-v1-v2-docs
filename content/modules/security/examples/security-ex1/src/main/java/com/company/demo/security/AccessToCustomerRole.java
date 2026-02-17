package com.company.demo.security;

import com.company.demo.entity.Customer;
import io.jmix.security.role.annotation.JpqlRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

// tag::role[]
@RowLevelRole(name = "AccessToCustomerRole", code = AccessToCustomerRole.CODE)
public interface AccessToCustomerRole {
    String CODE = "access-to-customer-role";

    @JpqlRowLevelPolicy(entityClass = Customer.class,
            join = "join CustomerAccess ca on ca.customer = {E}",
            where = "ca.employee.user.id = :current_user_id")
    void customer();
}
// end::role[]