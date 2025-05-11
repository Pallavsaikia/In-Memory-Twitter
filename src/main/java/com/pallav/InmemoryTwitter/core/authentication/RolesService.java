package com.pallav.InmemoryTwitter.core.authentication;

import com.pallav.InmemoryTwitter.core.authentication.models.Roles;
import com.pallav.InmemoryTwitter.core.authentication.repo.PrivilegesRepository;
import com.pallav.InmemoryTwitter.core.authentication.repo.RolesRepository;
import com.pallav.InmemoryTwitter.core.authentication.repo.UserRolesRepository;
import com.pallav.InmemoryTwitter.core.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class RolesService {
    private final RolesRepository rolesRepository;
    @Autowired
    public RolesService(RolesRepository rolesRepository) {
        this.rolesRepository=rolesRepository;
    }
    public Set<Roles> findByName(String name){
        return  rolesRepository.findByName(name);
    }
}
