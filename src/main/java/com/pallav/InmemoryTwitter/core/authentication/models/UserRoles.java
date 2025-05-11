package com.pallav.InmemoryTwitter.core.authentication.models;
import com.pallav.InmemoryTwitter.in_memory_db.decorators.Index;
import com.pallav.InmemoryTwitter.in_memory_db.decorators.PrimaryKey;
import com.pallav.InmemoryTwitter.in_memory_db.decorators.PriorityComparator;

import java.util.Comparator;

public record UserRoles(@PrimaryKey("id")
                             String id,
                             @Index("role_id")
                             String role_id,
                             @Index("user_id")
                             String user_id){

    @PriorityComparator
    public static Comparator<UserRoles> sortByID() {
        return Comparator.comparing(UserRoles::id);
    }

    @Override
    public String toString() {
        return "RolePrivileges{id='" + id + "', role_id='" + role_id + " , user_id='" + user_id + "'}";
    }
}