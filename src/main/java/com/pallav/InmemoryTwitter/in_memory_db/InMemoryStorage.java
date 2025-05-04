package com.pallav.InmemoryTwitter.in_memory_db;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;

public class InMemoryStorage<T> implements Storage<T> {

    private final Map<String, T> entityStorage = new HashMap<>();
    private final PriorityQueue<T> priorityQueue;
    private final Map<String, Map<String, Set<T>>> indexes = new HashMap<>();
    private final Map<String, Function<T, String>> indexExtractors = new HashMap<>();

    public InMemoryStorage(Comparator<T> comparator, Map<String, Function<T, String>> indexExtractors) {
        this.priorityQueue = new PriorityQueue<>(comparator);
        this.indexExtractors.putAll(indexExtractors);
        for (String field : indexExtractors.keySet()) {
            indexes.put(field, new HashMap<>());
        }
    }

    @Override
    public void saveEntity(T entity) {
        String entityId = getEntityId(entity);
        entityStorage.put(entityId, entity);
        priorityQueue.offer(entity);

        for (Map.Entry<String, Function<T, String>> entry : indexExtractors.entrySet()) {
            String field = entry.getKey();
            String value = entry.getValue().apply(entity);
            indexes.get(field)
                    .computeIfAbsent(value, k -> new HashSet<>())
                    .add(entity);
        }
    }

    @Override
    public T findEntityById(String entityId) {
        return entityStorage.get(entityId);
    }

    @Override
    public List<T> getAllEntities() {
        return new ArrayList<>(entityStorage.values());
    }

    @Override
    public void deleteEntity(String entityId) {
        T entity = entityStorage.remove(entityId);
        if (entity != null) {
            rebuildPriorityQueue();
            for (Map.Entry<String, Function<T, String>> entry : indexExtractors.entrySet()) {
                String field = entry.getKey();
                String value = entry.getValue().apply(entity);
                Set<T> set = indexes.get(field).get(value);
                if (set != null) {
                    set.remove(entity);
                    if (set.isEmpty()) {
                        indexes.get(field).remove(value);
                    }
                }
            }
        }
    }

    @Override
    public T findByPriority() {
        return priorityQueue.peek();
    }

    @Override
    public Set<T> findByIndex(String fieldName, String value) {
        return indexes.getOrDefault(fieldName, Collections.emptyMap())
                .getOrDefault(value, Collections.emptySet());
    }

    private void rebuildPriorityQueue() {
        priorityQueue.clear();
        priorityQueue.addAll(entityStorage.values());
    }

    private String getEntityId(T entity) {
        try {
            Method getId = entity.getClass().getMethod("getId");
            return (String) getId.invoke(entity);
        } catch (Exception e) {
            throw new RuntimeException("Entity must have a public getId() method returning String");
        }
    }
}
