package com.app.adventurehub.user.api;

import com.app.adventurehub.shared.exception.ResourceValidationException;
import com.app.adventurehub.user.domain.model.entity.User;
import com.app.adventurehub.user.domain.service.AuthService;
import com.app.adventurehub.user.resource.AuthCredentialsResource;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    @Operation(summary = "Login", tags = {"Auth"})
    public ResponseEntity<User> login(@RequestBody AuthCredentialsResource credentials) {
        return ResponseEntity.ok(authService.login(credentials));
    }
}
