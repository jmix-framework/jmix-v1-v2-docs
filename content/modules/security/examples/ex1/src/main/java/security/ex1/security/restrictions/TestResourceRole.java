package security.ex1.security.restrictions;

import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.ResourceRole;
import security.ex1.entity.Customer;
import security.ex1.entity.Order;

@ResourceRole(name = "Can read Orders and Customers", code = "test-resource-role")
public interface TestResourceRole {

    @EntityPolicy(
            entityClass = Customer.class,
            actions = EntityPolicyAction.ALL)
    @EntityAttributePolicy(
            entityClass = Customer.class,
            attributes = "*",
            action = EntityAttributePolicyAction.MODIFY)
    void customer();

    @EntityPolicy(
            entityClass = Order.class,
            actions = EntityPolicyAction.ALL)
    @EntityAttributePolicy(
            entityClass = Order.class,
            attributes = "*",
            action = EntityAttributePolicyAction.MODIFY)
    void order();
}
