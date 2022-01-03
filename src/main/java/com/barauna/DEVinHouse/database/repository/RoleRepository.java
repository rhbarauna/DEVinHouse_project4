package com.barauna.DEVinHouse.database.repository;

import com.barauna.DEVinHouse.entity.Role;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Repository
public class RoleRepository {
    private final String TABLE_NAME = "\"role\"";
    private final Connection dbConnection;

    public RoleRepository(Connection connection) {
        this.dbConnection = connection;
    }

    public Optional<Role> find(Long roleId) throws SQLException {
        PreparedStatement pStmt = dbConnection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id = ?");
        pStmt.setLong(1, roleId);
        pStmt.execute();

        ResultSet resultSet = pStmt.getResultSet();

        Role role = null;

        while (resultSet.next()) {
            role = new Role(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("description")
            );
        }

        return Optional.ofNullable(role);
    }

    public Optional<Role> get(String name) throws SQLException {
        PreparedStatement pStmt = dbConnection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE name = ?");
        pStmt.setString(1, name);
        pStmt.execute();

        ResultSet resultSet = pStmt.getResultSet();

        Role role = null;

        while (resultSet.next()) {
            role = new Role(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description")
            );
        }

        return Optional.ofNullable(role);
    }

    public List<Role> get(Set<String> names) throws SQLException {
        String stmt = String.format("SELECT * FROM " + TABLE_NAME + " WHERE name in (%s)",
            names.stream()
                .map(name -> "?")
                .collect(Collectors.joining(", "))
        );

        PreparedStatement pStmt = dbConnection.prepareStatement(stmt);
        int index = 1;

        for(String name : names) {
            pStmt.setString(index, name.toUpperCase());
            index++;
        }

        pStmt.execute();

        ResultSet resultSet = pStmt.getResultSet();

        List<Role> roles = new ArrayList<>();

        while (resultSet.next()) {
            roles.add(
                new Role(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("description")
                )
            );
        }
        return roles;
    }
}
