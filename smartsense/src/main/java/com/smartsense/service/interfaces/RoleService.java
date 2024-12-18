package com.smartsense.service.interfaces;

import java.util.Set;

import com.smartsense.dto.role.RoleDTO;
import com.smartsense.model.Role;

/**
 * Service interface for Role entity.
 * Defines methods for CRUD operations and additional business logic.
 */
public interface RoleService {

   Role getRoleById(String id);

   Set<Role> getAllRolesByName(Set<RoleDTO> roles);

   Role getRoleByName(String name);

}
