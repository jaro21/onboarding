package com.onboarding.payu.model.client.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Object for customer's response
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Builder
@Getter
@EqualsAndHashCode
@ToString
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class CustomerResponse {

	private Integer idCustomer;
	private String fullName;
	private String email;
	private String phone;
	private String dniNumber;
	private String address;
	private String city;
	private String state;
	private String country;
	private String postal_code;
	private boolean active;
}
