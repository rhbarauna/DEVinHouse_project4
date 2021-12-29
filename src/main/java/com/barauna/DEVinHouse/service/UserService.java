package com.barauna.DEVinHouse.service;

import com.barauna.DEVinHouse.database.repository.UserRepository;
import com.barauna.DEVinHouse.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository repository;

    public UserService(UserRepository repository) {
        this.repository = repository;
    }

    public User getUser(String username) {
        return repository.getByUsername(username);
    }

    public void updateUser(User user) {
        repository.update(user);
    }
}
