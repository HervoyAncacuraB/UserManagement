package com.example.usermanagement.controller.security;

import com.example.usermanagement.dto.LoginRequestDTO;
import com.example.usermanagement.dto.LoginResponseDTO;
import com.example.usermanagement.util.JwtTokenProvider;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/management/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostMapping
    public ResponseEntity<LoginResponseDTO> generateTokenJwt(@Valid @RequestBody LoginRequestDTO loginRequest) throws AuthenticationException {

        LoginResponseDTO response = new LoginResponseDTO();

        Authentication authentication = authenticationManager.authenticate
                (new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());

        // Generar token JWT si la autenticación es exitosa
        String token = jwtTokenProvider.createToken(userDetails);
        response.setToken(token);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Map<String, String>> refreshToken(@RequestBody String refreshToken) {
        // Validar y obtener el nombre de usuario del token de actualización
        String email = jwtTokenProvider.getUsernameFromToken(refreshToken);

        // Generar un nuevo token JWT
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);
        String token = jwtTokenProvider.createToken(userDetails);

        // Crear una respuesta con el nuevo token JWT
        Map<String, String> response = new HashMap<>();
        response.put("token", token);

        return ResponseEntity.ok(response);
    }
}
