package com.app.adventurehub.user.resource;

import com.app.adventurehub.user.enums.Role;
import lombok.*;

import javax.validation.constraints.*;

@Getter
@Setter
@With
@NoArgsConstructor
@AllArgsConstructor
public class AuthCredentialsResource {
	@NotNull
	@NotBlank(message = "email is required")
	@Email(message = "email is not valid")
	private String email;
	@NotNull
	@Size(min = 8, max = 16, message = "password must be between 8 and 16 characters")
	@NotBlank(message = "password is required")
	private String password;

	private Role role = Role.TRAVELER;

	private String username;
}
