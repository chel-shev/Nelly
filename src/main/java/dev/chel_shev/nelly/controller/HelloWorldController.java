package dev.chel_shev.nelly.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class HelloWorldController {

    @GetMapping("protected/dashboard")
    public String secondPage() {
        return "success";
    }

    @GetMapping("/dashboard")
    public String firstPage() {
        return "success";
    }
}
