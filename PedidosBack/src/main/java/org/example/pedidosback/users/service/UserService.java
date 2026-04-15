package org.example.pedidosback.users.service;

import org.example.pedidosback.users.DTO.UserRequest;
import org.example.pedidosback.users.domain.User;

public interface UserService {

    public User register(UserRequest user);
}
