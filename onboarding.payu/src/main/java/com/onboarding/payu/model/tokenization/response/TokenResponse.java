package com.onboarding.payu.model.tokenization.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TokenResponse {

	private String code;
	private String error;
	private CreditCardTokenResponse creditCard;
}
