package com.server.medportalserver.connection.creation.national_admin.confirm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ConfrimAdminControl {
    @Autowired
    private ConfirmAdminService confirmAdminService;

    @PostMapping("/confirm-admin")
    public String confirmAdmin(@RequestBody ConfirmAdmin request) {
        return confirmAdminService.confirmAdmin(request);
    }

    @PostMapping("/resend-token")
    public String resendToken(@RequestBody ResendTokenRequest request) {
        return confirmAdminService.resendToken(request);
    }
}
