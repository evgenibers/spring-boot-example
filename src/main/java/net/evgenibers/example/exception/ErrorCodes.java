package net.evgenibers.example.exception;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodes {
	UNKNOWN_ERROR("000000", "unknown_error", HttpStatus.INTERNAL_SERVER_ERROR),
	MISSING_PARAMETER("000001", "missing_parameter", HttpStatus.BAD_REQUEST),
	
	/* GENERAL */
	NO_PERMISSIONS("001001", "no_permissions", HttpStatus.BAD_REQUEST),
	EMPTY_EMAIL("001002", "empty_email", HttpStatus.BAD_REQUEST),
	WRONG_EMAIL("001003", "wrong_email", HttpStatus.BAD_REQUEST),

	/* USERS */
	USER_CANT_DELETE_HIMSELF("002001", "user_cant_delete_himself", HttpStatus.BAD_REQUEST),
	USER_NOT_EXIST("002002", "user_not_exist", HttpStatus.BAD_REQUEST),
	USER_WRONG_ROLES("002003", "user_wrong_roles", HttpStatus.BAD_REQUEST),
	USER_ALREADY_EXIST("002004", "user_already_exist", HttpStatus.BAD_REQUEST),
	USER_WRONG_EMAIL("002005", "user_wrong_email", HttpStatus.BAD_REQUEST),
	USER_WRONG_ID("002006", "user_wrong_id", HttpStatus.BAD_REQUEST),
	USER_EMPTY_DATA("002007", "user_empty_data", HttpStatus.BAD_REQUEST);

	private final String errorCode;
	private final String message;
	private final HttpStatus httpStatus;
}
