package net.evgenibers.example.exception;

import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.log4j.Log4j2;

@Log4j2
@ControllerAdvice
public class ExceptionAdvicer {
	@ExceptionHandler(RestException.class)
	@ResponseBody
	public RestException restExceptionHandler(HttpServletResponse response, RestException ex) {
		response.setStatus(ex.getHttpStatus().value());
		return ex;
	}

	@ExceptionHandler(Exception.class)
	@ResponseBody
	public RestException unknownExceptionHandler(HttpServletResponse response, Exception ex) {
		log.error("unknownExceptionHandler", ex);
		RestException rex = new RestException(ErrorCodes.UNKNOWN_ERROR.getErrorCode(), ex.getMessage(),
				ErrorCodes.UNKNOWN_ERROR.getHttpStatus());
		response.setStatus(rex.getHttpStatus().value());
		return rex;
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseBody
	public RestException missingServletRequestParameterExceptionHandler(HttpServletResponse response,
			MissingServletRequestParameterException ex) {
		log.error("missingServletRequestParameterExceptionHandler", ex);
		RestException rex = new RestException(ErrorCodes.MISSING_PARAMETER.getErrorCode(), ex.getMessage(),
				ErrorCodes.MISSING_PARAMETER.getHttpStatus());
		response.setStatus(rex.getHttpStatus().value());
		return rex;
	}
}
