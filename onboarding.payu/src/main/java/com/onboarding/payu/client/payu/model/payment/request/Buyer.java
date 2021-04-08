package com.onboarding.payu.client.payu.model.payment.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Object for buyer's request
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@Builder
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Buyer {

	private String merchantBuyerId;
	private String fullName;
	private String emailAddress;
	private String contactPhone;
	private String dniNumber;
	private IngAddress shippingAddress;
}
