package com.product.restful.entity;

public enum RoleName {

    C_LEVEL("C_LEVEL"),
    VICE_PRESIDENT("VICE_PRESIDENT"),
    MANAGER("MANAGER"),
    STAFF("STAFF");

    private final String roleName;

    RoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
