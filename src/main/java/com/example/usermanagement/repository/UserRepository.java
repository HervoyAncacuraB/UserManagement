package com.example.usermanagement.repository;

import com.example.usermanagement.dto.UserDTO;
import com.example.usermanagement.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity,Long> {
    Optional<UserDTO> findByName(String username);
    Optional<UserDTO> findByEmail(String email);
}
