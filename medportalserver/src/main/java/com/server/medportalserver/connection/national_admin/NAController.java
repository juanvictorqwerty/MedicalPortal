package com.server.medportalserver.connection.national_admin;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class NAController {

    @GetMapping("/sign-up-supreme-admin")
    public String signUpSupremeAdmin() {
        return null;

    }

}
