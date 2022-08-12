package com.product.restful.entity.enumerator;

public enum StatusRecord {
    ACTIVE("ACTIVE"),
    INACTIVE("INACTIVE");

    private final String statusRecord;

    StatusRecord(String statusRecord) {
        this.statusRecord = statusRecord;
    }

    public String getStatusRecord() {
        return this.statusRecord;
    }
}
