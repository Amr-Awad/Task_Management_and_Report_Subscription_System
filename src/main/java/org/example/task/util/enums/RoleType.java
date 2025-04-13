package org.example.task.util.enums;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "User role types")
public enum RoleType {
    @Schema(description = "Standard user role") ROLE_USER("user");

    private final String role;

    RoleType(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }
}