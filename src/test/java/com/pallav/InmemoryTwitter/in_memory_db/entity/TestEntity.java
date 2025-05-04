package com.pallav.InmemoryTwitter.in_memory_db.entity;

public class TestEntity {
    private final String id;
    private final int priority;

    public TestEntity(String id, int priority) {
        this.id = id;
        this.priority = priority;
    }

    public String getId() {
        return id;
    }

    public int getPriority() {
        return priority;
    }
}