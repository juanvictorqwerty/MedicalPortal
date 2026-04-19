package com.server.medportalserver.connection.national_admin.confirm;

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

    public String confirmAdmin(ConfirmAdmin request) {

        try {
            Optional<ConfirmationToken> tokenOpt = confirmationTokenRepo.findByToken(request.getToken());

            if (tokenOpt.isEmpty()) {
                return "{success : false, message : Invalid token}";
            }

            ConfirmationToken token = tokenOpt.get();
            User user = token.getUser();

            if (!user.getEmail().equals(request.getEmail())) {
                return "{success : false, message : Email does not match token}";
            }

            if (token.is_used()) {
                return "{success : false, message : Token has already been used}";
            }

            if (token.getExpiresAt().isBefore(LocalDateTime.now()) && token.getTokenType().equals("ADMIN_CONFIRM")) {
                return "{success : false, message : Token has expired}";
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

            return "{success : true, sessionToken : " + sessionToken + "}";
        } catch (Exception e) {
            return "{success : false, message : Internal Server Error}";
        }
    }
}
