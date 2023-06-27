package com.app.adventurehub.user.resource;

import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class AuthCredentialsResource {
    @NotNull
    @NotBlank
    @Email
    private String email;
    @NotNull
    @NotBlank
    @Size(min = 8, max = 16)
    private String password;
		
		private String role;
}
