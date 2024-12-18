package com.smartsense.mapper;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.smartsense.dto.role.RoleDTO;
import com.smartsense.dto.user.UserDTO;
import com.smartsense.exceptions.InvalidDataException;
import com.smartsense.model.Role;
import com.smartsense.model.User;
import com.smartsense.service.interfaces.RoleService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserMapper {
    private final List<String> VALID_INCLUDES = Arrays.asList("role");

    private final RoleService roleService;

    public void verifyIncludes(String... with)
            throws InvalidDataException {
        List<String> includesList = Arrays.asList(with);

        for (String include : includesList) {
            if (!include.isEmpty() && !VALID_INCLUDES.contains(include)) {
                throw new InvalidDataException("Invalid include: " + include);
            }
        }
    }

    public User convertToEntity(UserDTO userDTO) {
        log.info("User DTO: " + userDTO.toString());
        Set<Role> roles = roleService.getAllRolesByName(userDTO.getRoles());

        return User.builder()
                .email(userDTO.getEmail())
                .username(userDTO.getUsername())
                .password(userDTO.getPassword())
                .roles(roles)
                .build();
    }

    public UserDTO convertToDTO(User user) {
        return UserDTO.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .build();
    }

    public List<UserDTO> convertToDTOList(List<User> users) {
        return users.stream()
                .map(user -> convertToDTO(user))
                .collect(Collectors.toList());
    }

    public UserDTO convertToDTO(User user, String... with) {
        List<String> includesList = Arrays.asList(with);

        Set<RoleDTO> roleDTOs = null;

        if (includesList.contains("role")) {
            Set<Role> roles = user.getRoles();
            roleDTOs = roles.stream().map(role -> RoleDTO.builder().name(role.getName()).build())
                    .collect(Collectors.toSet());
        }

        return UserDTO.builder()
                .email(user.getEmail())
                .username(user.getUsername())
                .roles(roleDTOs)
                .build();
    }

    public List<UserDTO> convertToDTOList(List<User> users, String... with) {
        return users.stream()
                .map(user -> convertToDTO(user, with))
                .collect(Collectors.toList());
    }
}
