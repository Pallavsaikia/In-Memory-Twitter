package com.pallav.InmemoryTwitter.core.authentication.repo;

import com.pallav.InmemoryTwitter.core.authentication.models.Privileges;
import com.pallav.InmemoryTwitter.in_memory_db.InMemoryStorage;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class PrivilegesRepository {

    private final InMemoryStorage<Privileges> privilegeStorage;

    public PrivilegesRepository() {
        this.privilegeStorage = new InMemoryStorage<>(Privileges.class);
    }


    public Privileges save(Privileges privileges) {
        return  privilegeStorage.saveEntity(privileges);
    }

    public List<Privileges> getAll() {
        return privilegeStorage.getAllEntities();
    }

    public Privileges findById(String id) {
        return privilegeStorage.findEntityById(id);
    }

    public Set<Privileges> findByName(String name) {
        return privilegeStorage.findByIndex("name", name);
    }


}

