package com.app.adventurehub.user.api;

import com.app.adventurehub.shared.exception.ResourceValidationException;
import com.app.adventurehub.user.domain.model.entity.User;
import com.app.adventurehub.user.domain.persistence.UserRepository;
import com.app.adventurehub.user.domain.service.AuthService;
import com.app.adventurehub.user.mapping.UserMapper;
import com.app.adventurehub.user.resource.*;
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

import javax.validation.Valid;

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

	@Autowired
	private UserMapper mapper;
	private static final String statusBody = "User already exists";

	@GetMapping("/user")
	@Operation(summary = "Get User Mobile Token", tags = { "Auth" })
	public String getUserMobileToken(@RequestParam(value = "username", required = false) String username) {
		return authService.getUserMobileToken(username);
	}

	@PatchMapping("/user")
	@Operation(summary = "Update User Mobile Token", tags = { "Auth" })
	public UserResource updateUserMobileToken(@RequestParam(value = "email", required = false) String email,
			@RequestBody UpdateUserResource resource) {
		return mapper.toResource(authService.updateUserMobileToken(email, resource.getMobile_token()));
	}

	@PutMapping("/user/email")
	@Operation(summary = "Update User Email", tags = { "Auth" })
	public UserResource updateUserEmail(@RequestParam(value = "currentEmail") String currentEmail,
										@RequestBody UpdateEmailResource resource) {
		return mapper.toResource(authService.updateUserEmail(currentEmail, resource.getNewEmail()));
	}


	@PostMapping("/login")
	@Operation(summary = "Login", tags = { "Auth" })
	public ResponseEntity<?> login(@Valid @RequestBody AuthCredentialsResource resource) {
		Authentication authentication = manager.authenticate(
				new UsernamePasswordAuthenticationToken(resource.getEmail(), resource.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtil.generateJwtToken(authentication);
		Long userId = Long.parseLong(jwtUtil.getUserNameFromJwtToken(jwt));
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceValidationException("User not found"));

		return ResponseEntity.ok(new JwtResponse(jwt, user.getUsername(), user.getRole()));
	}

	@PostMapping("/register")
	@Operation(summary = "Register", tags = { "Auth" })
	public ResponseEntity<?> register(@Valid @RequestBody AuthCredentialsResource credentials) {

		ResponseErrorResource errorResource = new ResponseErrorResource();
		errorResource.setMessage(statusBody);

		if (userRepository.findByEmail(credentials.getEmail()) != null) {
			return ResponseEntity.badRequest().body(errorResource);
		}

		return ResponseEntity.ok(authService.register(credentials));
	}
}
