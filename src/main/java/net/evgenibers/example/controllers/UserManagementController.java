package net.evgenibers.example.controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.log4j.Log4j2;
import net.evgenibers.example.integration.UserRoles;
import net.evgenibers.example.models.User;
import net.evgenibers.example.models.UserInDTO;
import net.evgenibers.example.services.UserManagementService;

@Log4j2
@CrossOrigin
@RestController
@RequestMapping("/api/secured/admin/users")
public class UserManagementController {
	@Autowired
	private UserManagementService userManagementService;

	@GetMapping("/login")
	public User login(@AuthenticationPrincipal User requester) {
		log.info("login: requester = {}", requester);
		return userManagementService.getUser(requester.getId());
	}
	
	@PostMapping("/login")
	public User loginPost(@AuthenticationPrincipal User requester) {
		log.info("login: requester = {}", requester);
		return userManagementService.getUser(requester.getId());
	}
	
	@GetMapping("")
	public List<User> getUsers(@AuthenticationPrincipal User requester,
			@RequestParam(value = "q", required = false) Optional<String> queryName, @RequestParam Integer page,
			@RequestParam Integer limit, @RequestParam(value = "orderBy", required = false) String byColumn,
			@RequestParam Integer ascending) {
		log.info("getUsers: queryName = {}, page = {}, size = {}", queryName, page, limit, byColumn, ascending);
		return userManagementService.getUsers(queryName, page, limit, byColumn, ascending);
	}

	@GetMapping("/count")
	public Long getUsersCount(@AuthenticationPrincipal User requester,
			@RequestParam(value = "q", required = false) Optional<String> queryName) {
		log.info("getUsersCount: queryName = {}", queryName);
		return userManagementService.getUsersCount(queryName);
	}

	@GetMapping("/{userId}")
	public User getUser(@AuthenticationPrincipal User requester, @PathVariable String userId) {
		log.info("getUser: userId = {}", userId);
		return userManagementService.getUser(userId);
	}

	@PostMapping("")
	public User createUser(@AuthenticationPrincipal User requester, @Validated @RequestBody UserInDTO data) {
		log.info("createUser: body = {}", data);
		return userManagementService.createUser(requester, data);
	}

	@PutMapping("/{userId}")
	public User updateUser(@AuthenticationPrincipal User requester, @Validated @RequestBody UserInDTO data,
			@PathVariable String userId) {
		log.info("updateUser: data = {}, userId = {}", data, userId);
		return userManagementService.updateUser(requester, userId, data);
	}

	@DeleteMapping("/{userId}")
	public void deleteUser(@AuthenticationPrincipal User requester, @PathVariable String userId) {
		log.info("deleteUser: userId = {}", userId);
		userManagementService.deleteUser(requester, userId);
	}

	@GetMapping("/roles")
	public List<UserRoles> getAllUserRoles(@AuthenticationPrincipal User requester) {
		log.info("getAllUserRoles: ");
		return userManagementService.getAllUserRoles();
	}
}
