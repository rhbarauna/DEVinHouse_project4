package com.barauna.DEVinHouse.service;

import com.barauna.DEVinHouse.database.repository.UserRoleRepository;
import com.barauna.DEVinHouse.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserRoleService {

    private final UserRoleRepository repository;
    private final RoleService roleService;

    public UserRoleService(UserRoleRepository repository, RoleService roleService) {
        this.repository = repository;
        this.roleService = roleService;
    }

    public void create(Long userId, Set<String> roles) throws Exception {
        //TODO - validate roles content to be USER or ADMIN and uppercase to consult or ignore case
        List<Long> rolesIds = roleService.getByNames(roles).stream().map(Role::getId).collect(Collectors.toList());

        if(rolesIds.isEmpty()) {
            throw new Exception("Roles not found");
        }

        this.create(userId, rolesIds);
    }

    public void create(Long userId, List<Long> roles) throws RuntimeException {
        roles.forEach(roleId -> {
            try {
                this.create(userId, roleId);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void create(Long userId, Long roleId) throws Exception {
        repository.store(userId, roleId);
    }

    public Set<String> getRolesNamesByUserId(Long userId) throws Exception {
        return repository.getByUserId(userId).stream()
            .map(
                userRole -> {
                    try {
                        Role role = roleService.find(userRole.getRoleId());
                        return role.getName();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            ).collect(Collectors.toSet())
        ;
    }
}
