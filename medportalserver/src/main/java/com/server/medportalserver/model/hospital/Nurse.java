package com.server.medportalserver.model.hospital;

import com.server.medportalserver.model.auth.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Table(name = "nurse", schema = "medical_staff")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Nurse {

    @Id
    private UUID id;

    // Makes the reference to user table
    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private User user;

    @Column(name = "license_number")
    private String licenseNumber;

    private String specialty;
}
