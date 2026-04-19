package com.server.medportalserver.connection.creation.national_admin.basic;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SupremeAdminRequest {
    private String email;
    private String name;
    private String phone;
    private String password;
}
