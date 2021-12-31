package com.barauna.DEVinHouse.service;

import com.barauna.DEVinHouse.database.repository.UserRepository;
import com.barauna.DEVinHouse.entity.User;
import com.barauna.DEVinHouse.exception.InvalidVillagerDataException;
import com.barauna.DEVinHouse.utils.UserUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
    }

    public User store(Long id, String email, String password) throws Exception {
        Set<String> roles = new HashSet<>();
        roles.add("USER");

        return store(id, email, password, roles);
    }

    public User store(Long id, String email, String password, Set<String> roles) throws Exception {
        validate(id, email, password);

        String maskedPassword = passwordEncoder.encode(password);
        return repository.store(id, email, maskedPassword, roles);
    }

    public Optional<User> getUser(String username) throws Exception {
        return repository.getByLogin(username);
    }

    public void updateUser(User user) throws Exception {
        repository.updatePassword(user.getId(), user.getPassword());
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
