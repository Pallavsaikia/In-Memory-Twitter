package com.pallav.InmemoryTwitter.core.user.models;

import com.pallav.InmemoryTwitter.in_memory_db.decorators.Index;
import com.pallav.InmemoryTwitter.in_memory_db.decorators.PrimaryKey;
import com.pallav.InmemoryTwitter.in_memory_db.decorators.Unique;
import com.pallav.InmemoryTwitter.in_memory_db.decorators.PriorityComparator;

import java.util.Comparator;

public record User(@PrimaryKey("id")
                   String id,
                   @Index("username")
                   @Unique("username")
                   String username,
                   @Index("email")
                   @Unique("email")
                   String email,
                   String password) {

    @PriorityComparator
    public static Comparator<User> sortByUsername() {
        return Comparator.comparing(User::username);
    }

    @Override
    public String toString() {
        return "User{id='" + id + "', username='" + username + "', email='" + email + "'}";
    }
}
