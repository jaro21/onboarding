package com.onboarding.payu.model.purchase.request;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

/**
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@ToString
public class CustomerPoRequest {

	@NotNull(message = "Customer identification cannot not be empty")
	private Integer idCustomer;
	private String street1;
	private String street2;
	private String city;
	private String state;
	private String country;
	private String postalCode;
}
