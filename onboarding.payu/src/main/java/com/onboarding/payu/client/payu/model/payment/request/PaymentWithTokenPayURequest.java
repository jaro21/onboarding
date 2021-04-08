package com.onboarding.payu.client.payu.model.payment.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.onboarding.payu.client.payu.model.Merchant;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Object for payment's request
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class PaymentWithTokenPayURequest {

	private String language;
	private String command;
	private Merchant merchant;
	private TransactionPayU transaction;
	private boolean test;
}
