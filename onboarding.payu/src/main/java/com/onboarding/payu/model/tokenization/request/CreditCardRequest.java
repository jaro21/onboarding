package com.onboarding.payu.model.tokenization.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * CreditCard request object
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@ToString
public class CreditCardRequest {

	@NotBlank(message = "Credit card payerId cannot be empty")
	private String payerId;

	@ToString.Exclude
	@NotBlank(message = "Credit card name cannot be empty")
	@Size(max = 15, message = "The size of the name must be a maximum of 15 characters.")
	@Pattern(regexp = "[A-Za-z ]+", message = "The credit card name must contain only letters without accents.")
	private String name;

	@ToString.Exclude
	@NotBlank(message = "Credit card identification number cannot be empty")
	@Pattern(regexp = "[0-9]+", message = "The credit card identification number must contain only numeric characters")
	private String identificationNumber;

	@NotBlank(message = "Credit card payment method cannot be empty")
	private String paymentMethod;

	@ToString.Exclude
	@NotBlank(message = "Credit card number cannot be empty")
	@Size(min = 13, max = 16, message = "The card number must be between 13 and 15 numeric characters.")
	@Pattern(regexp = "[0-9]+", message = "The card number must contain only numeric characters")
	private String number;

	@ToString.Exclude
	@NotBlank(message = "Credit card expiration date cannot be empty")
	private String expirationDate;
}
