package com.ecore.roles.web.rest;

import com.ecore.roles.model.Role;
import com.ecore.roles.service.RolesService;
import com.ecore.roles.web.RolesApi;
import com.ecore.roles.web.dto.RoleDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.stream.Collectors;
import com.ecore.roles.exception.InvalidArgumentException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import java.util.List;
import java.util.UUID;
import static com.ecore.roles.web.dto.RoleDto.fromModel;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/roles")
public class RolesRestController implements RolesApi {

    private final RolesService rolesService;

    @Override
    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<RoleDto> createRole(
            @Valid @RequestBody RoleDto role) {
        return ResponseEntity
                .status(201)
                .body(fromModel(rolesService.createRole(role.toModel())));
    }

    @Override
    @GetMapping(produces = {"application/json"})
    public ResponseEntity<List<RoleDto>> getRoles() {
        return ResponseEntity
                .status(200)
                .body(rolesService.getRoles().stream()
                        .map(RoleDto::fromModel)
                        .collect(Collectors.toList()));
    }

    @Override
    @GetMapping(path = "/{roleId}", produces = {"application/json"})
    public ResponseEntity<RoleDto> getRole(
            @PathVariable UUID roleId) {
        return ResponseEntity
                .status(200)
                .body(fromModel(rolesService.getRole(roleId)));
    }

    @Override
    @GetMapping(path = "/search", produces = {"application/json"})
    public ResponseEntity<RoleDto> getRoleByUserIdAndTeamId(
            @NotNull @RequestParam UUID userId,
            @NotNull @RequestParam UUID teamId) {

        if (userId == null || teamId == null) {
            throw new InvalidArgumentException(Role.class);
        }

        return ResponseEntity
                .status(200)
                .body(fromModel(rolesService.getRoleByUserIdAndTeamId(userId, teamId)));
    }

}
