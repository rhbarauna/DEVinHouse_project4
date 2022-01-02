package com.barauna.DEVinHouse.service;

import com.barauna.DEVinHouse.database.repository.UserRepository;
import com.barauna.DEVinHouse.entity.User;
import com.barauna.DEVinHouse.exception.InvalidVillagerDataException;
import com.barauna.DEVinHouse.to.UserTO;
import com.barauna.DEVinHouse.utils.UserUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, UserRoleService userRoleService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;
    }

    public User create(Long id, String email, String password) throws Exception {
        Set<String> roles = new HashSet<>();
        roles.add("USER");

        return create(id, email, password, roles);
    }

    public User create(Long id, String email, String password, Set<String> roles) throws Exception {
        validate(id, email, password);

        String maskedPassword = passwordEncoder.encode(password);
        final User newUser = repository.store(id, email, maskedPassword);

        try {
            userRoleService.create(newUser.getId(), roles);
        } catch(Exception e) {
            this.delete(newUser.getId());
            throw e;
        }

        return newUser;
    }

    public void delete(Long id) throws Exception {
        repository.delete(id);
    }

    public UserTO getUser(String email) throws Exception {
        final User user = repository.getByEmail(email).orElseThrow();

        Set<String> roles = userRoleService.getRolesNamesByUserId(user.getId());

        return new UserTO(user.getEmail(), user.getPassword(), user.getVillagerId(), roles);
    }

    public void updatePassword(String email, String newPassword) throws Exception {
        final User user = repository.getByEmail(email).orElseThrow();
        repository.updatePassword(user.getId(), newPassword);
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
