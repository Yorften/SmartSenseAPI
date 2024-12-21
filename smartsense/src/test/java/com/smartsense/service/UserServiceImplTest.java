package com.smartsense.service;

import com.smartsense.dto.user.UpdateUserDTO;
import com.smartsense.dto.user.UserDTO;
import com.smartsense.exceptions.ResourceNotFoundException;
import com.smartsense.mapper.UserMapper;
import com.smartsense.model.User;
import com.smartsense.repository.UserRepository;
import com.smartsense.service.interfaces.RoleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleService roleService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;
    private UserDTO userDTO;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId("1");
        user.setUsername("testUser");
        user.setEmail("test@test.com");

        userDTO = new UserDTO();
        userDTO.setId("1");
        userDTO.setUsername("testUser");
        userDTO.setEmail("test@test.com");
    }

    @Test
    void getUserById_ShouldReturnUser_WhenExists() {
        when(userRepository.findById("1")).thenReturn(Optional.of(user));
        when(userMapper.convertToDTO(any(User.class))).thenReturn(userDTO);

        UserDTO result = userService.getUserById("1");

        assertNotNull(result);
        assertEquals("1", result.getId());
        verify(userRepository).findById("1");
    }

    @Test
    void getUserById_ShouldThrowException_WhenNotFound() {
        when(userRepository.findById("1")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class,
                () -> userService.getUserById("1"));
    }

    @Test
    void addUser_ShouldSaveAndReturnUser() {
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userMapper.convertToEntity(any(UserDTO.class))).thenReturn(user);
        when(userRepository.save(any(User.class))).thenReturn(user);
        when(userMapper.convertToDTO(any(User.class))).thenReturn(userDTO);

        UserDTO result = userService.addUser(userDTO);

        assertNotNull(result);
        verify(userRepository).save(any(User.class));
        verify(passwordEncoder).encode(any());
    }

    @Test
    void getAllUsers_ShouldReturnPageOfUsers() {
        Page<User> userPage = new PageImpl<>(Collections.singletonList(user));
        PageRequest pageRequest = PageRequest.of(0, 10);

        when(userRepository.findAll(pageRequest)).thenReturn(userPage);
        when(userMapper.convertToDTO(any(User.class))).thenReturn(userDTO);

        Page<UserDTO> result = userService.getAllUsers(pageRequest);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        verify(userRepository).findAll(pageRequest);
    }
}