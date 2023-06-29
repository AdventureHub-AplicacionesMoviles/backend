package com.app.adventurehub.user.service;


import com.app.adventurehub.user.domain.model.entity.User;
import com.app.adventurehub.user.domain.persistence.UserRepository;
import com.app.adventurehub.user.domain.service.AuthService;
import com.app.adventurehub.user.resource.AuthCredentialsResource;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder encoder;

    @Override
    public String getUserMobileToken(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        return user.getMobile_token();
    }

    @Override
    public User updateUserMobileToken(String email, String mobile_token) {
        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        user.setMobile_token(mobile_token);
        return userRepository.save(user);
    }

    @Override
    public User updateUserEmail(String currentEmail, String newEmail) {
        User user = userRepository.findByEmail(currentEmail);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        User existingUser = userRepository.findByEmail(newEmail);
        if (existingUser != null && !existingUser.equals(user)) {
            throw new IllegalArgumentException("Email already exists");
        }

        user.setEmail(newEmail);
        return userRepository.save(user);
    }


    public User login(AuthCredentialsResource credentials) {
        String email = credentials.getEmail();
        String password = credentials.getPassword();

        User user = userRepository.findByEmail(email);
        if (user == null) {
            throw new IllegalArgumentException("User not found");
        }
        if (!user.getPassword().equals(password)) {
            throw new IllegalArgumentException("Wrong password");
        }
        return user;
    }

    @Override
    public User register(AuthCredentialsResource credentialsResource) {
        User registeredUser = new User();
        registeredUser.setEmail(credentialsResource.getEmail());
        registeredUser.setPassword(encoder.encode(credentialsResource.getPassword()));
				registeredUser.setRole(credentialsResource.getRole());
        registeredUser = userRepository.save(registeredUser);
        return registeredUser;
    }

}
