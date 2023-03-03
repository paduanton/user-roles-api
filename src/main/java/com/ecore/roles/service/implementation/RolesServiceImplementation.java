package com.ecore.roles.service.implementation;

import com.ecore.roles.exception.ResourceExistsException;
import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.model.Role;
import com.ecore.roles.repository.MembershipRepository;
import com.ecore.roles.repository.RoleRepository;
import com.ecore.roles.service.MembershipsService;
import com.ecore.roles.service.RolesService;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Log4j2
@Service
public class RolesServiceImplementation implements RolesService {

    public static final String DEFAULT_ROLE = "Developer";

    private final RoleRepository roleRepository;
    private final MembershipRepository membershipRepository;
    private final MembershipsService membershipsService;

    @Autowired
    public RolesServiceImplementation(
            RoleRepository roleRepository,
            MembershipRepository membershipRepository,
            MembershipsService membershipsService) {
        this.roleRepository = roleRepository;
        this.membershipRepository = membershipRepository;
        this.membershipsService = membershipsService;
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
    public List<Role> getRolesByUserIdAndTeamId(UUID userId, UUID teamId) {
        List<Role> roles;

        if (userId != null && teamId != null) {
            roles = roleRepository.findByUserIdAndTeamId(userId, teamId);
        } else if (userId != null) {
            roles = roleRepository.findByUserId(userId);
        } else if (teamId != null) {
            roles = roleRepository.findByTeamId(teamId);
        } else {
            roles = roleRepository.findAll();
        }

        return roles;
    }
}
