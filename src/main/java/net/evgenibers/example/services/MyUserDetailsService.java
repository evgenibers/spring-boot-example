package net.evgenibers.example.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import net.evgenibers.example.repositories.UserDetailsRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {
	@Autowired
	private UserDetailsRepository userDetailsRepository;

	@Override
	public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
		return userDetailsRepository.findFirstByUsername(login)
				.orElseThrow(() -> new UsernameNotFoundException("User doesn't exist"));
	}
}
