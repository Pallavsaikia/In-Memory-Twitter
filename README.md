# 🐦 In-Memory Twitter Storage (Java + Spring Boot)

This project provides a lightweight, in-memory, generic storage engine for entities like Users, Tweets, and Follows. It leverages `HashMap` and `PriorityQueue` for storage and supports indexing on any field for fast lookups. The system has been enhanced with **decorators** to add additional functionality like **logging**, **validation**, **caching**, and more.

---

## ✅ Features

- **Generic `Storage<T>` interface**
- **In-memory backend** using `HashMap` and `PriorityQueue`
- **Indexing and searching** via any field (e.g., `username`, `email`)
- **Reflection-based ID access** (`getId()`)
- **Priority queue** for ordered access (e.g., recent Tweets)
- **Decorators** to enhance functionality:
  - **Logging**: Logs all operations
  - **Validation**: Validates entities before saving
  - **Caching**: Caches entities to speed up repeated lookups
  - **Indexing**: Automatically indexes fields such as `username` and `email`
  - **Uniqueness Enforcement**: Ensures that fields like `username` and `email` are unique
  - **Priority Sorting**: Allows sorting of entities by a particular field, like `username`
- Fully unit-tested with **Spring Boot**

---

## 📦 Structure

- **`Storage<T>`** — Interface with core methods: `save`, `findById`, `delete`, `findByPriority`, `findByIndex`
- **`InMemoryStorage<T>`** — Implementation with `Map`, `PriorityQueue`, and field index map
- **`LoggingStorageDecorator<T>`** — Adds logging functionality to storage operations
- **`ValidationStorageDecorator<T>`** — Validates entities before saving to storage
- **`CachingStorageDecorator<T>`** — Caches entities for faster lookup
- **`User`** — Sample entity with decorators applied
- **`InMemoryStorageTest`** — Unit tests

---

## 🧠 How Indexing and Decorators Work

In this example, the `User` entity uses several decorators to enhance the functionality of the storage system:

- **`@PrimaryKey("id")`** — Marks the `id` field as the primary key for the entity.
- **`@Index("field")`** — Marks the field for indexing, improving the speed of lookups.
- **`@Unique("field")`** — Ensures that the field is unique across the storage.
- **`@PriorityComparator`** — Defines how entities should be sorted within the priority queue.

Here's how the `User` entity is defined:

```java
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
```