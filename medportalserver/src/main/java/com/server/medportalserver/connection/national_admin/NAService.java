package com.server.medportalserver.connection.national_admin;

import org.springframework.stereotype.Service;
import com.server.medportalserver.model.auth.User;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NAService {
    private final NARepo naRepo;

    public NAService(NARepo naRepo) {
        this.naRepo = naRepo;
    }

    public String signUpSupremeAdmin(SupremeAdminRequest request) {
        User user = User.builder()
                .email(request.getEmail())
                .name(request.getName())
                .phone(request.getPhone())
                .password(request.getPassword())
                .build();

        try {
            naRepo.save(user);
            return "Supreme Admin signed up successfully";
        } catch (Exception e) {
            return "Failed to sign up Supreme Admin";
        }
    }

}
