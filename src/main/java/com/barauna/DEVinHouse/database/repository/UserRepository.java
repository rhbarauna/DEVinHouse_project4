package com.barauna.DEVinHouse.database.repository;

import com.barauna.DEVinHouse.entity.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Optional;

@Repository
public class UserRepository {

    private final String TABLE_NAME = "\"user\"";
    private final Connection dbConnection;

    public UserRepository(Connection connection) {
        this.dbConnection = connection;
    }

    public User store (Long villagerId, String email, String password) throws SQLException {
        PreparedStatement pStmt = dbConnection.prepareStatement("INSERT INTO " + TABLE_NAME + " (villager_id, email, password) VALUES(?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS);

        pStmt.setLong(1, villagerId);
        pStmt.setString(2, email);
        pStmt.setString(3, password);
        pStmt.execute();

        ResultSet resultSet = pStmt.getGeneratedKeys();
        User newUser = null;
        while (resultSet.next()) {
            newUser = new User(
                    resultSet.getLong("id"),
                    villagerId,
                    email,
                    password);
        }

        return newUser;
    }

    public Optional<User> getByEmail(String email) throws SQLException {
        PreparedStatement pStmt = dbConnection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE email = ?");
        pStmt.setString(1, email);
        pStmt.execute();

        ResultSet resultSet = pStmt.getResultSet();

        User user = null;
        while (resultSet.next()) {

            user = new User(
                resultSet.getLong("id"),
                resultSet.getLong("villager_id"),
                resultSet.getString("email"),
                resultSet.getString("password")
            );
        }

        return Optional.ofNullable(user);
    }

    public void updatePassword(Long userId, String newPassword) throws SQLException{
        PreparedStatement pStmt = dbConnection.prepareStatement("UPDATE " + TABLE_NAME + " SET password = ? WHERE id = ?");
        pStmt.setString(1, newPassword);
        pStmt.setLong(2, userId);
        pStmt.execute();
    }

    public void delete(Long villagerId) throws SQLException {
        PreparedStatement pStmt = dbConnection.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE villager_id = ?");
        pStmt.setLong(1, villagerId);
        pStmt.execute();
    }
}
