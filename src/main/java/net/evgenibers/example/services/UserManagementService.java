package net.evgenibers.example.services;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import net.evgenibers.example.exception.ErrorCodes;
import net.evgenibers.example.exception.RestException;
import net.evgenibers.example.integration.UserRoles;
import net.evgenibers.example.models.User;
import net.evgenibers.example.models.UserInDTO;
import net.evgenibers.example.repositories.UserDetailsRepository;
import net.evgenibers.example.validators.UserManagementValidator;

@Log4j2
@Service
public class UserManagementService {
	@Autowired
	private UserDetailsRepository userDetailsRepository;
	@Autowired
	private UserManagementValidator userManagementValidator;
	
	public List<User> getUsers(Optional<String> queryName, Integer page, Integer limit, String byColumn,
			Integer ascending) {
		log.debug("getUsers: queryName = {}, page = {}, limit = {}, byColumn = {}, ascending = {}", queryName, page,
				limit, byColumn, ascending);
		PageRequest paging = userManagementValidator.validatePagingAndThrowAndReturn(page, limit, byColumn, ascending);
		return queryName.map(s -> userDetailsRepository.findAllByUsernameContains(s, paging).getContent())
				.orElseGet(() -> userDetailsRepository.findAll(paging).getContent());
	}
	
	public Long getUsersCount(Optional<String> queryName) {
		log.debug("getUsersCount: queryName = {}", queryName);
		return queryName.map(s -> userDetailsRepository.countByUsernameContains(s))
				.orElseGet(() -> userDetailsRepository.count());
	}

	public User getUser(String userId) {
		log.debug("getUser: userId = {}", userId);
		return userDetailsRepository.findById(userId).orElseThrow(() -> new RestException(ErrorCodes.USER_NOT_EXIST));
	}

	public User createUser(User requester, UserInDTO data) {
		log.debug("createUser: requester = {}, data = {}", requester, data);
		userManagementValidator.validateCreateUserAndThrow(requester, data);
		return userDetailsRepository.save(User.of(data));
	}

	public User updateUser(User requester, String userId, UserInDTO data) {
		log.debug("updateUser: requester = {}, userId = {}, data = {}", requester, userId, data);
		User fromDb = userManagementValidator.validateUpdateUserAndReturn(requester, userId, data);
		return userDetailsRepository.save(User.merge(fromDb, data));
	}

	public void deleteUser(User requester, String userId) {
		log.debug("deleteUser: requester = {}, userId = {}", requester, userId);
		userManagementValidator.validateDeleteUser(requester, userId);
		userDetailsRepository.deleteById(userId);
	}

	public List<UserRoles> getAllUserRoles() {
		log.debug("getAllUserRoles: ");
		return new ArrayList<>(EnumSet.allOf(UserRoles.class));
	}
}
