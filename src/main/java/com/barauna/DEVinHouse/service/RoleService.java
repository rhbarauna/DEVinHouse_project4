package com.barauna.DEVinHouse.service;

import com.barauna.DEVinHouse.repository.RoleRepository;
import com.barauna.DEVinHouse.entity.Role;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
public class RoleService {

    private final RoleRepository repository;

    public RoleService(RoleRepository repository) {
        this.repository = repository;
    }

    public List<Role> getByNames(Set<String> names) throws Exception {
        if(names == null || names.isEmpty()) {
            throw new Exception("Must pass at least one Role");
        }

        return repository.findAllByNameIn(names);
    }


    public Role find(Long roleId) throws Exception {
        return repository.findById(roleId).orElseThrow();
    }
}
