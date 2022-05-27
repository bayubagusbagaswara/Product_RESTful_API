package com.product.restful.entity;

public enum RoleName {

    MANAGER("MANAGER"),
    SUPER_ADMIN("SUPER_ADMIN"),
    ADMIN("ADMIN"),
    MEMBER("MEMBER");

    private final String roleName;

    RoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
