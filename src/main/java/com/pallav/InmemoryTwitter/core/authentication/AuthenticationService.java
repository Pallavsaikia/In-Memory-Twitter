package com.pallav.InmemoryTwitter.core.authentication;




import com.pallav.InmemoryTwitter.config.base_roles.RoleBasic;
import com.pallav.InmemoryTwitter.core.authentication.models.Roles;
import com.pallav.InmemoryTwitter.core.authentication.models.UserRoles;
import com.pallav.InmemoryTwitter.core.authentication.repo.PrivilegesRepository;
import com.pallav.InmemoryTwitter.core.authentication.repo.RolesRepository;
import com.pallav.InmemoryTwitter.core.authentication.repo.UserRolesRepository;
import com.pallav.InmemoryTwitter.core.user.models.User;
import com.pallav.InmemoryTwitter.core.user.repo.UserRepository;
import com.pallav.InmemoryTwitter.exceptions.AuthenticationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Set;

import java.util.UUID;

@Service
public class AuthenticationService {

    private final UserRepository userRepository;
    private final UserRoleService userRoleService;
    private final RolesService rolesService;
    @Autowired
    public AuthenticationService(UserRepository userRepository,
            UserRoleService userRoleService,
            RolesService rolesService) {
        this.userRepository = userRepository;
        this.userRoleService=userRoleService;
        this.rolesService=rolesService;
    }

    public User register(String username, String email, String password) {
        // Check if username or email already exist
        if (userRepository.existsByUsername(username)) {
            throw new IllegalArgumentException("Username is already taken.");
        }

        if (userRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("Email is already taken.");
        }

        // Create a new user with a generated ID
        String id = UUID.randomUUID().toString();
        User user = new User(id, username, email, password);

        // Save the user
        userRepository.save(user);
        Set<Roles> roles=rolesService.findByName(RoleBasic.INSTANCE.getName());
        Roles role=roles.toArray(new Roles[0])[0];
        System.out.println(role);
        UserRoles userRoles=userRoleService.assignRoleToUser(user,role);
        System.out.println(userRoles);
        return user;
    }

    public User login(String username, String password) {

        Set<User> userSet= userRepository.findByUsername(username);
        if(userSet.isEmpty()){
            throw new AuthenticationException("Wrong Credentials");
        }
        for (User user : userSet) {
            if(!Objects.equals(password, user.password())){
                throw new AuthenticationException("Wrong Credentials");
            }
        }
        return  userSet.iterator().next();
    }
}
