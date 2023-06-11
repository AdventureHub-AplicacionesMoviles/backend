package com.app.adventurehub.user.domain.service;

import com.app.adventurehub.user.domain.model.entity.User;
import com.app.adventurehub.user.resource.AuthCredentialsResource;

public interface AuthService {
    User updateUserMobileToken(String email, String mobile_token);
    String getUserMobileToken(String email);
    User login (AuthCredentialsResource credentials);
    User register(AuthCredentialsResource credentialsResource);

}
