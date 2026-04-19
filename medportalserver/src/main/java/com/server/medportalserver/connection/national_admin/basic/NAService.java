package com.server.medportalserver.connection.national_admin.basic;

import org.springframework.stereotype.Service;
import com.server.medportalserver.model.auth.User;
import com.server.medportalserver.model.auth.ConfirmationToken;
import java.time.LocalDateTime;
import com.server.medportalserver.connection._common.GenerateRandomCode;
import com.server.medportalserver.connection.national_admin.confirm.ConfirmationTokenRepo;
import com.server.medportalserver.connection._common.email.EmailService;

@Service
public class NAService {
    private final NARepo naRepo;
    private final ConfirmationTokenRepo confirmationTokenRepo;
    private final EmailService emailService;
    private final GenerateRandomCode generateRandomCode;

    public NAService(NARepo naRepo, ConfirmationTokenRepo confirmationTokenRepo, EmailService emailService, GenerateRandomCode generateRandomCode) {
        this.naRepo = naRepo;
        this.confirmationTokenRepo = confirmationTokenRepo;
        this.emailService = emailService;
        this.generateRandomCode = generateRandomCode;
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
            String code = generateRandomCode.generateAdminConfirmCode();

            ConfirmationToken token = ConfirmationToken.builder()
                    .user(user)
                    .token(code)
                    .tokenType("ADMIN_CONFIRM")
                    .expiresAt(LocalDateTime.now().plusDays(1))
                    .build();
            confirmationTokenRepo.save(token);

            // Send confirmation email
            String subject = "Confirm Your Medical Portal Account";
            String body = String.format(
                    "Dear %s,\n\n" +
                    "Welcome to the Medical Portal! To complete your registration, please use the following confirmation token:\n\n" +
                    "TOKEN: %s\n\n" +
                    "If you did not request this, please ignore this email.\n\n" +
                    "Best regards,\n" +
                    "Medical Portal Team",
                    user.getName(), code);
            
            emailService.sendSimpleMessage(user.getEmail(), subject, body);

            return "{\"success\":true,\"message\":\"Supreme Admin signed up successfully. Please check your email for the confirmation token.\"}";
        } catch (Exception e) {
            return "{\"success\":false,\"message\":\"Failed to sign up Supreme Admin\"}";
        }
    }

}
