package com.pallav.InmemoryTwitter.config.base_roles;

import com.pallav.InmemoryTwitter.config.PrivilegeActionEnum;

import java.util.List;

/**
 * This is not required in a deployed application.
 * The Admin should be able to manage Custom roles and privileges attached to it.
 * This class is to bypass creation of role privileges every time
 */
public enum RoleBasic {
    INSTANCE;
    public String getName(){
        return  "Basic";
    }
    public List<String> getPrivileges() {
        return List.of(PrivilegeActionEnum.CREATE_TWEET.getValue(),
                PrivilegeActionEnum.DELETE_TWEET.getValue(),
                PrivilegeActionEnum.DELETE_TWEET.getValue());
    }
}
