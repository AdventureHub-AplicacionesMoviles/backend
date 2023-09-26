package com.app.adventurehub.user.service;

import com.app.adventurehub.user.domain.model.entity.User;
import com.app.adventurehub.user.domain.model.entity.UserDetailsImpl;
import com.app.adventurehub.user.domain.persistence.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public Long getUserIdFromSecurityContext() {
        Authentication auth =  SecurityContextHolder.getContext().getAuthentication();

        if(auth.getPrincipal() == "anonymousUser") {
            throw new UsernameNotFoundException("The user is not logged in");
        }

        UserDetailsImpl userDetails = (UserDetailsImpl) auth.getPrincipal();
        return userDetails.getId();
    }

    public UserDetails findEmailById(String idString){
        Long id = Long.parseLong(idString);
        User user = userRepository.findById(id).orElse(null);
        if (user == null)
        {
            return null;
        }
        return UserDetailsImpl.build(user);
    }
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        return UserDetailsImpl.build(user);
    }
}