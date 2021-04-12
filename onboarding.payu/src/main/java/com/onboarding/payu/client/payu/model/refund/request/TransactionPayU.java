package com.onboarding.payu.client.payu.model.refund.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;

/**
 * Object for transaction's request
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class TransactionPayU {

	private OrderPayU order;
	private String type;
	private String reason;
	private String parentTransactionId;
}
