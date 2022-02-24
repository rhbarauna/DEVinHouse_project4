package com.barauna.DEVinHouse.unit.service;

import com.barauna.DEVinHouse.entity.Role;
import com.barauna.DEVinHouse.entity.User;
import com.barauna.DEVinHouse.entity.Villager;
import com.barauna.DEVinHouse.repository.UserRepository;
import com.barauna.DEVinHouse.service.RoleService;
import com.barauna.DEVinHouse.service.UserService;
import com.barauna.DEVinHouse.to.UserTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTests {
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private RoleService roleService;
    private UserService userService;

    @BeforeEach
    public void beforeEach(){
        this.userRepository = mock(UserRepository.class);
        this.passwordEncoder = mock(PasswordEncoder.class);
        this.roleService = mock(RoleService.class);
        this.userService = new UserService(userRepository, passwordEncoder, roleService);
    }

    @Test
    public void createUserSuccessfully() throws Exception {

        Villager villager = new Villager();
        String email = "teste@teste.com";
        String password = "1#cV4Da!1";
        Set<String> roleNames = Set.of("ADMIN");

        when(passwordEncoder.encode(password)).thenReturn("encoded");
        when(roleService.getByNames(roleNames)).thenReturn(List.of(new Role(1L, "ADMIN", "")));
        final UserTO userTO = userService.create(villager, email, password, roleNames);
        assertNotNull(userTO);
        assertEquals(email, userTO.getEmail());
        assertEquals("encoded", userTO.getPassword());
    }

    @Test
    public void createUserThrowErrorOnInvalidOrIncompleteVillager() {
        final Exception exception1 = assertThrows(Exception.class, () -> userService.create(null, "", "", Set.of()));
        assertEquals(exception1.getMessage(), "Invalid villager reference");
    }

    @Test
    public void createUserThrowErrorOnInvalidEmail() {
        Villager villager = new Villager();
        final String expectedMessage = "Invalid username. Must be a valid email";
        final Exception exception1 = assertThrows(Exception.class, () -> userService.create(villager, null, "", Set.of()));
        final Exception exception2 = assertThrows(Exception.class, () -> userService.create(villager, "t@t", "", Set.of()));
        final Exception exception3 = assertThrows(Exception.class, () -> userService.create(villager, "t@.com", "", Set.of()));
        final Exception exception4 = assertThrows(Exception.class, () -> userService.create(villager, "@.com", "", Set.of()));
        final Exception exception5 = assertThrows(Exception.class, () -> userService.create(villager, "1@.com", "", Set.of()));
        assertEquals(expectedMessage, exception1.getMessage());
        assertEquals(expectedMessage, exception2.getMessage());
        assertEquals(expectedMessage, exception3.getMessage());
        assertEquals(expectedMessage, exception4.getMessage());
        assertEquals(expectedMessage, exception5.getMessage());
    }

    @Test
    public void createUserThrowErrorOnInvalidPassword() {
        Villager villager = new Villager();
        String email = "teste@teste.com";
        final String expectedMessage = "Invalid password. \n" +
                "Must have 8+ characters containing:\n" +
                "● 1+ Uppercase\n" +
                "● 1+ Lowercase\n" +
                "● 1+ Special character\n" +
                "● 1+ Number\n";

        final Exception exception1 = assertThrows(Exception.class, () -> userService.create(villager, email, null, Set.of()));
        final Exception exception2 = assertThrows(Exception.class, () -> userService.create(villager, email, "", Set.of()));
        final Exception exception3 = assertThrows(Exception.class, () -> userService.create(villager, email, "12345678", Set.of()));
        final Exception exception4 = assertThrows(Exception.class, () -> userService.create(villager, email, "a1234567", Set.of()));
        final Exception exception5 = assertThrows(Exception.class, () -> userService.create(villager, email, "a12A34567", Set.of()));
        final Exception exception6 = assertThrows(Exception.class, () -> userService.create(villager, email, "a12A34!", Set.of()));
        assertEquals(expectedMessage, exception1.getMessage());
        assertEquals(expectedMessage, exception2.getMessage());
        assertEquals(expectedMessage, exception3.getMessage());
        assertEquals(expectedMessage, exception4.getMessage());
        assertEquals(expectedMessage, exception5.getMessage());
        assertEquals(expectedMessage, exception6.getMessage());
    }

    @Test
    public void deleteUserByIdSuccessfully() {
        doNothing().when(userRepository).deleteById(1L);
        assertDoesNotThrow(()->userService.delete(1L));
        verify(userRepository, atLeastOnce()).deleteById(1L);
    }

    @Test
    public void deleteUserByIdThrowsError() {
        doThrow(IllegalArgumentException.class).when(userRepository).deleteById(null);
        assertThrows(IllegalArgumentException.class, ()->userService.delete(null));
    }

    @Test
    public void getUserDataByEmailSuccessfully() throws SQLException {
        Role mockedRole = mock(Role.class);
        Villager mockedVillager = mock(Villager.class);
        String email = "test@test.com";

        User mockedUser = mock(User.class);
        when(mockedUser.getId()).thenReturn(1L);
        when(mockedUser.getEmail()).thenReturn(email);
        when(mockedUser.getPassword()).thenReturn("");
        when(mockedUser.getVillager()).thenReturn(mockedVillager);

        final Optional<User> optionalUser = Optional.of(mockedUser);

        when(mockedRole.getName()).thenReturn("ADMIN");
        when(mockedUser.getRoles()).thenReturn(Set.of(mockedRole));
        when(userRepository.findOneByEmail(email)).thenReturn(optionalUser);

        UserTO response = userService.getUser(email);

        verify(userRepository, atLeastOnce()).findOneByEmail(email);
        assertNotNull(response);
        assertEquals(email, response.getEmail());
    }
}
