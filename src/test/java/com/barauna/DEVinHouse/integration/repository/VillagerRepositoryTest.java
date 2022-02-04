package com.barauna.DEVinHouse.integration.repository;

import com.barauna.DEVinHouse.repository.VillagerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.sql.SQLException;


@DataJpaTest
public class VillagerRepositoryTest {

    @Autowired
    private VillagerRepository villagerRepository;

    @Test
    @Rollback(false) // esta anotation não é necessário já que a anotation colocada na classe já faz isso entre os métodos
    void testFindVillagerByBirthMonth() throws SQLException {
//        final String birthMonth = "";
//        villagerRepository.findByBirthMonth(birthMonth);
    }

    @Test
    void testFindVillagerByMinAge() throws SQLException {
//        final Integer age = 30;
//        villagerRepository.findByAgeGreaterThanOrEqualTo(age);
    }
}
