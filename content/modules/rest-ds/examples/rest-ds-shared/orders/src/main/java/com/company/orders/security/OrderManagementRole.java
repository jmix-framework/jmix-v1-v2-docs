package com.company.orders.security;

import com.company.orders.entity.Order;
import com.company.orders.entity.OrderLine;
import com.company.shared.security.ProductsRole;
import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;

@ResourceRole(name = "OrderManagementRole", code = OrderManagementRole.CODE)
public interface OrderManagementRole extends ProductsRole {
    String CODE = "order-management-role";

    @MenuPolicy(menuIds = {"Order_.list", "shared_ProductCategory.list", "shared_Product.list"})
    @ViewPolicy(viewIds = {"Order_.list", "shared_ProductCategory.list", "shared_Product.list", "Order_.detail", "OrderLine.detail", "shared_Product.detail", "shared_ProductCategory.detail"})
    void screens();

    @EntityAttributePolicy(entityClass = Order.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = Order.class, actions = EntityPolicyAction.ALL)
    void order();

    @EntityAttributePolicy(entityClass = OrderLine.class, attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @EntityPolicy(entityClass = OrderLine.class, actions = EntityPolicyAction.ALL)
    void orderLine();
}