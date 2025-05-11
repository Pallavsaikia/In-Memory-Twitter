package com.pallav.InmemoryTwitter.core.authentication;

import com.pallav.InmemoryTwitter.core.authentication.models.Roles;
import com.pallav.InmemoryTwitter.core.authentication.models.UserRoles;
import com.pallav.InmemoryTwitter.core.authentication.repo.RolesRepository;
import com.pallav.InmemoryTwitter.core.authentication.repo.UserRolesRepository;
import com.pallav.InmemoryTwitter.core.user.models.User;
import com.pallav.InmemoryTwitter.core.user.repo.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserRoleService {
    private final UserRolesRepository userRolesRepository;
    @Autowired
    public UserRoleService(UserRolesRepository userRolesRepository) {
        this.userRolesRepository=userRolesRepository;
    }

    public UserRoles assignRoleToUser(User user, Roles role){
          return userRolesRepository.save(new UserRoles(UUID.randomUUID().toString(),role.id(),user.id()));
    }
}
