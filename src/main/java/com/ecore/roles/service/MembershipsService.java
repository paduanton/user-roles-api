package com.ecore.roles.service;

import com.ecore.roles.exception.ResourceNotFoundException;
import com.ecore.roles.model.Membership;

import java.util.List;
import java.util.UUID;

public interface MembershipsService {

    List<Membership> getMembershipsByRoleId(UUID roleId);

    Membership assignRoleToMembership(Membership membership) throws ResourceNotFoundException;
}
