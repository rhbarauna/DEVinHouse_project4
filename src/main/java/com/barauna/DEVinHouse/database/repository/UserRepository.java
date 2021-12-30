package com.barauna.DEVinHouse.database.repository;

import com.barauna.DEVinHouse.entity.User;
import org.springframework.stereotype.Repository;

import java.util.*;

@Repository
public class UserRepository {
    Set<User> users = new HashSet<>();

    public UserRepository() {
        this.users.addAll(Arrays.asList(
                new User("rhbarauna@powerkitchen.ca", "$2a$10$vdzLTE.f4hUuo1sseEJnzO8hJim0.2UnBnqpDhI.xTqVvFAD68FSm", Set.of("ADMIN"), 1L),
                new User("rhbarauna@gmail.com", "$2a$10$vdzLTE.f4hUuo1sseEJnzO8hJim0.2UnBnqpDhI.xTqVvFAD68FSm", Set.of("USER"), 2L)
        ));
    }

    public User store (Long villagerId, String login, String password) {
        final User user = new User(login, password, Set.of("USER"), villagerId);
        this.users.add(user);
        return user;
    }

    public User getByUsername(String username) {
        return users.stream().filter(user -> user.getLogin().equals(username)).findFirst().orElse(null);
    }

    public void update(User user) {
        this.users.add(user);
    }
}
