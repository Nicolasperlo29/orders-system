package org.example.pedidosback.users.service.implementation;

import lombok.RequiredArgsConstructor;
import org.example.pedidosback.users.DTO.UserRequest;
import org.example.pedidosback.users.domain.User;
import org.example.pedidosback.users.domain.UserType;
import org.example.pedidosback.users.repository.UserRepository;
import org.example.pedidosback.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository repository;

    @Override
    public User register(UserRequest userRequest) {

        String email = userRequest.getEmail();

        if (repository.existsByEmail(email)) {
            throw new IllegalArgumentException("Ya esta registrado el email");
        }

        User user = new User();
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setName(userRequest.getName());
        user.setType(UserType.CLIENT);

        return repository.save(user);
    }
}
