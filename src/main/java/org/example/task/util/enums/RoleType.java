package org.example.task.util.enums;

public enum RoleType {
    ROLE_USER("user");

    private final String role;

    RoleType(String role) {
        this.role = role;
    }


    public String getRole() {
        return role;
    }

}
