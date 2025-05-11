package com.pallav.InmemoryTwitter.core.authentication.repo;

import com.pallav.InmemoryTwitter.core.authentication.models.RolePrivileges;
import com.pallav.InmemoryTwitter.in_memory_db.InMemoryStorage;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public class RolePrivilegesRepository {

    private final InMemoryStorage<RolePrivileges> rolePrivilegesStorage;

    public RolePrivilegesRepository() {
        this.rolePrivilegesStorage = new InMemoryStorage<>(RolePrivileges.class);
    }

    public RolePrivileges save(RolePrivileges rolePrivileges) {
        return rolePrivilegesStorage.saveEntity(rolePrivileges);
    }

    public List<RolePrivileges> getAll() {
        return rolePrivilegesStorage.getAllEntities();
    }

    public RolePrivileges findById(String id) {
        return rolePrivilegesStorage.findEntityById(id);
    }

    public Set<RolePrivileges> findByRoleId(String roleId) {
        return rolePrivilegesStorage.findByIndex("role_id", roleId);
    }

    public Set<RolePrivileges> findByPrivilegeId(String privilegeId) {
        return rolePrivilegesStorage.findByIndex("privilege_id", privilegeId);
    }
}
