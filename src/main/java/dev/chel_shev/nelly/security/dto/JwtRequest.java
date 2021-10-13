package dev.chel_shev.nelly.security.dto;

import lombok.Data;

import java.io.Serializable;

@Data
public class JwtRequest implements Serializable {

    private final String username;
    private final String password;
}