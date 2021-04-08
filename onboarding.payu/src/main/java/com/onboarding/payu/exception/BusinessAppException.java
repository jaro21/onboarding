package com.onboarding.payu.exception;

import static java.lang.String.format;

import lombok.Getter;

/**
 * Exception thrown by business services
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
public class BusinessAppException extends RuntimeException {

	private String code;

	/**
	 * Constructor that receive the exception's message
	 *
	 * @param exceptionCodes {@link String} The exception's code
	 * */
	public BusinessAppException(final ExceptionCodes exceptionCodes) {
		super(exceptionCodes.getMessage());
		this.code = exceptionCodes.getCode();
	}

	/**
	 *
	 *
	 * @param exceptionCodes {@link ExceptionCodes}
	 * @param param {@link String}
	 */
	public BusinessAppException(final ExceptionCodes exceptionCodes, final String param) {
		super(format(exceptionCodes.getMessage(), param));
		this.code = exceptionCodes.getCode();
	}
}
