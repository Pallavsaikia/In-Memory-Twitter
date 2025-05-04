# 🐦 In-Memory Twitter Storage (Java + Spring Boot)

This project provides a lightweight, in-memory, generic storage engine for entities like Users, Tweets, and Follows. It leverages `HashMap` and `PriorityQueue` for storage, and supports indexing on any field for fast lookups.

---

## ✅ Features

- Generic `Storage<T>` interface
- In-memory backend using `HashMap` and `PriorityQueue`
- Indexing and searching via any field (e.g. `username`, `email`)
- Reflection-based ID access (`getId()`)
- Priority queue for ordered access (e.g. recent Tweets)
- Fully unit-tested with Spring Boot

---

## 📦 Structure

- `Storage<T>` — interface with core methods: `save`, `findById`, `delete`, `findByPriority`, `findByIndex`
- `InMemoryStorage<T>` — implementation with `Map`, `PriorityQueue`, and field index map
- `User` — sample entity
- `InMemoryStorageTest` — unit tests

---

## 🧠 How Indexing Works

You provide a map of field names to extractor functions when initializing storage:

```java
Map<String, Function<User, String>> userIndexes = new HashMap<>();
userIndexes.put("username", User::getUsername);
userIndexes.put("email", User::getEmail);

Storage<User> userStorage = new InMemoryStorage<>(Comparator.comparing(User::getUsername), userIndexes);
