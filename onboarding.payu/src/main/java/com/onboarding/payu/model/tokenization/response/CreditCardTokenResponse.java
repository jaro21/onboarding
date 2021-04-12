package com.onboarding.payu.model.tokenization.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

/**
 * Credit Card token response object
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreditCardTokenResponse {

	private String creditCardTokenId;
	private String name;
	private String payerId;
	private String identificationNumber;
	private String paymentMethod;
	private String number;
	private String expirationDate;
	private String creationDate;
	private String maskedNumber;
	private String errorDescription;
}
