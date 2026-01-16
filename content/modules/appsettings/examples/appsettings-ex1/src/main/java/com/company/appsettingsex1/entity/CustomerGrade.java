package com.company.appsettingsex1.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum CustomerGrade implements EnumClass<String> {

    BRONZE("B"),
    GOLD("G"),
    PLATINUM("P");

    private final String id;

    CustomerGrade(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static CustomerGrade fromId(String id) {
        for (CustomerGrade at : CustomerGrade.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}