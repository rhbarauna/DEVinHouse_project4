package com.barauna.DEVinHouse.database.repository;


import com.barauna.DEVinHouse.entity.UserRole;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UserRoleRepository {
    private final String TABLE_NAME = "user_role";
    private final Connection dbConnection;

    public UserRoleRepository(Connection connection) {
        this.dbConnection = connection;
    }

    public void store(Long userId, Long roleId) throws SQLException {
        PreparedStatement pStmt = dbConnection.prepareStatement("INSERT INTO " + TABLE_NAME + " (user_id, role_id) VALUES(?, ?)");
        pStmt.setLong(1, userId);
        pStmt.setLong(2, roleId);
        pStmt.execute();
    }

    public List<UserRole> getByUserId(Long userId) throws SQLException {
        PreparedStatement pStmt = dbConnection.prepareStatement("SELECT * FROM " + TABLE_NAME + " where user_id = ?");
        pStmt.setLong(1, userId);
        pStmt.execute();

        ResultSet resultSet = pStmt.getResultSet();

        List<UserRole> userRoles = new ArrayList<>();
        while (resultSet.next()) {
            userRoles.add(
                new UserRole(
                    resultSet.getLong("user_id"),
                    resultSet.getLong("role_id")
                )
            );
        }

        return userRoles;
    }

    public void deleteByUserId(Long userId) throws SQLException {
        PreparedStatement pStmt = dbConnection.prepareStatement("DELETE FROM " + TABLE_NAME + " where user_id = ?");
        pStmt.setLong(1, userId);
        pStmt.execute();
    }
}
