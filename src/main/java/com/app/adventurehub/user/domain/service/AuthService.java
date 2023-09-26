package com.app.adventurehub.user.domain.service;

import com.app.adventurehub.user.domain.model.entity.User;
import com.app.adventurehub.user.resource.AuthCredentialsResource;
import com.app.adventurehub.user.resource.JwtResponse;

public interface AuthService {
    User updateUserMobileToken(String mobile_token);
    User getUser();
    JwtResponse login (AuthCredentialsResource credentials);
    User register(AuthCredentialsResource credentialsResource);
}
