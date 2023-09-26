package com.app.adventurehub.user.api;

import com.app.adventurehub.user.domain.service.AuthService;
import com.app.adventurehub.user.mapping.UserMapper;
import com.app.adventurehub.user.resource.*;
import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    private UserMapper mapper;

    @PatchMapping("/user")
    @Operation(summary = "Update User Mobile Token", tags = {"Auth"})
    public ResponseEntity<UserResource> updateUserMobileToken(@RequestBody UpdateUserResource resource) {
        return new ResponseEntity<>(
                mapper.toResource(authService.updateUserMobileToken(resource.getMobile_token())),
                HttpStatus.OK);
    }

    @GetMapping("/user/me")
    @Operation(summary = "Get User", tags = {"Auth"})
    public ResponseEntity<UserResource> getUser() {
        return new ResponseEntity<>(mapper.toResource(authService.getUser()), HttpStatus.OK);
    }

    @PostMapping("/login")
    @Operation(summary = "Login", tags = {"Auth"})
    public ResponseEntity<JwtResponse> login(@Valid @RequestBody AuthCredentialsResource resource) {
        return new ResponseEntity<>(authService.login(resource), HttpStatus.OK);
    }

    @PostMapping("/register")
    @Operation(summary = "Register", tags = {"Auth"})
    public ResponseEntity<?> register(@Valid @RequestBody AuthCredentialsResource credentials)  {
        return new ResponseEntity<>(mapper.toResource(authService.register(credentials)), HttpStatus.CREATED);
    }
}
