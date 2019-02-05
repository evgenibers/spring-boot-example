package net.evgenibers.example.exception;

import java.util.Date;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;

@JsonAutoDetect(getterVisibility = JsonAutoDetect.Visibility.NONE)
@Getter
public class RestException extends RuntimeException {
	@JsonIgnore
	private static final long serialVersionUID = 8817378747847933822L;
	@JsonProperty
	private final String errorCode;
	@JsonProperty
	private final String message;
	@JsonProperty
	private final Date date;
	private final HttpStatus httpStatus;

	public RestException(ErrorCodes error) {
		errorCode = error.getErrorCode();
		message = error.getMessage();
		httpStatus = error.getHttpStatus();
		date = new Date();
	}

	public RestException(String errorCode, String message, HttpStatus httpStatus) {
		this.message = message;
		this.errorCode = errorCode;
		this.date = new Date();
		this.httpStatus = httpStatus;
	}
}
