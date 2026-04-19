package com.server.medportalserver.connection.national_admin.confirm;

import com.server.medportalserver.connection._common.GenerateRandomCode;
import com.server.medportalserver.connection._common.email.EmailService;
import com.server.medportalserver.connection.national_admin.basic.NARepo;
import com.server.medportalserver.connection.national_admin.basic.RefreshedTokenRepo;
import com.server.medportalserver.model.auth.ConfirmationToken;
import com.server.medportalserver.model.auth.RefreshedToken;
import com.server.medportalserver.model.auth.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class ConfirmAdminService {
    @Autowired
    private NARepo naRepo;
    @Autowired
    private ConfirmationTokenRepo confirmationTokenRepo;
    @Autowired
    private RefreshedTokenRepo refreshedTokenRepo;
    @Autowired
    private EmailService emailService;
    @Autowired
    private GenerateRandomCode generateRandomCode;

    public String resendToken(ResendTokenRequest request) {
        try {
            Optional<User> userOpt = naRepo.findByEmail(request.getEmail());

            if (userOpt.isEmpty()) {
                return "{\"success\":false,\"message\":\"Account with this email does not exist\"}";
            }

            User user = userOpt.get();

            if (user.isVerified()) {
                return "{\"success\":false,\"message\":\"Account is already verified\"}";
            }

            String code = generateRandomCode.generateAdminConfirmCode();

            ConfirmationToken token = ConfirmationToken.builder()
                    .user(user)
                    .token(code)
                    .tokenType("ADMIN_CONFIRM")
                    .expiresAt(LocalDateTime.now().plusHours(24))
                    .build();
            confirmationTokenRepo.save(token);

            // Send confirmation email
            String subject = "New Confirmation Token - Medical Portal";
            String body = String.format(
                    "Dear %s,\n\n" +
                    "You requested a new confirmation token. Please use the following token to complete your registration:\n\n" +
                    "TOKEN: %s\n\n" +
                    "This token is valid for 24 hours.\n\n" +
                    "Best regards,\n" +
                    "Medical Portal Team",
                    user.getName(), code);

            emailService.sendSimpleMessage(user.getEmail(), subject, body);

            return "{\"success\":true,\"message\":\"A new confirmation token has been sent to your email.\"}";
        } catch (Exception e) {
            return "{\"success\":false,\"message\":\"Internal Server Error\"}";
        }
    }

    public String confirmAdmin(ConfirmAdmin request) {
        try {
            Optional<ConfirmationToken> tokenOpt = confirmationTokenRepo.findByToken(request.getToken());

            if (tokenOpt.isEmpty()) {
                return "{\"success\":false,\"message\":\"Invalid token\"}";
            }

            ConfirmationToken token = tokenOpt.get();
            User user = token.getUser();

            if (!user.getEmail().equals(request.getEmail())) {
                return "{\"success\":false,\"message\":\"Email does not match token\"}";
            }

            if (token.is_used()) {
                return "{\"success\":false,\"message\":\"Token has already been used\"}";
            }

            if (token.getExpiresAt().isBefore(LocalDateTime.now()) && token.getTokenType().equals("ADMIN_CONFIRM")) {
                return "{\"success\":false,\"message\":\"Token has expired\"}";
            }

            token.set_used(true);
            user.setVerified(true);
            user.setRole("NATIONAL_ADMIN");
            naRepo.save(user);

            // Generate a new authenticated token valid for 2 days
            String sessionToken = UUID.randomUUID().toString();
            RefreshedToken newAuthToken = RefreshedToken.builder()
                    .user(user)
                    .tokenHash(sessionToken)
                    .expiresAt(LocalDateTime.now().plusDays(2))
                    .revocked(false)
                    .build();

            refreshedTokenRepo.save(newAuthToken);

            return "{\"success\":true,\"sessionToken\":\"" + sessionToken + "\"}";
        } catch (Exception e) {
            return "{\"success\":false,\"message\":\"Internal Server Error\"}";
        }
    }
}
