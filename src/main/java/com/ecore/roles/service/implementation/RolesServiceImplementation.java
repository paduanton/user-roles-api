package com.ecore.roles.service.implementation;

import com.ecore.roles.exception.ResourceExistsException;
import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.model.Role;
import com.ecore.roles.repository.RoleRepository;
import com.ecore.roles.service.RolesService;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecore.roles.client.TeamsClient;
import com.ecore.roles.client.model.Team;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
public class RolesServiceImplementation implements RolesService {

    public static final String DEFAULT_ROLE = "Developer";

    private final RoleRepository roleRepository;
    private final TeamsClient teamsClient;

    @Autowired
    public RolesServiceImplementation(
            RoleRepository roleRepository,
            TeamsClient teamsClient) {
        this.roleRepository = roleRepository;
        this.teamsClient = teamsClient;
    }

    @Override
    public Role createRole(@NonNull Role role) {
        if (roleRepository.findByName(role.getName()).isPresent()) {
            throw new ResourceExistsException(Role.class);
        }
        return roleRepository.save(role);
    }

    @Override
    public Role getRole(@NonNull UUID roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException(Role.class, roleId));
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Role getRoleByUserIdAndTeamId(UUID userId, UUID teamId) {
        if (teamsClient.getTeam(teamId).getBody() == null) {
            throw new ResourceNotFoundException(Team.class, teamId);
        }

        return roleRepository.findByUserIdAndTeamId(userId, teamId);
    }
}
