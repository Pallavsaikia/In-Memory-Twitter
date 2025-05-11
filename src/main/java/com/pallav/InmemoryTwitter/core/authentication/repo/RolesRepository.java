package com.pallav.InmemoryTwitter.core.authentication.repo;

import com.pallav.InmemoryTwitter.core.authentication.models.Roles;
import com.pallav.InmemoryTwitter.in_memory_db.InMemoryStorage;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class RolesRepository {

    private final InMemoryStorage<Roles> rolesStorage;

    public RolesRepository() {
        this.rolesStorage = new InMemoryStorage<>(Roles.class);
    }

    public Roles save(Roles roles) {
        return  rolesStorage.saveEntity(roles);
    }

    public List<Roles> getAll() {
        return rolesStorage.getAllEntities();
    }

    public Roles findById(String id) {
        return rolesStorage.findEntityById(id);
    }

    public Set<Roles> findByName(String name) {
        return rolesStorage.findByIndex("name", name);
    }
}
