package com.server.medportalserver.connection.creation.national_admin.confirm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ResendTokenRequest {
    private String email;
}
