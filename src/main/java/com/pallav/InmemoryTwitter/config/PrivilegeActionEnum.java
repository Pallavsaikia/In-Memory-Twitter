package com.pallav.InmemoryTwitter.config;

public enum PrivilegeActionEnum {
    VIEW_TWEET("tweet:get"),
    DELETE_TWEET("tweet:delete"),
    CREATE_TWEET("tweet:post"),
    CREATE_ROLES("roles:create");
    private final String value;

    PrivilegeActionEnum(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    // Optional: find enum by value
    public static PrivilegeActionEnum fromValue(String value) {
        for (PrivilegeActionEnum p : PrivilegeActionEnum.values()) {
            if (p.value.equalsIgnoreCase(value)) {
                return p;
            }
        }
        throw new IllegalArgumentException("Unknown privilege: " + value);
    }
}
