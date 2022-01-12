package com.barauna.DEVinHouse.repository;

import com.barauna.DEVinHouse.entity.Role;
import org.springframework.data.repository.CrudRepository;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@org.springframework.stereotype.Repository
public interface RoleRepository extends CrudRepository<Role, Long> {
    Optional<Role> findOneByName(String name) throws SQLException;
    List<Role> findAllByNameIn(Set<String> names) throws SQLException;
}
