package com.onboarding.payu.model.payment.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class OrderDto {

	private String referenceCode;
	private String description;
	@JsonProperty("additionalValues")
	private AdditionalValuesDto additionalValuesDto;
	private BuyerDto buyerDto;
	private IngAddressDto shippingAddressDto;
}
