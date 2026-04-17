package com.server.medportalserver.model.public_layer;

import com.server.medportalserver.model.auth.User;
import com.server.medportalserver.model.hospital.Hospital;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "vaccine")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vaccine {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "patient_id", nullable = false)
    private Patient patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor_id", nullable = false)
    private User doctor;

    @Column(name = "vac_name", nullable = false)
    private String vacName;

    @Column(columnDefinition = "TEXT")
    private String objective;

    @Column(name = "date_administered")
    private LocalDateTime dateAdministered;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital_id")
    private Hospital hospital;

    @Column(name = "place_administered")
    private String placeAdministered;
}
