package com.company.sample.entity;

import io.jmix.core.metamodel.datatype.EnumClass;

import org.springframework.lang.Nullable;


public enum Room implements EnumClass<String> {

    SENIOR("senior"),
    JUNIOR("junior"),
    MIDDLE("middle");

    private final String id;

    Room(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Nullable
    public static Room fromId(String id) {
        for (Room at : Room.values()) {
            if (at.getId().equals(id)) {
                return at;
            }
        }
        return null;
    }
}