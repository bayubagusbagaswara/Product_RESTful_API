package com.product.restful.entity;

public enum RoleName {

    MANAGER("MANAGER"),
    ADMIN("ADMIN"),
    CUSTOMER("CUSTOMER"),
    USER("USER");

    private final String roleName;

    RoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
