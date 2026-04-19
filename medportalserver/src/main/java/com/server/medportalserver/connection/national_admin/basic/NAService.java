package com.server.medportalserver.connection.national_admin.basic;

import org.springframework.stereotype.Service;
import com.server.medportalserver.model.auth.User;
import com.server.medportalserver.model.auth.ConfirmationToken;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.server.medportalserver.connection.GenerateRandomCode;
import com.server.medportalserver.connection.national_admin.confirm.ConfirmationTokenRepo;

@Service
public class NAService {
    private final NARepo naRepo;
    private final ConfirmationTokenRepo confirmationTokenRepo;

    public NAService(NARepo naRepo, ConfirmationTokenRepo confirmationTokenRepo) {
        this.naRepo = naRepo;
        this.confirmationTokenRepo = confirmationTokenRepo;
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
            GenerateRandomCode generateRandomCode = new GenerateRandomCode();
            String code = generateRandomCode.generateAdminConfirmCode();

            ConfirmationToken token = ConfirmationToken.builder()
                    .user(user)
                    .token(code)
                    .tokenType("ADMIN_CONFIRM")
                    .expiresAt(LocalDateTime.now().plusDays(1))
                    .build();
            confirmationTokenRepo.save(token);

            return "Supreme Admin signed up successfully";
        } catch (Exception e) {
            return "Failed to sign up Supreme Admin";
        }
    }

}
