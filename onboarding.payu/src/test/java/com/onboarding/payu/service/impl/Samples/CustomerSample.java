package com.onboarding.payu.service.impl.Samples;

import com.onboarding.payu.model.purchase.request.CustomerPoRequest;
import com.onboarding.payu.repository.entity.Customer;

/**
 * Get sample object for run unit tests.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public class CustomerSample {

	public static CustomerPoRequest getCustomerDto() {

		return CustomerPoRequest.builder()
								.idCustomer(1)
								.street1("Prueba street1")
								.city("Tulua")
								.state("Valle del cauca")
								.country("Colombia")
								.postalCode("00000").build();
	}

	public static Customer getCustomer() {

		return Customer.builder()
					   .idCustomer(1)
					   .fullName("Client Test")
					   .email("client@gmail.com")
					   .phone("3141212222")
					   .dniNumber("14")
					   .creditCardList(CreditCardSample.getCreditCardList())
					   .build();
	}
}
