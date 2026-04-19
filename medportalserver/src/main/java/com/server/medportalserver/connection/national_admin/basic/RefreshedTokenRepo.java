package com.server.medportalserver.connection.national_admin.basic;

import com.server.medportalserver.model.auth.RefreshedToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface RefreshedTokenRepo extends JpaRepository<RefreshedToken, UUID> {
}
