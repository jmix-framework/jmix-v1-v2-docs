package com.company.demo.security.restrictions;

import com.company.demo.entity.Customer;
import com.company.demo.entity.User;
import io.jmix.core.security.CurrentAuthentication;
import io.jmix.security.model.RowLevelPolicyAction;
import io.jmix.security.role.annotation.PredicateRowLevelPolicy;
import io.jmix.security.role.annotation.RowLevelRole;

import java.util.function.Predicate;

@RowLevelRole(
        name = "Can see Orders with amount < 1000, Can see Customers of their region",
        code = "test-row-level-predicate-role")
public interface TestRowLevelPredicateRole {

    @PredicateRowLevelPolicy(
            entityClass = Customer.class,
            actions = {RowLevelPolicyAction.READ})
    default Predicate<Customer> customerOfMyRegion(CurrentAuthentication currentAuthentication) {
        return customer -> customer.getRegion() != null
                && customer.getRegion().equals(((User) currentAuthentication.getUser()).getRegion());
    }
}
