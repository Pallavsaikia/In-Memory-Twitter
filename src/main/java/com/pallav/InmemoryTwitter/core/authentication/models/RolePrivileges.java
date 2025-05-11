package com.pallav.InmemoryTwitter.core.authentication.models;

import com.pallav.InmemoryTwitter.in_memory_db.decorators.Index;
import com.pallav.InmemoryTwitter.in_memory_db.decorators.PrimaryKey;
import com.pallav.InmemoryTwitter.in_memory_db.decorators.PriorityComparator;
import com.pallav.InmemoryTwitter.in_memory_db.decorators.Unique;

import java.util.Comparator;

public record RolePrivileges(@PrimaryKey("id")
                    String id,
                    String role_id,
                    String privilege_id){

    @PriorityComparator
    public static Comparator<RolePrivileges> sortByName() {
        return Comparator.comparing(RolePrivileges::id);
    }

    @Override
    public String toString() {
        return "RolePrivileges{id='" + id + "', role_id='" + role_id + " , privilege_id='" + privilege_id + "'}";
    }
}