package com.pallav.InmemoryTwitter.in_memory_db;
import com.pallav.InmemoryTwitter.in_memory_db.entity.TestEntity;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class InMemoryStorageTest {

    private InMemoryStorage<TestEntity> storage;

    @BeforeEach
    void setUp() {
        storage = new InMemoryStorage<>(
                Comparator.comparingInt(TestEntity::getPriority).reversed()
        );
    }

    @Test
    void shouldSaveAndFindEntityById() {
        TestEntity entity = new TestEntity("1", 50);
        storage.saveEntity(entity);

        TestEntity result = storage.findEntityById("1");

        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo("1");
        assertThat(result.getPriority()).isEqualTo(50);
    }

    @Test
    void shouldReturnAllEntities() {
        storage.saveEntity(new TestEntity("1", 10));
        storage.saveEntity(new TestEntity("2", 20));

        List<TestEntity> all = storage.getAllEntities();

        assertThat(all).hasSize(2);
    }

    @Test
    void shouldDeleteEntity() {
        storage.saveEntity(new TestEntity("1", 10));
        storage.saveEntity(new TestEntity("2", 20));

        storage.deleteEntity("1");

        assertThat(storage.findEntityById("1")).isNull();
        assertThat(storage.getAllEntities()).hasSize(1);
    }

    @Test
    void shouldReturnEntityWithHighestPriority() {
        storage.saveEntity(new TestEntity("1", 5));
        storage.saveEntity(new TestEntity("2", 100));
        storage.saveEntity(new TestEntity("3", 50));

        TestEntity top = storage.findByPriority();

        assertThat(top).isNotNull();
        assertThat(top.getPriority()).isEqualTo(100);
    }
}