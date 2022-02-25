package com.barauna.DEVinHouse.service;

import com.barauna.DEVinHouse.entity.Role;
import com.barauna.DEVinHouse.entity.Villager;
import com.barauna.DEVinHouse.repository.UserRepository;
import com.barauna.DEVinHouse.entity.User;
import com.barauna.DEVinHouse.exception.InvalidVillagerDataException;
import com.barauna.DEVinHouse.to.UserTO;
import com.barauna.DEVinHouse.utils.UserUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder, RoleService roleService) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    @Transactional()
    public UserTO create(Villager villager, String email, String password, Set<String> roleNames) throws Exception {
        validate(villager, email, password);

        final String maskedPassword = passwordEncoder.encode(password);
        final Set<Role> roles = new HashSet<>(roleService.getByNames(roleNames));

        final User newUser = new User();
        newUser.setEmail(email);
        newUser.setPassword(maskedPassword);
        newUser.setVillager(villager);
        newUser.setRoles(roles);

        repository.save(newUser);
        villager.setUser(newUser);
        return new UserTO(newUser.getId(), newUser.getEmail(), newUser.getPassword(), newUser.getVillager().getId(), roleNames);
    }

    public void delete(Long id) throws IllegalArgumentException {
        repository.deleteById(id);
    }

    public UserTO getUser(String email) throws SQLException, NoSuchElementException {
        final User user = repository.findOneByEmail(email).orElseThrow();
        final Set<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());

        return new UserTO(user.getId(), user.getEmail(), user.getPassword(), user.getVillager().getId(), roles);
    }

    public UserTO getByVillagerId(Long villagerId) throws Exception {
        if(villagerId == null) {
            throw new Exception("Invalid argument. VillagerId cannot be null");
        }
        final Optional<User> optionalUser = repository.findOneByVillagerId(villagerId);

        if(optionalUser.isEmpty()) {
            throw new Exception("No user found for villager with id " + villagerId);
        }

        final User user = optionalUser.get();
        final Set<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toSet());
        return new UserTO(user.getId(), user.getEmail(), user.getPassword(), user.getVillager().getId(), roles);
    }

    public void updatePassword(String email, String newPassword) throws Exception {
        validate(email, newPassword);
        final User user = repository.findOneByEmail(email).orElseThrow();
        user.setPassword(newPassword);
    }

    private void validate(Villager villager, String username, String password) throws Exception {
        if (villager == null) {
            throw new InvalidVillagerDataException("Invalid villager reference");
        }

        validate(username, password);
    }

    private void validate(String username, String password) throws Exception {

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
