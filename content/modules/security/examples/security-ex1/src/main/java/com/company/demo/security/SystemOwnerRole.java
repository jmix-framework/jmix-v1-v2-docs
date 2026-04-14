package com.company.demo.security;

import io.jmix.security.role.annotation.ResourceRole;

// tag::combined-role[]
@ResourceRole(name = "SystemOwner", code = SystemOwnerRole.CODE)
public interface SystemOwnerRole extends BasicEmployeeRole, ManagerRole, SupervisorRole {
    String CODE = "system-owner";

    // System owner's policies go here

}

// end::combined-role[]