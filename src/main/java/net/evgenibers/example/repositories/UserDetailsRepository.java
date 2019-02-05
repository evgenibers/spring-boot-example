package net.evgenibers.example.repositories;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import net.evgenibers.example.models.User;

public interface UserDetailsRepository extends MongoRepository<User, String> {
	Optional<User> findFirstByUsername(String login);
	Long countByUsernameContains(String username);
	Page<User> findAllByUsernameContains(String username, Pageable pageRequest);
}
