package com.company.demo.security;

import io.jmix.security.model.EntityAttributePolicyAction;
import io.jmix.security.model.EntityPolicyAction;
import io.jmix.security.role.annotation.EntityAttributePolicy;
import io.jmix.security.role.annotation.EntityPolicy;
import io.jmix.security.role.annotation.SpecificPolicy;
import io.jmix.securityflowui.role.annotation.MenuPolicy;
import io.jmix.securityflowui.role.annotation.ViewPolicy;
// tag::role[]
import io.jmix.security.role.annotation.ResourceRole;
import io.jmix.security.model.SecurityScope;

@ResourceRole( // <1>
    name = "Full Access", // <2>
    code = FullAccessRole.CODE, // <3>
    scope = SecurityScope.UI) // <4>
public interface FullAccessRole {

    String CODE = "system-full-access";
    
    // end::role[]

    @EntityPolicy(entityName = "*", actions = {EntityPolicyAction.ALL})
    @EntityAttributePolicy(entityName = "*", attributes = "*", action = EntityAttributePolicyAction.MODIFY)
    @ViewPolicy(viewIds = "*")
    @MenuPolicy(menuIds = "*")
    @SpecificPolicy(resources = "*")
    // tag::role[]
    void fullAccess(); // <5>
    // end::role[]
}