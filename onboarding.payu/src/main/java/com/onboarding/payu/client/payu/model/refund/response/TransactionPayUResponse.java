package com.onboarding.payu.client.payu.model.refund.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Object for transaction's response
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TransactionPayUResponse {

	private Long orderID;
	private Long transactionID;
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
}
