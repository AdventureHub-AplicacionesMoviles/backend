package com.app.adventurehub.user.service;

import com.app.adventurehub.shared.exception.ResourceValidationException;
import com.app.adventurehub.user.domain.model.entity.User;
import com.app.adventurehub.user.domain.persistence.UserRepository;
import com.app.adventurehub.user.domain.service.AuthService;
import com.app.adventurehub.user.resource.AuthCredentialsResource;
import com.app.adventurehub.user.resource.JwtResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.app.adventurehub.user.util.JwtUtil;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {
	private static final String ENTITY = "User";

	private UserServiceImpl userService;
	private UserRepository userRepository;
	private JdbcTemplate jdbcTemplate;
	private JwtUtil jwtUtil;
	private AuthenticationManager manager;
	private PasswordEncoder encoder;

	@Override
	public User updateUserMobileToken(String mobile_token) {
		Long userId = userService.getUserIdFromSecurityContext();
		User user = userRepository.findById(userId).orElseThrow(() -> new ResourceValidationException("User not found"));
		user.setMobile_token(mobile_token);
		return userRepository.save(user);
	}

	@Override
	public User getUser() {
		Long userId = userService.getUserIdFromSecurityContext();
		return userRepository.findById(userId).orElseThrow(() -> new ResourceValidationException("User not found"));
	}

	@Override
	public JwtResponse login(AuthCredentialsResource credentials) {
		Authentication authentication = manager.authenticate(
				new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtil.generateJwtToken(authentication);
		return new JwtResponse(jwt);
	}

	@Override
	public User register(AuthCredentialsResource credentials) {
		HashMap<String, List<String>> errors = new HashMap<>();

		if (userRepository.findByEmail(credentials.getEmail()) != null) {
			errors.put("email", List.of("email is already taken"));
			throw new ResourceValidationException(ENTITY, errors);
		}

		User userSaved = userRepository.save(new User()
				.withEmail(credentials.getEmail())
				.withPassword(encoder.encode(credentials.getPassword())));

		userSaved.setUsername("Guest-" + userSaved.getId());
		userRepository.save(userSaved);

		jdbcTemplate.execute("INSERT INTO users_roles (user_id, role_id) VALUES (" + userSaved.getId() + ", "
				+ credentials.getRole().getRoleId() + ")");

		return userSaved;
	}

}
