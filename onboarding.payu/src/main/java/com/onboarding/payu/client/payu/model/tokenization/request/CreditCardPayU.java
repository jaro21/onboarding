package com.onboarding.payu.client.payu.model.tokenization.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

/**
 * Object for credit card's resquest
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CreditCardPayU {

	private String payerId;
	private String name;
	private String identificationNumber;
	private String paymentMethod;
	private String number;
	private String expirationDate;
}
