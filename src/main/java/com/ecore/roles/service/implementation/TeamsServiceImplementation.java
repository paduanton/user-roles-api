package com.ecore.roles.service.implementation;

import com.ecore.roles.client.TeamsClient;
import com.ecore.roles.client.model.Team;
import com.ecore.roles.service.TeamsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TeamsServiceImplementation implements TeamsService {

    private final TeamsClient teamsClient;

    @Autowired
    public TeamsServiceImplementation(TeamsClient teamsClient) {
        this.teamsClient = teamsClient;
    }

    public Team getTeam(UUID id) {
        return teamsClient.getTeam(id).getBody();
    }

    public List<Team> getTeams() {
        return teamsClient.getTeams().getBody();
    }
}
