package com.server.medportalserver.connection._common.email;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Email {
    private String to;
    private String subject;
    private String body;
}
