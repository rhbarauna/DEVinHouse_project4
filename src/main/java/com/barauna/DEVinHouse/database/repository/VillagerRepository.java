package com.barauna.DEVinHouse.database.repository;

import com.barauna.DEVinHouse.entity.Villager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class VillagerRepository {
    private static final String TABLE_NAME = "villager";
    private final Connection dbConnection;

    public VillagerRepository(Connection connection) {
        this.dbConnection = connection;
    }

    public List<Villager> all() throws SQLException {
        Statement stmt = dbConnection.createStatement();
        stmt.execute("SELECT * FROM " + TABLE_NAME);

        ResultSet resultSet = stmt.getResultSet();
        List<Villager> villagers = new ArrayList<>();
        while (resultSet.next()) {
            final Villager villager = new Villager(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("surname"),
                    resultSet.getString("document"),
                    resultSet.getDate("birthday").toLocalDate(),
                    resultSet.getFloat("wage")
            );
            villagers.add(villager);
        }
        return villagers;
    }

    public Optional<Villager> find(Long villagerId) throws SQLException {
        PreparedStatement pStmt = dbConnection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id = ?");
        pStmt.setLong(1, villagerId);
        pStmt.execute();

        ResultSet resultSet = pStmt.getResultSet();

        Villager villager = null;

        while (resultSet.next()) {
            villager = new Villager(
                resultSet.getLong("id"),
                resultSet.getString("name"),
                resultSet.getString("surname"),
                resultSet.getString("document"),
                resultSet.getDate("birthday").toLocalDate(),
                resultSet.getFloat("wage")
            );
        }

        return Optional.ofNullable(villager);
    }

    public List<Villager> getByName(String villagerName) throws SQLException {
        PreparedStatement pStmt = dbConnection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE name ilike ?");
        pStmt.setString(1, "%"+villagerName+"%");
        pStmt.execute();

        ResultSet resultSet = pStmt.getResultSet();

        List<Villager> villagers = new ArrayList<>();
        while (resultSet.next()) {
            final Villager villager = new Villager(
                    resultSet.getLong("id"),
                    resultSet.getString("name"),
                    resultSet.getString("surname"),
                    resultSet.getString("document"),
                    resultSet.getDate("birthday").toLocalDate(),
                    resultSet.getFloat("wage")
            );
            villagers.add(villager);
        }
        return villagers;
    }

    public List<Villager> getByBirthMonth(String villagerBirthMonth) throws SQLException {
        return this.all().stream()
            .filter(
                    villager -> villager.getBirthday().getMonth().name().equalsIgnoreCase(villagerBirthMonth)
            ).collect(Collectors.toList());
    }

    public List<Villager> getByAge(Integer villagerAge) throws SQLException {
        return this.all().stream()
            .filter(
                    villager -> Period.between(villager.getBirthday(), LocalDate.now()).getYears() >= villagerAge
            ).collect(Collectors.toList());
    }

    public void delete(Long villagerId) throws SQLException {
        PreparedStatement pStmt = dbConnection.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE id = ?");
        pStmt.setLong(1, villagerId);
        pStmt.execute();
    }

    public Villager store(String name, String surName, LocalDate birthday, String document, Float wage) throws SQLException {
        PreparedStatement pStmt = dbConnection.prepareStatement("INSERT INTO " + TABLE_NAME + " (name, surname, document, birthday, wage) VALUES(?, ?, ?, ?, ?)",
            Statement.RETURN_GENERATED_KEYS);
        pStmt.setString(1, name);
        pStmt.setString(2, surName);
        pStmt.setString(3, document);
        pStmt.setDate(4, Date.valueOf(birthday));
        pStmt.setFloat(5, wage);
        pStmt.execute();

        ResultSet resultSet = pStmt.getGeneratedKeys();
        Villager newVillager = null;
        while (resultSet.next()) {
            newVillager = new Villager(
                    resultSet.getLong(1),
                    name.trim(),
                    surName.trim(),
                    document,
                    birthday,
                    wage
            );
        }
        return newVillager;
    }
}
