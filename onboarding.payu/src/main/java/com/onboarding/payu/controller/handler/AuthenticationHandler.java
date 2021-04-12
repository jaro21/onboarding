package com.onboarding.payu.controller.handler;

import com.onboarding.payu.controller.AuthenticationController;
import com.onboarding.payu.exception.BusinessAppException;
import com.onboarding.payu.exception.ExceptionCodes;
import com.onboarding.payu.model.ResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Customer's Exception handler
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@ControllerAdvice(assignableTypes = AuthenticationController.class)
public class AuthenticationHandler extends ResponseEntityExceptionHandler {

	/**
	 * Customize the response for {@link MethodArgumentNotValidException}.
	 *
	 * @param ex      the exception {@link MethodArgumentNotValidException}
	 * @param headers the {@link HttpHeaders} to be written to the response
	 * @param status  the selected response {@link HttpStatus}
	 * @param request the current {@link WebRequest}
	 * @return a {@code ResponseEntity} instance
	 */
	@Override
	@NonNull
	protected ResponseEntity<Object> handleMethodArgumentNotValid(final MethodArgumentNotValidException ex,
																  final @NonNull HttpHeaders headers,
																  final @NonNull HttpStatus status,
																  final @NonNull WebRequest request) {

		log.info(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							 .body(ResponseDto.builder()
											  .message(ex.getBindingResult().getFieldError().getDefaultMessage())
											  .build());
	}

	/**
	 * Handle an {@link BusinessAppException}.
	 *
	 * @param ex of {@link BusinessAppException} with the information about the error.
	 * @return {@link ResponseEntity<ResponseDto>} object with the formatted error information.
	 */
	@ResponseBody
	@ExceptionHandler(BusinessAppException.class)
	public ResponseEntity<ResponseDto> handleBusinessAppException(final BusinessAppException ex) {

		log.info(ex.getMessage());
		return ResponseEntity.status(HttpStatus.CONFLICT)
							 .body(ResponseDto.builder()
											  .message(ex.getMessage())
											  .errorCode(ex.getCode())
											  .build());
	}

	/**
	 * Handle an {@link UsernameNotFoundException}.
	 *
	 * @param ex of {@link UsernameNotFoundException} with the information about the error.
	 * @return {@link ResponseEntity<ResponseDto>} object with the formatted error information.
	 */
	@ResponseBody
	@ExceptionHandler(UsernameNotFoundException.class)
	public ResponseEntity<ResponseDto> handleUsernameNotFoundException(final UsernameNotFoundException ex) {

		log.info(ex.getMessage());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
							 .body(ResponseDto.builder()
											  .message(ex.getMessage())
											  .build());
	}

	/**
	 * Handle an {@link BadCredentialsException}.
	 *
	 * @param ex of {@link BadCredentialsException} with the information about the error.
	 * @return {@link ResponseEntity<ResponseDto>} object with the formatted error information.
	 */
	@ResponseBody
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<ResponseDto> handleUsernameNotFoundException(final BadCredentialsException ex) {

		log.info(ex.getMessage());
		return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
							 .body(ResponseDto.builder()
											  .message(ex.getMessage())
											  .build());
	}

	/**
	 * Handler the generic exception.
	 *
	 * @param e {@link Exception} with the information about the error.
	 * @return {@link ResponseEntity<ResponseDto>} object with the formatted error information.
	 */
	@ResponseBody
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ResponseDto> handleException(final Exception e) {

		log.error("Unhandled exception : ", e);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
							 .body(ResponseDto.builder()
											  .message(ExceptionCodes.UNCONTROLLED_ERROR.getMessage())
											  .errorCode(ExceptionCodes.UNCONTROLLED_ERROR.getCode())
											  .build());
	}
}
