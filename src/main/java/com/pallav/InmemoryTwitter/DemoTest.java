package com.pallav.InmemoryTwitter;


import com.pallav.InmemoryTwitter.in_memory_db.InMemoryStorage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;

@RestController
public class DemoTest  {

    @GetMapping("/")
    public String hello() {
        Map<String, Function<User, String>> indexMap = new HashMap<>();
        indexMap.put("username", User::getUsername);
        indexMap.put("email", User::getEmail);

        InMemoryStorage<User> userStorage = new InMemoryStorage<>(
                Comparator.comparing(User::getId),
                indexMap
        );

        User u1 = new User("1", "pallav", "pallav@example.com");
        User u2 = new User("2", "john", "john@example.com");
        User u3 = new User("3", "abc", "p2@example.com");

        userStorage.saveEntity(u1);
        userStorage.saveEntity(u2);
        userStorage.saveEntity(u3);

        Set<User> usersByEmail = userStorage.findByIndex("username", "pallav");
        for (User user : usersByEmail) {
            System.out.println(user.getUsername());
        }
        return "Hello from Spring Boot with Java 21!";
    }
}