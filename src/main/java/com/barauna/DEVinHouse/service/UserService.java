package com.barauna.DEVinHouse.service;

import com.barauna.DEVinHouse.database.repository.UserRepository;
import com.barauna.DEVinHouse.entity.User;
import com.barauna.DEVinHouse.exception.InvalidVillagerDataException;
import com.barauna.DEVinHouse.utils.UserUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User store(Long id, String username, String password) throws Exception {
        validate(id, username, password);

        String maskedPassword = passwordEncoder.encode(password);
        return this.repository.store(id, username, maskedPassword);
    }

    public User getUser(String username) {
        return repository.getByUsername(username);
    }

    public void updateUser(User user) {
        repository.update(user);
    }

    private void validate(Long id, String username, String password) throws Exception {
        if(id == null || id <= 0) {
            throw new InvalidVillagerDataException("invalid villager reference");
        }

        if(!UserUtils.isValidUsername(username)) {
            throw new InvalidVillagerDataException("Invalid username. Must be a valid email");
        }

        if(!UserUtils.isValidPassword(password)) {
            throw new InvalidVillagerDataException("Invalid password. \n" +
                    "Must have 8+ characters containing:\n" +
                    "● 1+ Uppercase\n" +
                    "● 1+ Lowercase\n" +
                    "● 1+ Special character\n" +
                    "● 1+ Number\n");
        }
    }
}
