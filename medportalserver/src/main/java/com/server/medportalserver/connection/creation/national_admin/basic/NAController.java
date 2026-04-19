package com.server.medportalserver.connection.creation.national_admin.basic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class NAController {

    @Autowired
    private NAService naService;

    @PostMapping("/sign-up-supreme-admin")
    public String signUpSupremeAdmin(@RequestBody SupremeAdminRequest request) {
        return naService.signUpSupremeAdmin(request);
    }

}
