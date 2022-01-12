package com.barauna.DEVinHouse.repository;

import com.barauna.DEVinHouse.entity.Villager;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.sql.*;
import java.util.List;

@Repository
public interface VillagerRepository extends CrudRepository<Villager, Long> {

    List<Villager> findByNameContaining(String villagerName);

    @Query("SELECT v FROM Villager v where lower(TRIM(TO_CHAR(v.birthday, 'Month'))) like lower(:villagerBirthMonth)")
    List<Villager> findByBirthMonth(@Param("villagerBirthMonth") String villagerBirthMonth) throws SQLException;

    @Query("SELECT v FROM Villager v where DATE_PART('year', AGE(v.birthday)) >= :villagerAge")
    List<Villager> findByAgeGreaterThanOrEqualTo(Integer villagerAge) throws SQLException;
}
