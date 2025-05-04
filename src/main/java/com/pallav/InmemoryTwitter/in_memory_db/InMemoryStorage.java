package com.pallav.InmemoryTwitter.in_memory_db;


import java.util.*;

public class InMemoryStorage<T> implements Storage<T> {

    private final Map<String, T> entityStorage = new HashMap<>();
    private final PriorityQueue<T> priorityQueue;

    // Constructor that takes a comparator to define the priority ordering
    public InMemoryStorage(Comparator<T> comparator) {
        this.priorityQueue = new PriorityQueue<>(comparator);
    }

    @Override
    public void saveEntity(T entity) {
        // Assuming entities have a method to get their ID (this could be done using reflection or interface method)
        String entityId = getEntityId(entity);
        entityStorage.put(entityId, entity);
        priorityQueue.offer(entity);
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
            // Remove from the priority queue, but priority queue doesn't support direct removal
            // A full rebuild could be done here (though inefficient)
            rebuildPriorityQueue();
        }
    }

    @Override
    public T findByPriority() {
        return priorityQueue.peek();  // Return the highest priority entity
    }

    // Helper method to rebuild the priority queue after an entity is deleted
    private void rebuildPriorityQueue() {
        priorityQueue.clear();
        priorityQueue.addAll(entityStorage.values());
    }

    // Method to get entity ID (assuming entity has an `id` field or method to retrieve it)
    private String getEntityId(T entity) {
        try {
            // Using reflection to get the "id" field value (assuming entity has an "id" method or field)
            return (String) entity.getClass().getMethod("getId").invoke(entity);
        } catch (Exception e) {
            throw new RuntimeException("Entity must have an ID method or field");
        }
    }
}
