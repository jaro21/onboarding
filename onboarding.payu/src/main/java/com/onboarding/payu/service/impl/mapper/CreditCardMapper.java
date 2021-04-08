package com.onboarding.payu.service.impl.mapper;

import com.onboarding.payu.model.tokenization.response.TokenResponse;
import com.onboarding.payu.repository.entity.CreditCard;
import com.onboarding.payu.repository.entity.Customer;

/**
 * Mapper for the Payment's objects
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public class CreditCardMapper {

	public static CreditCard toCreditCard(final TokenResponse tokenResponse, final Customer customer) {

		return CreditCard.builder().idCustomer(customer.getIdCustomer())
						 .maskedNumber(tokenResponse.getCreditCard().getMaskedNumber())
						 .paymentMethod(tokenResponse.getCreditCard().getPaymentMethod())
						 .token(tokenResponse.getCreditCard().getCreditCardTokenId()).build();
	}
}
