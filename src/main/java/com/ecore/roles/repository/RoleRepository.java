package com.ecore.roles.repository;

import com.ecore.roles.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RoleRepository extends JpaRepository<Role, UUID> {

    @Query("SELECT r FROM Role r LEFT JOIN r.membership m " +
            "WHERE (m.userId = :userId) AND (m.teamId = :teamId)")
    Role findByUserIdAndTeamId(UUID userId, UUID teamId);

    Optional<Role> findByName(String name);
}
