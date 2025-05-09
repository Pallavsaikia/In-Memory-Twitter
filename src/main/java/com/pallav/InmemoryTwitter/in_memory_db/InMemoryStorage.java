package com.pallav.InmemoryTwitter.in_memory_db;

import com.pallav.InmemoryTwitter.in_memory_db.decorators.Index;
import com.pallav.InmemoryTwitter.in_memory_db.decorators.PrimaryKey;
import com.pallav.InmemoryTwitter.in_memory_db.decorators.PriorityComparator;
import com.pallav.InmemoryTwitter.in_memory_db.decorators.Unique;

import java.lang.reflect.Method;
import java.util.*;
import java.util.function.Function;

public class InMemoryStorage<T> implements Storage<T> {

    private final Map<String, T> entityStorage = new HashMap<>();
    private final PriorityQueue<T> priorityQueue;
    private final Map<String, Map<String, Set<T>>> indexes = new HashMap<>();
    private final Map<String, Function<T, String>> indexExtractors = new HashMap<>();
    private Method idGetter;
    private List<Method> uniqueFields = new ArrayList<>();

    public InMemoryStorage(Class<T> clazz) {
        this.idGetter = findPrimaryKeyGetter(clazz);
        this.priorityQueue = new PriorityQueue<>(findComparator(clazz));
        initIndexes(clazz);
        initUniqueFields(clazz);
    }

    private void initUniqueFields(Class<T> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Unique.class)) {
                uniqueFields.add(method);
            }
        }
    }

    private Method findPrimaryKeyGetter(Class<T> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(PrimaryKey.class)) {
                return method;
            }
        }
        throw new RuntimeException("No @PrimaryKey annotated method found in class " + clazz.getName());
    }

    private Comparator<T> findComparator(Class<T> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(PriorityComparator.class)
                    && Comparator.class.isAssignableFrom(method.getReturnType())
                    && java.lang.reflect.Modifier.isStatic(method.getModifiers())) {
                try {
                    return (Comparator<T>) method.invoke(null);
                } catch (Exception e) {
                    throw new RuntimeException("Error invoking @PriorityComparator method", e);
                }
            }
        }
        throw new RuntimeException("No valid @PriorityComparator method found in class " + clazz.getName());
    }

    private void initIndexes(Class<T> clazz) {
        for (Method method : clazz.getDeclaredMethods()) {
            if (method.isAnnotationPresent(Index.class)) {
                // Get the index name directly from the @Index annotation
                Index indexAnnotation = method.getAnnotation(Index.class);
                String fieldName = indexAnnotation.value();  // E.g., "email", "username"

                // Register index extractor
                indexExtractors.put(fieldName, entity -> {
                    try {
                        // Extract the actual value for the field
                        return (String) method.invoke(entity);  // This will return the actual field value
                    } catch (Exception e) {
                        throw new RuntimeException("Failed to extract index for " + fieldName, e);
                    }
                });

                // Initialize the index map for this field
                indexes.put(fieldName, new HashMap<>());
            }
        }
    }

    @Override
    public void saveEntity(T entity) {
        // Check uniqueness before saving the entity
        for (Method uniqueField : uniqueFields) {
            String value = extractValueFromEntity(uniqueField, entity);

            // Check if the value already exists in the relevant index
            for (Map.Entry<String, Map<String, Set<T>>> entry : indexes.entrySet()) {
                Map<String, Set<T>> fieldMap = entry.getValue();
                if (fieldMap.containsKey(value)) {
                    throw new RuntimeException("Duplicate value found for unique field: " + value);
                }
            }
        }

        // Proceed to save the entity
        String entityId = getEntityId(entity);
        entityStorage.put(entityId, entity);
        priorityQueue.offer(entity);

        // Update the indexes after saving the entity
        for (Map.Entry<String, Function<T, String>> entry : indexExtractors.entrySet()) {
            String field = entry.getKey();
            String value = entry.getValue().apply(entity);
            indexes.get(field)
                    .computeIfAbsent(value, k -> new HashSet<>())
                    .add(entity);  // Add the entity to the index
        }
    }

    private String extractValueFromEntity(Method field, T entity) {
        try {
            return (String) field.invoke(entity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to extract value from entity for unique field", e);
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
//        System.out.println(indexes);
        return indexes.getOrDefault(fieldName, Collections.emptyMap())
                .getOrDefault(value, Collections.emptySet());
    }

    private void rebuildPriorityQueue() {
        priorityQueue.clear();
        priorityQueue.addAll(entityStorage.values());
    }

    private String getEntityId(T entity) {
        try {
            return (String) idGetter.invoke(entity);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get entity ID", e);
        }
    }
}
