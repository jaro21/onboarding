package com.onboarding.payu.service.impl.mapper;

import com.onboarding.payu.model.ActiveType;
import com.onboarding.payu.model.client.request.CustomerRequest;
import com.onboarding.payu.model.client.response.CustomerResponse;
import com.onboarding.payu.repository.entity.Customer;
import org.springframework.stereotype.Component;

@Component
public class CustomerMapper {

	public Customer toCustomer(final CustomerRequest customerRequest) {

		Customer.CustomerBuilder customerBuilder = Customer.builder()
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
													   .active(customerRequest.getActive() != null ?
															   customerRequest.getActive() :
															   ActiveType.ACTIVE.getId());

		return customerBuilder.build();
	}

	public CustomerResponse toCustomerResponse(final Customer customer) {

		return CustomerResponse.builder()
							   .idCustomer(customer.getIdCustomer())
							   .fullName(customer.getFullName())
							   .email(customer.getEmail())
							   .phone(customer.getPhone())
							   .dniNumber(customer.getDniNumber())
							   .active(customer.getActive()).build();
	}
}
