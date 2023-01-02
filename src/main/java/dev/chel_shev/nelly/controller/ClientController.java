package dev.chel_shev.nelly.controller;

import dev.chel_shev.nelly.entity.users.UserEntity;
import dev.chel_shev.nelly.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/user")
@Controller
@RequiredArgsConstructor
public class ClientController {

    private final UserService userService;

    @ResponseBody
    @PostMapping(value = "/signup", produces = MediaType.APPLICATION_JSON_VALUE)
    UserEntity signUp(@RequestBody UserEntity userEntity) {
        return userService.signUp(userEntity);
    }

    void signIn() {

    }

    void changePassword() {

    }

    void changeName() {

    }

    void changeEmail() {

    }
}
