package com.smartsense.service;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.smartsense.dto.role.RoleDTO;
import com.smartsense.model.Role;
import com.smartsense.repository.RoleRepository;
import com.smartsense.service.interfaces.RoleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Service implementation for User entity.
 * Defines methods for CRUD operations and additional business logic.
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    @Override
    public Role getRoleById(String id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
    }

    @Override
    public Set<Role> getAllRolesByName(Set<RoleDTO> roles) {
        roles.forEach(role -> log.info("Role : " + role.toString()));
        Set<Role> roleEntities = new HashSet<>();
        try {
            Iterable<String> roleNames = roles
                    .stream()
                    .map(RoleDTO::getName)
                    .collect(Collectors.toList());

            roleRepository.findAllByNameIn(roleNames).forEach(roleEntities::add);
            roleEntities.forEach(role -> log.info("Role : " + role.toString()));
        } catch (Exception e) {
            log.error("Error fetching roles", e);
        }

        return roleEntities;
    }

    @Override
    public Role getRoleByName(String name) {
        return roleRepository.findByName(name)
                .orElseThrow(() -> new IllegalArgumentException("Role not found"));
    }

}
