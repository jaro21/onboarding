package com.onboarding.payu.model.payment.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class TransactionRequest {

	@JsonProperty("order")
	private OrderDto orderDto;
	private PayerDto payerDto;
	private String creditCardTokenId;
	private String paymentMethod;
	private String deviceSessionId;
	private String ipAddress;
	private String cookie;
	private String userAgent;
}
