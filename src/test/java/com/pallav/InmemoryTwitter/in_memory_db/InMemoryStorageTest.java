package com.pallav.InmemoryTwitter.in_memory_db;

import com.pallav.InmemoryTwitter.core.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class InMemoryStorageTest {

    @Test
    public void testUserStorage() {
        // Create index map with username and email
        Map<String, Function<User, String>> indexMap = new HashMap<>();
        indexMap.put("username", User::getUsername);
        indexMap.put("email", User::getEmail);

        // Initialize InMemoryStorage with User class and indexes
        InMemoryStorage<User> userStorage = new InMemoryStorage<>(User.class);

        // Create User objects
        User u1 = new User("1", "pallav", "pallav@example.com", "Adaa");
        User u2 = new User("2", "john", "john@example.com", "Adaa");
        User u3 = new User("3", "pallav", "p2@example.com", "Adaa");

        // Save users to storage
        userStorage.saveEntity(u1);
        userStorage.saveEntity(u2);

        // Validate that adding another user with the same username "pallav" throws an exception
        assertThrows(RuntimeException.class, () -> userStorage.saveEntity(u3));


        User foundUser = userStorage.findEntityById("2");
        assertThat(foundUser).isNotNull();
        assertThat(foundUser.getId()).isEqualTo("2");
//
//        // Test finding users by username (using the index)
        Set<User> pallavUsers = userStorage.findByIndex("username", "pallav");

        assertThat(pallavUsers).hasSize(1);  // Only one user with username "pallav"

        // Test finding the user with the highest priority (based on comparator)
        User priorityUser = userStorage.findByPriority();
        assertThat(priorityUser.getId()).isEqualTo("2");  // User with ID=2 should be first based on comparator (sorted by username)

        // Test deleting a user by ID
        userStorage.deleteEntity("1");

        // Test that the user with ID=1 is removed
        Set<User> remaining = userStorage.findByIndex("username", "pallav");
        assertThat(remaining).hasSize(0);  // No users with username "pallav" left after deletion

        // Test finding the user with the highest priority after deletion
        User priorityUserAfterDelete = userStorage.findByPriority();
        assertThat(priorityUserAfterDelete.getId()).isEqualTo("2");  // User with ID=2 should now be first
    }
}
