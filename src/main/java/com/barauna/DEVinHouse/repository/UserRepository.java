package com.barauna.DEVinHouse.repository;

import com.barauna.DEVinHouse.entity.User;
import org.springframework.data.repository.CrudRepository;
import java.sql.*;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findOneByEmail(String email) throws SQLException;

    Optional<User> findOneByVillagerId(Long villagerId) throws SQLException;

    void deleteByVillagerId(Long villagerId) throws SQLException;
}
