package com.simplevat.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserRestController {

    @GetMapping("/users")
    public void retrieveAllUsers() {
        System.out.println("users===");
    }

}
