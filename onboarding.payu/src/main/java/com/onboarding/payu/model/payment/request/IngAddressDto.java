package com.onboarding.payu.model.payment.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * Object for address's request
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class IngAddressDto {

	private String street1;
	private String street2;
	private String city;
	private String state;
	private String country;
	private String postalCode;
	private String phone;
}
