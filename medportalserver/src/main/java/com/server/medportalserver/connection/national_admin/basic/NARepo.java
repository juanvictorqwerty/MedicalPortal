package com.server.medportalserver.connection.national_admin.basic;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.server.medportalserver.model.auth.User;

@Repository
public interface NARepo extends JpaRepository<User, UUID> {

}
