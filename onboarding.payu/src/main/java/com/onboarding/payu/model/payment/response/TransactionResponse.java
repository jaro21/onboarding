package com.onboarding.payu.model.payment.response;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

/**
 * Object for payment's response
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TransactionResponse {

	private Long orderId;
	private UUID transactionId;
	private String state;
	private String paymentNetworkResponseCode;
	private String paymentNetworkResponseErrorMessage;
	private String trazabilityCode;
	private String authorizationCode;
	private String pendingReason;
	private String responseCode;
	private String errorCode;
	private String responseMessage;
	private String transactionDate;
	private String transactionTime;
	private String operationDate;
	private ExtraParametersResponse extraParameters;
}
