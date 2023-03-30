package com.ecore.roles.web.rest;

import com.ecore.roles.model.Membership;
import com.ecore.roles.service.MembershipsService;
import com.ecore.roles.web.MembershipsApi;
import com.ecore.roles.web.dto.MembershipDto;
import lombok.RequiredArgsConstructor;
import java.util.stream.Collectors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

import static com.ecore.roles.web.dto.MembershipDto.fromModel;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/v1/roles/memberships")
public class MembershipsRestController implements MembershipsApi {

    private final MembershipsService membershipsService;

    @Override
    @PostMapping(consumes = {"application/json"}, produces = {"application/json"})
    public ResponseEntity<MembershipDto> assignRoleToMembership(
            @NotNull @Valid @RequestBody MembershipDto membershipDto) {
        Membership membership = membershipsService.assignRoleToMembership(membershipDto.toModel());
        return ResponseEntity
                .status(201)
                .body(fromModel(membership));
    }

    @Override
    @GetMapping(path = "/search", produces = {"application/json"})
    public ResponseEntity<List<MembershipDto>> getMembershipsByRoleId(@NotNull @RequestParam UUID roleId) {
        return ResponseEntity
                .status(200)
                .body(membershipsService.getMembershipsByRoleId(roleId).stream()
                        .map(MembershipDto::fromModel)
                        .collect(Collectors.toList()));
    }

}
