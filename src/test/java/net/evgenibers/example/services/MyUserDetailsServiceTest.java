package net.evgenibers.example.services;

import java.util.ArrayList;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import net.evgenibers.example.BasicTest;
import net.evgenibers.example.integration.UserRoles;
import net.evgenibers.example.models.User;
import net.evgenibers.example.repositories.UserDetailsRepository;

public class MyUserDetailsServiceTest extends BasicTest {
	@Autowired
	private MyUserDetailsService userDetailsService;
	@Autowired
	private UserDetailsRepository userDetailsRepository;
	
	@Test(expected = UsernameNotFoundException.class)
	public void testLoadUserByUsernameNull() {
		userDetailsService.loadUserByUsername(null);
	}
	
	@Test(expected = UsernameNotFoundException.class)
	public void testLoadUserByUsernameEmpty() {
		userDetailsService.loadUserByUsername("");
	}
	
	@Test(expected = UsernameNotFoundException.class)
	public void testLoadUserByUsernameSpace() {
		userDetailsService.loadUserByUsername(" ");
	}
	
	@Test(expected = UsernameNotFoundException.class)
	public void testLoadUserByUsernameTrash() {
		userDetailsService.loadUserByUsername("awawdsd d *&ADH*&H@!IN:L!@JEU@!nsd s");
	}
	
	@Test
	public void testLoadUserByUsername() {
		userDetailsRepository.save(new User("user1", "password1", new ArrayList<>(Arrays.asList(UserRoles.ROLE_ADMIN))));
		UserDetails ud = userDetailsService.loadUserByUsername("user1");
		
		Assert.assertEquals("ud.getUsername() = " + ud.getUsername(), "user1", ud.getUsername());
		Assert.assertEquals("ud.getPassword() = " + ud.getPassword(), "password1", ud.getPassword());
	}
}
