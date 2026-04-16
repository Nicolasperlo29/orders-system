package org.example.pedidosback.users.DTO;

import lombok.Data;

@Data
public class LoginRequest {

    private String email;
    private String password;
}
