package net.evgenibers.example.validators;

import java.util.Arrays;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import net.evgenibers.example.exception.ErrorCodes;
import net.evgenibers.example.exception.RestException;
import net.evgenibers.example.integration.UserRoles;
import net.evgenibers.example.models.User;
import net.evgenibers.example.models.UserInDTO;
import net.evgenibers.example.repositories.UserDetailsRepository;

@Log4j2
@Service
public class UserManagementValidator {
	@Autowired
	private UserDetailsRepository userDetailsRepository;
	
	public User validateUpdateUserAndReturn(User requester, String userId, UserInDTO data) {
		if (Objects.isNull(userId) || userId.isEmpty()) {
			throw new RestException(ErrorCodes.USER_WRONG_ID);
		}
		if (Objects.isNull(data)) {
			throw new RestException(ErrorCodes.USER_EMPTY_DATA);
		}
		// TODO validate data
		User fromDb = userDetailsRepository.findById(userId)
				.orElseThrow(() -> new RestException(ErrorCodes.USER_NOT_EXIST));
		if (requester.getId().equals(userId) && Arrays.asList(fromDb.getGrantedAuthorities())
				.contains(UserRoles.ROLE_ADMIN.name()) && !data.getRole().equals(UserRoles.ROLE_ADMIN)) {
			throw new RestException(ErrorCodes.USER_WRONG_ROLES);
		}
		return fromDb;
	}
	
	public void validateDeleteUser(User requester, String userId) {
		if (Objects.isNull(userId) || userId.isEmpty()) {
			throw new RestException(ErrorCodes.USER_WRONG_ID);
		}
		if (requester.getId().equals(userId)) {
			throw new RestException(ErrorCodes.USER_CANT_DELETE_HIMSELF);
		}
		userDetailsRepository.findById(userId).orElseThrow(() -> new RestException(ErrorCodes.USER_NOT_EXIST));
	}

	// TODO impl
	public PageRequest validatePagingAndThrowAndReturn(Integer page, Integer limit, String byColumn, Integer ascending) {
		if (Objects.isNull(byColumn) || byColumn.isEmpty()) {
			byColumn = "username";
		}
		if (Objects.isNull(ascending)) {
			ascending = 1;
		}
		page = page - 1;
		Direction dir = parseDirection(ascending);
		if ("role".equals(byColumn)) {
			byColumn = "grantedAuthorities";
		}
		return PageRequest.of(page, limit, new Sort(dir, byColumn));
	}
	
	private Direction parseDirection(Integer ascending) {
		if (ascending.equals(1)) {
			return Sort.Direction.ASC;
		} else {
			return Sort.Direction.DESC;
		}
	}

	public void validateCreateUserAndThrow(User requester, UserInDTO data) {
		if (Objects.isNull(data)) {
			throw new RestException(ErrorCodes.USER_EMPTY_DATA);
		}
		// TODO validate data
	}
}
