package com.smartsense.dto.user;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import com.smartsense.dto.role.RoleDTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserDTO {

    @Builder.Default
    @NotNull(message = "Role shouldn't be null")
    private Set<RoleDTO> roles = new HashSet<>();

}
