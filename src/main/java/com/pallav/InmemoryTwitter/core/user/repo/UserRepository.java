package com.pallav.InmemoryTwitter.core.user.repo;

import com.pallav.InmemoryTwitter.core.user.User;
import com.pallav.InmemoryTwitter.in_memory_db.InMemoryStorage;
import org.springframework.stereotype.Repository;

import java.util.Set;

@Repository
public class UserRepository {

    private final InMemoryStorage<User> userStorage;

    public UserRepository() {
        // Now using annotation-based storage initialization
        this.userStorage = new InMemoryStorage<>(User.class);
    }

    // Save a user to the in-memory storage
    public void save(User user) {
        userStorage.saveEntity(user);
    }

    // Find a user by their ID
    public User findById(String id) {
        return userStorage.findEntityById(id);
    }

    // Find a user by their username (using the index)
    public Set<User> findByUsername(String username) {
        return userStorage.findByIndex("username", username);
    }

    // Find a user by their email (using the index)
    public Set<User> findByEmail(String email) {
        return userStorage.findByIndex("email", email);
    }

    // Check if a user with the given username exists
    public boolean existsByUsername(String username) {
        return !findByUsername(username).isEmpty();
    }

    // Check if a user with the given email exists
    public boolean existsByEmail(String email) {
        return !findByEmail(email).isEmpty();
    }
}
