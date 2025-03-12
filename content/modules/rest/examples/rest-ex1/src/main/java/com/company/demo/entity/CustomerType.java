package com.company.demo.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import javax.annotation.Nullable;


public enum CustomerType implements EnumClass<String> {

    BUSINESS("BUSINESS"),
    PRIVATE("PRIVATE");


    private String id;

    CustomerType(String value) {
        this.id = value;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static CustomerType fromId(String id) {
        for (CustomerType at : CustomerType.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}