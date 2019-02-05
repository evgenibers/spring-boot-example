package net.evgenibers.example.models;

import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;
import net.evgenibers.example.integration.UserRoles;
import net.evgenibers.example.utils.AuthHelper;

@Document(collection = "userDetails")
@Data
@NoArgsConstructor
public class User implements UserDetails {
	@Id
	private String id;
	@Indexed(unique = true, dropDups = true)
	private String username;
	@JsonIgnore
	private String password;
	private String[] grantedAuthorities;
	private String email;
	private String firstName;
	private String lastName;

	public static User of(UserInDTO data) {
		return new User(data.getUsername(), data.getPassword(), data.getRole(), data.getEmail(), data.getFirstName(),
				data.getLastName());
	}
	
	public static User merge(User fromDb, UserInDTO data) {
		fromDb.setEmail(data.getEmail());
		fromDb.setFirstName(data.getFirstName());
		fromDb.setLastName(data.getLastName());
		fromDb.setGrantedAuthorities(new String[] {data.getRole().name()});
		return fromDb;
	}
	
	public User(String id, String username, String password, String[] grantedAuthorities) {
		this.id = id;
		this.username = username;
		this.password = password;
		this.grantedAuthorities = grantedAuthorities;
	}

	public User(String username, String password, List<UserRoles> grantedAuthorities) {
		this.username = username;
		this.password = password;
		this.grantedAuthorities = AuthHelper.convertToAuthorities(grantedAuthorities);
	}
	
	public User(String username, String password, UserRoles grantedAuthorities, String email, String firstName,
			String lastName) {
		this.username = username;
		this.password = password;
		this.grantedAuthorities = AuthHelper.convertToAuthorities(grantedAuthorities);
		this.email = email;
		this.firstName = firstName;
		this.lastName = lastName;
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return AuthorityUtils.createAuthorityList(grantedAuthorities);
	}

	@Override
	public String getPassword() {
		return this.password;
	}

	@Override
	public String getUsername() {
		return this.username;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
