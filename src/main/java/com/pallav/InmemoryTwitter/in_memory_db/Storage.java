package com.pallav.InmemoryTwitter.in_memory_db;

import java.util.List;
import java.util.Set;


public interface Storage<T> {
    T saveEntity(T entity);
    T findEntityById(String entityId);
    List<T> getAllEntities();
    void deleteEntity(String entityId);
    T findByPriority();
    Set<T> findByIndex(String fieldName, String value);
}
