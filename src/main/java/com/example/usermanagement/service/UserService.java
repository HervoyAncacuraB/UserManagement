package com.example.usermanagement.service;

import com.example.usermanagement.dto.UserDTO;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public interface UserService {

    Optional<UserDTO> findByEmail(String email);
    Optional<UserDTO> findByName(String name);

}
