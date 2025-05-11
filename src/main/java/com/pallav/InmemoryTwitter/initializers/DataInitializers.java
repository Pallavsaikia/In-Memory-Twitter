package com.pallav.InmemoryTwitter.initializers;


import com.pallav.InmemoryTwitter.config.PrivilegeActionEnum;
import com.pallav.InmemoryTwitter.config.base_roles.RoleAdmin;
import com.pallav.InmemoryTwitter.config.base_roles.RoleBasic;
import com.pallav.InmemoryTwitter.core.authentication.models.Privileges;
import com.pallav.InmemoryTwitter.core.authentication.models.RolePrivileges;
import com.pallav.InmemoryTwitter.core.authentication.models.Roles;
import com.pallav.InmemoryTwitter.core.authentication.models.UserRoles;
import com.pallav.InmemoryTwitter.core.authentication.repo.PrivilegesRepository;
import com.pallav.InmemoryTwitter.core.authentication.repo.RolePrivilegesRepository;
import com.pallav.InmemoryTwitter.core.authentication.repo.RolesRepository;
import com.pallav.InmemoryTwitter.core.authentication.repo.UserRolesRepository;
import com.pallav.InmemoryTwitter.core.user.models.User;
import com.pallav.InmemoryTwitter.core.user.repo.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Component
public class DataInitializers {

    @Autowired
    private PrivilegesRepository privilegesRepository;
    @Autowired
    private RolesRepository rolesRepository;

    @Autowired
    private RolePrivilegesRepository rolePrivilegesRepository;

    @Autowired
    private UserRolesRepository userRolesRepository;

    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void initialize() {
        List<Privileges> privilegesList=initializePrivileges();
        RolesSaveTuple rolesSaved=initializeRoles();
        initialize_role_privileges(privilegesList,rolesSaved);
        initialize_admin(rolesSaved.adminRole());
    }

    void initialize_admin(Roles adminRole){
        User adminUser=userRepository.save(new User(UUID.randomUUID().toString(),
                "admin",
                "admin@xyz.com",
                "12345"
                ));
        userRolesRepository.save(new UserRoles(UUID.randomUUID().toString(),
                adminRole.id(),
                adminUser.id()));
        userRolesRepository.save(new UserRoles(UUID.randomUUID().toString(),
                "adminRole.id()",
                "adminUser.id())"));
    }

    void initialize_role_privileges(List<Privileges> privilegesList,RolesSaveTuple rolesSaved){
        for (Privileges privilege : privilegesList) {
            // Save each RolePrivileges object with a new UUID
            rolePrivilegesRepository.save(new RolePrivileges(UUID.randomUUID().toString(), rolesSaved.adminRole().id(), privilege.id()));
            System.out.println(privilegesList);

            if (RoleBasic.INSTANCE.getPrivileges().contains(privilege.name())) {
                rolePrivilegesRepository.save(new RolePrivileges(UUID.randomUUID().toString(), rolesSaved.basicRole().id(), privilege.id()));
            }
        }
    }

    List<Privileges> initializePrivileges(){
        PrivilegeActionEnum[] privilegesList = PrivilegeActionEnum.values();
        List<Privileges> privilegeSavedList= new ArrayList<>();
        for (PrivilegeActionEnum privilege : privilegesList) {
            String id = UUID.randomUUID().toString();
            privilegeSavedList.add( privilegesRepository.save(new Privileges(id, privilege.getValue())));
        }
        return privilegeSavedList;
    }

    RolesSaveTuple initializeRoles(){
        Roles adminRole=rolesRepository.save(new Roles(UUID.randomUUID().toString(), RoleAdmin.INSTANCE.getName()));
        Roles basicRole=rolesRepository.save(new Roles(UUID.randomUUID().toString(), RoleBasic.INSTANCE.getName()));
        return new RolesSaveTuple(adminRole,basicRole);
    }
}
