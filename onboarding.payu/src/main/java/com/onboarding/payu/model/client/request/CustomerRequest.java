package com.onboarding.payu.model.client.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * Object for customer's request
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
public class CustomerRequest {

	private Integer idCustomer;

	@NotBlank(message = "Client full name cannot not be empty")
	@Size(max = 45, message = "The size of the full name must be a maximum of 45 characters.")
	private String fullName;

	@NotBlank(message = "Client email cannot not be empty")
	@Size(max = 45, message = "The size of the email must be a maximum of 45 characters.")
	@Email
	private String email;

	@NotBlank(message = "Client phone number cannot not be empty")
	@Size(max = 45, message = "The size of the phone number must be a maximum of 45 characters.")
	@Pattern(regexp = "[0-9]+", message = "The phone number must contain only numeric characters")
	private String phone;

	@NotBlank(message = "Client dni number cannot not be empty")
	@Size(max = 45, message = "The size of the dni number must be a maximum of 45 characters.")
	@Pattern(regexp = "[0-9]+", message = "The dni number must contain only numeric characters")
	private String dniNumber;

	@NotBlank(message = "Address cannot not be empty")
	@Size(max = 200, message = "The size of the address must be a maximum of 200 characters.")
	private String address;

	@NotBlank(message = "City cannot not be empty")
	@Size(max = 50, message = "The size of the city must be a maximum of 50 characters.")
	private String city;

	@NotBlank(message = "State cannot not be empty")
	@Size(max = 50, message = "The size of the state must be a maximum of 50 characters.")
	private String state;

	@NotBlank(message = "Country cannot not be empty")
	@Size(max = 50, message = "The size of the country must be a maximum of 50 characters.")
	private String country;

	@Size(max = 5, message = "The size of the postal code must be a maximum of 5 characters.")
	private String postal_code;

	private boolean active = true;
}
