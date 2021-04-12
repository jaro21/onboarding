package com.onboarding.payu.client.payu.model.tokenization.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

/**
 * Object for tokenization's response
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TokenizationPayUResponse {

	private String code;
	private String error;
	private CreditCardTokenPayU creditCardToken;
}
