package dev.chel_shev.nelly.controller;

import dev.chel_shev.nelly.entity.UserEntity;
import dev.chel_shev.nelly.security.JwtTokenProvider;
import dev.chel_shev.nelly.security.dto.JwtResponse;
import dev.chel_shev.nelly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import static java.util.Objects.isNull;

@RestController
@CrossOrigin
@RequiredArgsConstructor
public class JwtAuthenticationController {

    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    @ResponseBody
    public ResponseEntity<?> login(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
            UserEntity userEntity = userService.getUserByName(username);
            if (isNull(userEntity))
                throw new UsernameNotFoundException("User not found!");
            String token = jwtTokenProvider.createToken(username);
            return ResponseEntity.ok(new JwtResponse(username, token));
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }
}
