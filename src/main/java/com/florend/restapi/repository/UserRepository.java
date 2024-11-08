package com.florend.restapi.repository;

import com.florend.restapi.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<Users, UUID> {
    Users findByUsername(String username);
}
