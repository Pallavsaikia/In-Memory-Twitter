package com.pallav.InmemoryTwitter.in_memory_db;
import com.pallav.InmemoryTwitter.core.user.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.function.Function;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class InMemoryStorageTest {

    @Test
    public void testUserStorage() {
        Map<String, Function<User, String>> indexMap = new HashMap<>();
        indexMap.put("username", User::getUsername);
        indexMap.put("email", User::getEmail);

        InMemoryStorage<User> userStorage = new InMemoryStorage<>(
                Comparator.comparing(User::getId),
                indexMap
        );

        User u1 = new User("1", "pallav", "pallav@example.com","Adaa");
        User u2 = new User("2", "john", "john@example.com","Adaa");
        User u3 = new User("3", "pallav", "p2@example.com","Adaa");

        userStorage.saveEntity(u1);
        userStorage.saveEntity(u2);
        userStorage.saveEntity(u3);

        System.out.println("Find by ID 2: " + userStorage.findEntityById("2"));

        System.out.println("Find by username = 'pallav'");
        Set<User> pallavUsers = userStorage.findByIndex("username", "pallav");
        pallavUsers.forEach(System.out::println);

        System.out.println("Priority user: " + userStorage.findByPriority());

        userStorage.deleteEntity("1");

        System.out.println("After deleting ID=1");
        Set<User> remaining = userStorage.findByIndex("username", "pallav");
        remaining.forEach(System.out::println);
    }
}