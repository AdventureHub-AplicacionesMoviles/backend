package com.app.adventurehub.user.resource;

import com.app.adventurehub.user.domain.model.entity.Role;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class UserResource {
    private Long id;
    private String email;
    private String username;
    private String mobile_token;
    private Set<Role> authorities;

}