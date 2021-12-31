package com.barauna.DEVinHouse.database.repository;

import com.barauna.DEVinHouse.entity.User;
import com.barauna.DEVinHouse.entity.Villager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class UserRepository {

    private final String TABLE_NAME = "users";
    private final Connection dbConnection;

    public UserRepository(Connection connection) {
        this.dbConnection = connection;
    }

    public User store (Long villagerId, String email, String password, Set<String> roles) throws SQLException {
        final User user = new User(email, password, Set.of("USER"), villagerId);
        try (PreparedStatement pStmt = dbConnection.prepareStatement("INSERT INTO " + TABLE_NAME + " (villager_id, email, password, roles) VALUES(?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
            final Array rolesArr = dbConnection.createArrayOf("VARCHAR", roles.toArray());

            pStmt.setLong(1, villagerId);
            pStmt.setString(2, email);
            pStmt.setString(3, password);
            pStmt.setArray(4, rolesArr);
            pStmt.execute();

            ResultSet resultSet = pStmt.getGeneratedKeys();
            User newUser = null;
            while (resultSet.next()) {
                newUser = new User(
                        resultSet.getLong("id"),
                        villagerId,
                        email,
                        password,
                        roles);
            }
            return newUser;
        }
    }

    public Optional<User> getByLogin(String email) throws SQLException {
        try (PreparedStatement pStmt = dbConnection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE email = ?")) {
            pStmt.setString(1, email);
            pStmt.execute();

            ResultSet resultSet = pStmt.getResultSet();

            User user = null;
            while (resultSet.next()) {
                final String[] stringsArr = (String[]) resultSet.getArray("roles").getArray();

                user = new User(
                    resultSet.getLong("id"),
                    resultSet.getLong("villager_id"),
                    resultSet.getString("email"),
                    resultSet.getString("password"),
                    Arrays.stream(stringsArr).collect(Collectors.toSet())
                );
            }

            return Optional.ofNullable(user);
        }
    }

    public void updatePassword(Long userId, String newPassword) throws SQLException{
        try (PreparedStatement pStmt = dbConnection.prepareStatement("UPDATE " + TABLE_NAME + " SET password = ? WHERE id = ?")) {
            pStmt.setLong(1, userId);
            pStmt.setString(2, newPassword);
            pStmt.execute();
        }
    }

    public void delete(Long villagerId) throws SQLException {
        try (PreparedStatement pStmt = dbConnection.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE villager_id = ?")) {
            pStmt.setLong(1, villagerId);
            pStmt.execute();
        }
    }
}
