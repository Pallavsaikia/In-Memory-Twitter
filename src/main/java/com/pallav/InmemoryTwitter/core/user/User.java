package com.pallav.InmemoryTwitter.core.user;

import com.pallav.InmemoryTwitter.in_memory_db.decorators.Index;
import com.pallav.InmemoryTwitter.in_memory_db.decorators.PrimaryKey;
import com.pallav.InmemoryTwitter.in_memory_db.decorators.Unique;
import com.pallav.InmemoryTwitter.in_memory_db.decorators.PriorityComparator;

import java.util.Comparator;

public class User {

    private final String id;
    private final String username;
    private final String email;
    private final String password;

    public User(String id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    @PrimaryKey("id")
    public String getId() {
        return id;
    }

    @Index("username")
    @Unique("username")
    public String getUsername() {
        return username;
    }

    @Index("email")
    @Unique("email")
    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    @PriorityComparator
    public static Comparator<User> sortByUsername() {
        return Comparator.comparing(User::getUsername);
    }

    @Override
    public String toString() {
        return "User{id='" + id + "', username='" + username + "', email='" + email + "'}";
    }
}
