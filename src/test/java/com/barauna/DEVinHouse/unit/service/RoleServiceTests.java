package com.barauna.DEVinHouse.unit.service;

import com.barauna.DEVinHouse.entity.Role;
import com.barauna.DEVinHouse.repository.RoleRepository;
import com.barauna.DEVinHouse.service.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoleServiceTests {

    private RoleService roleService;
    private RoleRepository roleRepository;


    @BeforeEach()
    public void beforeEach(){
        this.roleRepository = mock(RoleRepository.class);
        this.roleService = new RoleService(roleRepository);
    }

    @Test
    public void throwsExceptionOnEmptyAttr() {
        assertThrows(Exception.class, () -> roleService.getByNames(Set.of()));
        assertThrows(Exception.class, () -> roleService.getByNames(null));
    }

    @Test
    public void expectRolesAttr() throws Exception {
        Set<String> names = Set.of("Aqui");
        when(roleRepository.findAllByNameIn(names)).thenReturn(List.of(new Role(1L, "Aqui", "")));

        List<Role> roles = roleService.getByNames(names);

        verify(roleRepository, atLeastOnce()).findAllByNameIn(names);
        assertEquals(1, roles.size());
        assertTrue(names.contains(roles.get(0).getName()));
    }

    @Test
    public void findById() throws Exception {
        final Role expectedRole = new Role(1L, "Aqui", "");
        when(roleRepository.findById(1L)).thenReturn(Optional.of(expectedRole));
        final Role role = roleService.find(1L);

        assertEquals(role, expectedRole);
    }
}
