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

        Villager villager = mock(Villager.class);
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

    @Test
    public void getUserDataByEmailErrors() throws SQLException {
        String email = null;
        when(userRepository.findOneByEmail(email)).thenThrow(SQLException.class);
        assertThrows(SQLException.class, () -> userService.getUser(email));
        verify(userRepository, atLeastOnce()).findOneByEmail(email);
    }

    @Test
    public void testUpdatePasswordSuccessfully() throws Exception {
        String email = "teste@teste.com";
        String password = "1A2b3!45678";
        User mockedUser = mock(User.class);
        when(userRepository.findOneByEmail(email)).thenReturn(Optional.of(mockedUser));

        assertDoesNotThrow(() -> userService.updatePassword(email, password));
    }

    @Test
    public void testUpdatePasswordInvalidArgumetnsExpectError() {
        final Exception exception1 = assertThrows(Exception.class, () -> userService.updatePassword(null, ""));
        final Exception exception2 = assertThrows(Exception.class, () -> userService.updatePassword("", ""));
        final Exception exception3 = assertThrows(Exception.class, () -> userService.updatePassword( "t@t", ""));
        final Exception exception4 = assertThrows(Exception.class, () -> userService.updatePassword( "t@.com", ""));
        final Exception exception5 = assertThrows(Exception.class, () -> userService.updatePassword( "@.com", ""));
        final Exception exception6 = assertThrows(Exception.class, () -> userService.updatePassword( "1@.com", ""));
        final String expectedMessage = "Invalid username. Must be a valid email";
        assertEquals(expectedMessage, exception1.getMessage());
        assertEquals(expectedMessage, exception2.getMessage());
        assertEquals(expectedMessage, exception3.getMessage());
        assertEquals(expectedMessage, exception4.getMessage());
        assertEquals(expectedMessage, exception5.getMessage());
        assertEquals(expectedMessage, exception6.getMessage());

        final Exception exception7 = assertThrows(Exception.class, () -> userService.updatePassword("teste@teste.com", null));
        final Exception exception8 = assertThrows(Exception.class, () -> userService.updatePassword("teste@teste.com", ""));
        final Exception exception9 = assertThrows(Exception.class, () -> userService.updatePassword( "teste@teste.com", "12345678"));
        final Exception exception10 = assertThrows(Exception.class, () -> userService.updatePassword( "teste@teste.com", "a1234567"));
        final Exception exception11 = assertThrows(Exception.class, () -> userService.updatePassword( "teste@teste.com", "a1A3456"));
        final Exception exception12 = assertThrows(Exception.class, () -> userService.updatePassword( "teste@teste.com", "a!123456"));
        final String expectedPasswordMessage = "Invalid password. \n" +
                "Must have 8+ characters containing:\n" +
                "● 1+ Uppercase\n" +
                "● 1+ Lowercase\n" +
                "● 1+ Special character\n" +
                "● 1+ Number\n";
        assertEquals(expectedPasswordMessage, exception7.getMessage());
        assertEquals(expectedPasswordMessage, exception8.getMessage());
        assertEquals(expectedPasswordMessage, exception9.getMessage());
        assertEquals(expectedPasswordMessage, exception10.getMessage());
        assertEquals(expectedPasswordMessage, exception11.getMessage());
        assertEquals(expectedPasswordMessage, exception12.getMessage());
    }

    @Test
    public void getVillagerByIdSuccessfully() throws Exception {
        Long villagerId = 1L;

        Villager mockedVillager = mock(Villager.class);
        when(mockedVillager.getId()).thenReturn(1L);

        User mockedUser = mock(User.class);
        when(mockedUser.getId()).thenReturn(1L);
        when(mockedUser.getEmail()).thenReturn("");
        when(mockedUser.getPassword()).thenReturn("");
        when(mockedUser.getVillager()).thenReturn(mockedVillager);

        Role mockedRole = mock(Role.class);
        when(mockedRole.getName()).thenReturn("ADMIN");

        final Optional<User> optionalUser = Optional.of(mockedUser);
        when(mockedUser.getRoles()).thenReturn(Set.of(mockedRole));
        when(userRepository.findOneByVillagerId(1L)).thenReturn(optionalUser);

        UserTO response = userService.getByVillagerId(1L);
        verify(userRepository, atLeastOnce()).findOneByVillagerId(1L);
        assertNotNull(response);
    }

    @Test
    public void returnErrorOnNullVillagerId() throws Exception {
        final Exception exception1 = assertThrows(Exception.class, () -> userService.getByVillagerId(null));
        assertEquals("Invalid argument. VillagerId cannot be null", exception1.getMessage());

        when(userRepository.findOneByVillagerId(1L)).thenReturn(Optional.empty());
        final Exception exception2 = assertThrows(Exception.class, () -> userService.getByVillagerId(1L));
        assertEquals("No user found for villager with id 1", exception2.getMessage());
    }
}
