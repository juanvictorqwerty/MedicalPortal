package com.server.medportalserver.connection.national_admin;

import com.server.medportalserver.model.auth.ConfirmationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ConfirmationTokenRepo extends JpaRepository<ConfirmationToken, UUID> {
}
