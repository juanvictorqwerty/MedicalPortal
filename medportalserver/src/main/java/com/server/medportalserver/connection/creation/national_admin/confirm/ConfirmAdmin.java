package com.server.medportalserver.connection.creation.national_admin.confirm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConfirmAdmin {
    private String token;
    private String email;
}
