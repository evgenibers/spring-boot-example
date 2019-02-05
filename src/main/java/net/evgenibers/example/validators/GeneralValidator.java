package net.evgenibers.example.validators;

import java.util.Objects;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Service;

import lombok.extern.log4j.Log4j2;
import net.evgenibers.example.exception.ErrorCodes;
import net.evgenibers.example.exception.RestException;

@Log4j2
@Service
public class GeneralValidator {
	public void validateEmailAndThrow(String email) {
		log.debug("validateEmailAndThrow: email = {}", email);
		if (Objects.isNull(email) || email.isEmpty()) {
			throw new RestException(ErrorCodes.EMPTY_EMAIL);
		}
		if (!EmailValidator.getInstance(true).isValid(email)) {
			throw new RestException(ErrorCodes.WRONG_EMAIL);
		}
	}
}
