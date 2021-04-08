package com.onboarding.payu.service;

import java.util.List;

import com.onboarding.payu.model.tokenization.request.CreditCardRequest;
import com.onboarding.payu.model.tokenization.response.TokenResponse;
import com.onboarding.payu.repository.entity.CreditCard;

/**
 * Interface that define of Credit Card's services
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface ICreditCard {

	/**
	 * Service to tokenize a credit card
	 *
	 * @param creditCardRequest {@link CreditCardRequest}
	 * @return {@link TokenResponse}
	 * @
	 */
	TokenResponse tokenizationCard(CreditCardRequest creditCardRequest);

	/**
	 * Service to save a tokenized credit card
	 *
	 * @param tokenResponse {@link TokenResponse}
	 * @return {@link TokenResponse}
	 * @
	 */
	TokenResponse saveCreditCard(TokenResponse tokenResponse);

	/**
	 * Find all credit cards for a customer by ID number
	 *
	 * @param dniNumber {@link String}
	 * @return {@link List<CreditCard>}
	 * @
	 */
	List<CreditCard> findAllCardsByCustomer(String dniNumber);
}
