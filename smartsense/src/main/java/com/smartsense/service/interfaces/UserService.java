package com.smartsense.service.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.smartsense.dto.user.UpdateUserDTO;
import com.smartsense.dto.user.UserDTO;

/**
 * Service interface for User entity.
 * Defines methods for CRUD operations and additional business logic.
 */
public interface UserService {

   UserDTO getUserById(String id);

   UserDTO getUserById(String id, String... with);

   public UserDTO getByUserName(String userName);

   Page<UserDTO> getAllUsers(Pageable pageable);

   Page<UserDTO> getAllUsers(Pageable pageable, String... with);

   UserDTO addUser(UserDTO User);

   public UserDTO updateUser(String UserId, UpdateUserDTO User, String... with);

   public void deleteUserById(String UserId);
}
