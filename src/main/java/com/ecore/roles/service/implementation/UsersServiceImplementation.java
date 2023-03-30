package com.ecore.roles.service.implementation;

import com.ecore.roles.client.UsersClient;
import com.ecore.roles.client.model.User;
import com.ecore.roles.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UsersServiceImplementation implements UsersService {

    private final UsersClient usersClient;

    @Autowired
    public UsersServiceImplementation(UsersClient usersClient) {
        this.usersClient = usersClient;
    }

    public User getUser(UUID id) {
        return usersClient.getUser(id).getBody();
    }

    public List<User> getUsers() {
        return usersClient.getUsers().getBody();
    }
}
