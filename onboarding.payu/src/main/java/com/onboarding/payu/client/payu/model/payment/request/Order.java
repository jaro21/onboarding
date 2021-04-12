package com.onboarding.payu.client.payu.model.payment.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Object for order's request
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Order {

	private String accountId;
	private String referenceCode;
	private String description;
	private String language;
	private String signature;
	private AdditionalValues additionalValues;
	private Buyer buyer;
	private IngAddress shippingAddress;
}
