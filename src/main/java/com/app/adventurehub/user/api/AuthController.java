package com.app.adventurehub.user.api;

import com.app.adventurehub.shared.exception.ResourceValidationException;
import com.app.adventurehub.user.domain.model.entity.User;
import com.app.adventurehub.user.domain.persistence.UserRepository;
import com.app.adventurehub.user.domain.service.AuthService;
import com.app.adventurehub.user.resource.AuthCredentialsResource;
import com.app.adventurehub.user.resource.JwtResponse;
import com.app.adventurehub.user.resource.ResponseErrorResource;
import com.app.adventurehub.user.util.JwtUtil;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    private JwtUtil jwtUtil;
    @Autowired
    private AuthenticationManager manager;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    @Operation(summary = "Login", tags = {"Auth"})
    public ResponseEntity<?> login(@RequestBody AuthCredentialsResource user) {
        Authentication authentication = manager.authenticate(
                new UsernamePasswordAuthenticationToken(user.getEmail(), user.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtil.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/register")
    @Operation(summary = "Register", tags = {"Auth"})
    public ResponseEntity<?> register(@RequestBody AuthCredentialsResource credentials) {
        String statusBody = "User already exists";

        ResponseErrorResource errorResource = new ResponseErrorResource();
        errorResource.setMessage(statusBody);

        if(userRepository.findByEmail(credentials.getEmail()) != null) {
            return ResponseEntity.badRequest().body(errorResource);
        }
        return ResponseEntity.ok(authService.register(credentials));
    }
}
