package com.pallav.InmemoryTwitter.config.base_roles;

import com.pallav.InmemoryTwitter.config.PrivilegeActionEnum;

import java.util.List;

public enum RoleAdmin {
    INSTANCE;
    public String getName(){
        return  "Admin";
    }
    public List<PrivilegeActionEnum> getPrivileges() {
        return List.of(PrivilegeActionEnum.values());
    }
}