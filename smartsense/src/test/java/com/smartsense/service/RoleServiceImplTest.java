package com.smartsense.service;

import com.smartsense.dto.role.RoleDTO;
import com.smartsense.model.Role;
import com.smartsense.repository.RoleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;

    @InjectMocks
    private RoleServiceImpl roleService;

    private Role role;
    private RoleDTO roleDTO;

    @BeforeEach
    void setUp() {
        role = new Role();
        role.setId("1");
        role.setName("ROLE_USER");

        roleDTO = new RoleDTO();
        roleDTO.setName("ROLE_USER");
    }

    @Test
    void getRoleById_ShouldReturnRole_WhenExists() {
        when(roleRepository.findById("1")).thenReturn(Optional.of(role));

        Role result = roleService.getRoleById("1");

        assertNotNull(result);
        assertEquals("ROLE_USER", result.getName());
    }

    @Test
    void getRoleById_ShouldThrowException_WhenNotFound() {
        when(roleRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class,
                () -> roleService.getRoleById("1"));
    }

    @Test
    void getAllRolesByName_ShouldReturnRoles() {
        Set<RoleDTO> roleDTOs = new HashSet<>();
        roleDTOs.add(roleDTO);

        Set<Role> roles = new HashSet<>();
        roles.add(role);

        when(roleRepository.findAllByNameIn(any())).thenReturn(roles);

        Set<Role> result = roleService.getAllRolesByName(roleDTOs);

        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void getRoleByName_ShouldReturnRole_WhenExists() {
        when(roleRepository.findByName("ROLE_USER")).thenReturn(Optional.of(role));

        Role result = roleService.getRoleByName("ROLE_USER");

        assertNotNull(result);
        assertEquals("ROLE_USER", result.getName());
    }
}