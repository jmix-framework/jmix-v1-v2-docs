package com.company.demo.security;

import io.jmix.security.role.annotation.ResourceRole;

@ResourceRole(name = "Supervisor", code = SupervisorRole.CODE)
public interface SupervisorRole {
    String CODE = "supervisor";
}