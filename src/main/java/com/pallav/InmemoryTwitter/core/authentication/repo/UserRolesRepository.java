package com.pallav.InmemoryTwitter.core.authentication.repo;
import com.pallav.InmemoryTwitter.core.authentication.models.UserRoles;
import com.pallav.InmemoryTwitter.in_memory_db.InMemoryStorage;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class UserRolesRepository {

    private final InMemoryStorage<UserRoles> userRolesStorage;

    public UserRolesRepository() {
        this.userRolesStorage = new InMemoryStorage<>(UserRoles.class);
    }
    public UserRoles save(UserRoles userRoles) {
        return  userRolesStorage.saveEntity(userRoles);
    }

    public List<UserRoles> getAll() {
        return userRolesStorage.getAllEntities();
    }

    public UserRoles findById(String id) {
        return userRolesStorage.findEntityById(id);
    }

    public Set<UserRoles> findByUser(String id) {
        return userRolesStorage.findByIndex("user_id", id);
    }

}
