package com.company.demo.security;

import com.company.demo.entity.Customer;
import com.company.demo.entity.CustomerDetail;
import com.company.demo.entity.Order;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;

@ResourceRole(name = "BasicEmployee", code = BasicEmployeeRole.CODE)
public interface BasicEmployeeRole extends UiMinimalRole {
    String CODE = "basic-employee";

    @EntityAttributePolicy(entityClass = Customer.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Customer.class, actions = EntityPolicyAction.ALL)
    void customer();

    @EntityAttributePolicy(entityClass = CustomerDetail.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = CustomerDetail.class, actions = EntityPolicyAction.ALL)
    void customerDetail();

    @EntityAttributePolicy(entityClass = Order.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Order.class, actions = EntityPolicyAction.ALL)
    void order();
}