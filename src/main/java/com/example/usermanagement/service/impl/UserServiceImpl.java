package com.example.usermanagement.service.impl;

import com.example.usermanagement.dto.UserDTO;
import com.example.usermanagement.repository.UserRepository;
import com.example.usermanagement.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    public UserRepository userRepository;

    @Override
    public Optional<UserDTO> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional<UserDTO> findByName(String name) {
        return userRepository.findByName(name);
    }
}
