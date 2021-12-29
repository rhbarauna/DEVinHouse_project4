package com.barauna.DEVinHouse.database.repository;

import com.barauna.DEVinHouse.entity.Villager;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
public class VillagerRepository {
    private static final String TABLE_NAME = "villagers";
    private final Connection dbConnection;
    private final List<Villager> villagers = new ArrayList<>();

    public VillagerRepository(Connection connection) {
        this.dbConnection = connection;
        this.villagers.addAll(
            Arrays.asList(
                new Villager(1L, "Villager 1", "surname", "100.000.000-00", LocalDate.of(1941, Month.MARCH, 19), 10f),
                new Villager(2L, "Villager 2", "surname", "200.000.000-00", LocalDate.of(1953, Month.APRIL, 29), 20f),
                new Villager(3L, "Villager 3", "surname", "300.000.000-00", LocalDate.of(1983, Month.APRIL, 16), 40f),
                new Villager(4L, "Villager 4", "surname", "040.000.000-00", LocalDate.of(1985, Month.JULY, 10), 30f)
            )
        );
    }

    public List<Villager> all() throws SQLException {
        try (Statement stmt = dbConnection.createStatement()) {
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
    }

    public Optional<Villager> find(Long villagerId) throws SQLException {

        try (PreparedStatement pStmt = dbConnection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE id = ?")) {
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
    }

    public List<Villager> getByName(String villagerName) throws SQLException {
        try (PreparedStatement pStmt = dbConnection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE name ilike ?")) {
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
    }

    public List<Villager> getByBirthMonth(String villagerBirthMonth) throws SQLException {
        return villagers.stream()
                .filter(
                        villager -> villager.getBirthday().getMonth().name().equalsIgnoreCase(villagerBirthMonth)
                ).collect(Collectors.toList());
    }

    public List<Villager> getByAge(Integer villagerAge) throws SQLException {
        try (PreparedStatement pStmt = dbConnection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE age >= ?")) {
            pStmt.setInt(1, villagerAge);
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
    }

    public void delete(Long villagerId) throws SQLException {
        try (PreparedStatement pStmt = dbConnection.prepareStatement("DELETE FROM " + TABLE_NAME + " WHERE id = ?")) {
            pStmt.setLong(1, villagerId);
            pStmt.execute();
        }
    }

    public Villager store(String name, String surName, LocalDate birthday, String document, Float wage) throws SQLException {
        try (PreparedStatement pStmt = dbConnection.prepareStatement("INSERT INTO hero (name, surname, document, birthday, wage) VALUES(?, ?, ?, ?)",
                Statement.RETURN_GENERATED_KEYS)) {
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
}
