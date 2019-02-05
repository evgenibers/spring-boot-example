package net.evgenibers.example.utils;

import java.util.List;

import net.evgenibers.example.integration.UserRoles;

public class AuthHelper {
	private AuthHelper() {
	}

	public static String[] convertToAuthorities(List<UserRoles> grantedAuthorities) {
		return grantedAuthorities.stream().map(Enum::name).toArray(String[]::new);
	}
	
	public static String[] convertToAuthorities(UserRoles grantedAuthorities) {
		return new String[] {grantedAuthorities.name()};
	}
}
