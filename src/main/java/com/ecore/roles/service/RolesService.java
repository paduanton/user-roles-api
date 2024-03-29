package com.ecore.roles.service;

import com.ecore.roles.model.Role;

import java.util.List;
import java.util.UUID;

public interface RolesService {

    Role getRole(UUID id);

    List<Role> getRoles();

    Role getRoleByUserIdAndTeamId(UUID userId, UUID teamId);

    Role createRole(Role role);
}
