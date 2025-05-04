package com.pallav.InmemoryTwitter.in_memory_db;


import java.util.List;

public interface Storage<T> {

    // Save an entity to storage
    void saveEntity(T entity);

    // Find an entity by its ID
    T findEntityById(String entityId);

    // Get all entities in storage
    List<T> getAllEntities();

    // Delete an entity by its ID
    void deleteEntity(String entityId);

    // Find entity by priority (e.g., timestamp, score, etc.)
    T findByPriority();

}
