package com.barauna.DEVinHouse.database.repository;

import com.barauna.DEVinHouse.entity.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Repository
public class UserRepository {
    Map<String, User> users = new HashMap<>();

    public UserRepository() {
        this.users.put("teste@teste.com", new User("teste@teste.com", "$2a$10$vdzLTE.f4hUuo1sseEJnzO8hJim0.2UnBnqpDhI.xTqVvFAD68FSm", Set.of("ADMIN", "USER")));
    }

    public User getByUsername(String username) {
        return users.get(username);
    }

    public void update(User user) {
        this.users.put(user.getLogin(), user);
    }

}
