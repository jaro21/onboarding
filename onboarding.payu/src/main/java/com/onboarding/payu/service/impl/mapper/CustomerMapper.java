package com.onboarding.payu.service.impl.mapper;

import com.onboarding.payu.model.ActiveType;
import com.onboarding.payu.model.customer.request.CustomerRequest;
import com.onboarding.payu.model.customer.response.CustomerResponse;
import com.onboarding.payu.repository.entity.Customer;
import com.onboarding.payu.security.Encoder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

	private Encoder encoder;

	@Autowired
	public CustomerMapper(final Encoder encoder) {

		this.encoder = encoder;
	}

	public Customer toCustomer(final CustomerRequest customerRequest) {

		return Customer.builder()
					   .idCustomer(customerRequest.getIdCustomer())
					   .fullName(customerRequest.getFullName())
					   .email(customerRequest.getEmail())
					   .phone(customerRequest.getPhone())
					   .dniNumber(customerRequest.getDniNumber())
					   .address(customerRequest.getAddress())
					   .city(customerRequest.getCity())
					   .state(customerRequest.getState())
					   .country(customerRequest.getCountry())
					   .postal_code(customerRequest.getPostal_code())
					   .enabled(customerRequest.isEnabled() ?
								ActiveType.ENABLED.getId() :
								ActiveType.DISABLED.getId())
					   .username(customerRequest.getUsername())
					   .password_hash(encoder.passwordEncoder().encode(customerRequest.getPassword()))
					   .build();
	}

	public CustomerResponse toCustomerResponse(final Customer customer) {

		return CustomerResponse.builder()
							   .idCustomer(customer.getIdCustomer())
							   .fullName(customer.getFullName())
							   .email(customer.getEmail())
							   .phone(customer.getPhone())
							   .dniNumber(customer.getDniNumber())
							   .enabled(ActiveType.ENABLED.getId().equals(customer.getEnabled())).build();
	}
}
