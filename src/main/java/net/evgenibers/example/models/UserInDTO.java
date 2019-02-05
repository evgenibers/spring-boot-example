package net.evgenibers.example.models;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.evgenibers.example.integration.UserRoles;

@Data
@NoArgsConstructor
public class UserInDTO {
	@NotNull
	private String username;
	@NotNull
	private String password;
	@NotNull
	private UserRoles role;
	@NotNull
	private String email;
	private String firstName;
	private String lastName;
}