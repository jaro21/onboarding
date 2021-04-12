package com.onboarding.payu.service.impl;

import java.util.Collections;
import java.util.List;

import com.onboarding.payu.model.tokenization.request.CreditCardRequest;
import com.onboarding.payu.model.tokenization.response.TokenResponse;
import com.onboarding.payu.provider.payments.IPaymentProvider;
import com.onboarding.payu.repository.ICreditCardRepository;
import com.onboarding.payu.repository.entity.CreditCard;
import com.onboarding.payu.repository.entity.Customer;
import com.onboarding.payu.service.ICreditCard;
import com.onboarding.payu.service.ICustomerService;
import com.onboarding.payu.service.impl.mapper.CreditCardMapper;
import com.onboarding.payu.service.validator.CreditCardValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Implementation of {@link ICreditCard} interface.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Service
public class CreditCardImpl implements ICreditCard {

	private final IPaymentProvider iPaymentProvider;

	private final ICreditCardRepository iCreditCardRepository;

	private final ICustomerService iCustomerService;

	private final CreditCardValidator creditCardValidator;

	@Autowired
	public CreditCardImpl(final IPaymentProvider iPaymentProvider,
						  final ICreditCardRepository iCreditCardRepository,
						  final ICustomerService iCustomerService,
						  final CreditCardValidator creditCardValidator) {

		this.iPaymentProvider = iPaymentProvider;
		this.iCreditCardRepository = iCreditCardRepository;
		this.iCustomerService = iCustomerService;
		this.creditCardValidator = creditCardValidator;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public TokenResponse tokenizationCard(final CreditCardRequest creditCardRequest) {

		log.debug("TokenizationCard : ", creditCardRequest.toString());
		creditCardValidator.runValidations(creditCardRequest);
		final TokenResponse tokenResponse = iPaymentProvider.tokenizationCard(creditCardRequest);
		saveCreditCard(tokenResponse);
		return iPaymentProvider.tokenizationCard(creditCardRequest);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public TokenResponse saveCreditCard(final TokenResponse tokenResponse) {

		log.debug("Save Credit Card", tokenResponse.toString());
		final Customer customer = iCustomerService.findByDniNumber(tokenResponse.getCreditCard().getIdentificationNumber());

		if (isValidRegistration(customer, tokenResponse)) {
			final CreditCard creditCard = CreditCardMapper.toCreditCard(tokenResponse, customer);
			iCreditCardRepository.save(creditCard);
		}
		return tokenResponse;
	}

	/**
	 * Identify if the card should be registered
	 *
	 * @param customer      {@link Customer}
	 * @param tokenResponse {@link TokenResponse}
	 * @return
	 */
	private boolean isValidRegistration(final Customer customer, final TokenResponse tokenResponse) {

		return tokenResponse != null && tokenResponse.getCreditCard() != null && customer != null && customer.getCreditCardList() != null
				&& !customer.getCreditCardList().stream()
							.filter(creditCard -> creditCard.getToken().equals(tokenResponse.getCreditCard().getCreditCardTokenId()))
							.findFirst().isPresent();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public List<CreditCard> findAllCardsByCustomer(final String dniNumber) {

		final Customer customer = iCustomerService.findByDniNumber(dniNumber);
		return iCreditCardRepository.findByIdCustomer(customer.getIdCustomer()).orElse(Collections.emptyList());
	}
}
