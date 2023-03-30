package com.ecore.roles.service.implementation;

import com.ecore.roles.exception.InvalidArgumentException;
import com.ecore.roles.exception.ResourceExistsException;
import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.exception.InvalidObjectException;
import com.ecore.roles.model.Membership;
import com.ecore.roles.model.Role;
import com.ecore.roles.client.model.Team;
import com.ecore.roles.repository.MembershipRepository;
import com.ecore.roles.repository.RoleRepository;
import com.ecore.roles.service.MembershipsService;
import lombok.NonNull;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ecore.roles.client.TeamsClient;

import java.util.List;
import java.util.UUID;

import static java.util.Optional.ofNullable;

@Log4j2
@Service
public class MembershipsServiceImplementation implements MembershipsService {

    private final MembershipRepository membershipRepository;
    private final RoleRepository roleRepository;
    private final TeamsClient teamsClient;

    @Autowired
    public MembershipsServiceImplementation(
            TeamsClient teamsClient,
            MembershipRepository membershipRepository,
            RoleRepository roleRepository) {
        this.membershipRepository = membershipRepository;
        this.roleRepository = roleRepository;
        this.teamsClient = teamsClient;
    }

    @Override
    public Membership assignRoleToMembership(@NonNull Membership membership) {
        UUID roleId = ofNullable(membership.getRole()).map(Role::getId)
                .orElseThrow(() -> new InvalidArgumentException(Role.class));
        UUID teamId = membership.getTeamId();
        UUID userId = membership.getUserId();

        if (membershipRepository.findByUserIdAndTeamId(membership.getUserId(), teamId)
                .isPresent()) {
            throw new ResourceExistsException(Membership.class);
        }

        roleRepository.findById(roleId).orElseThrow(() -> new ResourceNotFoundException(Role.class, roleId));

        if (teamsClient.getTeam(teamId).getBody() == null) {
            throw new ResourceNotFoundException(Team.class, teamId);
        }

        if (!teamsClient.getTeam(teamId).getBody().getTeamMemberIds().contains(userId)) {
            throw new InvalidObjectException(Membership.class,
                    "The provided user doesn't belong to the provided team");
        }

        return membershipRepository.save(membership);
    }

    @Override
    public List<Membership> getMembershipsByRoleId(@NonNull UUID roleId) {
        return membershipRepository.findByRoleId(roleId);
    }

}
