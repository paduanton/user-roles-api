package com.ecore.roles.service;

import com.ecore.roles.model.Role;

import java.util.List;
import java.util.UUID;

public interface RolesService {


    Role GetRole(UUID id);

    List<Role> GetRoles();

    List<Role> GetRolesByUserIdAndTeamId(UUID userId, UUID teamId);

    Role CreateRole(Role role);
}
