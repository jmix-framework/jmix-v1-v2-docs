package com.company.demo.security;

import io.jmix.security.role.annotation.ResourceRole;

@ResourceRole(name = "SecurityAdmin", code = SecurityAdminRole.CODE)
public interface SecurityAdminRole {
    String CODE = "security-admin";
}