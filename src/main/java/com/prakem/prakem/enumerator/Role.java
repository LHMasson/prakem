package com.prakem.prakem.enumerator;

import com.prakem.prakem.exception.InvalidRoleException;

public enum Role {
    ADMIN,
    USER;

    public static Role fromString(String value) {
        for (Role role : Role.values()) {
            if (role.name().equalsIgnoreCase(value)) {
                return role;
            }
        }
        throw new InvalidRoleException("Invalid role: " + value);
    }
}
