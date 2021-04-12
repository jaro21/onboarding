package com.onboarding.payu.provider.payments.payu.impl;

import com.onboarding.payu.client.payu.PaymentClient;
import com.onboarding.payu.client.payu.model.Merchant;
import com.onboarding.payu.client.payu.model.payment.request.PaymentWithTokenPayURequest;
import com.onboarding.payu.model.payment.request.PaymentTransactionRequest;
import com.onboarding.payu.model.payment.response.PaymentWithTokenResponse;
import com.onboarding.payu.model.refund.response.RefundDtoResponse;
import com.onboarding.payu.model.tokenization.request.CreditCardRequest;
import com.onboarding.payu.model.tokenization.response.TokenResponse;
import com.onboarding.payu.provider.payments.IPaymentProvider;
import com.onboarding.payu.provider.payments.payu.mapper.PaymentPayuMapper;
import com.onboarding.payu.provider.payments.payu.mapper.RefundPayuMapper;
import com.onboarding.payu.provider.payments.payu.mapper.TokenizationPayuMapper;
import com.onboarding.payu.repository.entity.Customer;
import com.onboarding.payu.repository.entity.Payment;
import com.onboarding.payu.repository.entity.PurchaseOrder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Implementation of {@link IPaymentProvider} interface.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Component
public class PayUProviderImpl implements IPaymentProvider {

	private final PaymentClient paymentClient;

	private final PaymentPayuMapper paymentPayUMapper;

	private final RefundPayuMapper refundPayUMapper;

	@Value("${payment-api.apiKey}")
	private String apiKey;
	@Value("${payment-api.apiLogin}")
	private String apiLogin;

	@Autowired
	public PayUProviderImpl(final PaymentClient paymentClient,
							final PaymentPayuMapper paymentPayUMapper, final RefundPayuMapper refundPayUMapper) {

		this.paymentClient = paymentClient;
		this.paymentPayUMapper = paymentPayUMapper;
		this.refundPayUMapper = refundPayUMapper;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public TokenResponse tokenizationCard(final CreditCardRequest creditCardRequest) {

		return TokenizationPayuMapper.getTokenResponse(paymentClient.tokenizationCard(
				TokenizationPayuMapper.getTokenizationRequest(creditCardRequest, getMerchant())));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public PaymentWithTokenResponse paymentWithToken(final PaymentTransactionRequest paymentTransactionRequest,
													 final PurchaseOrder purchaseOrder,
													 final Customer customer) {

		log.debug("paymentWithToken : ", paymentTransactionRequest.toString());

		final PaymentWithTokenPayURequest request = paymentPayUMapper.buildPaymentWithTokenRequest(getMerchant(),
																								   paymentTransactionRequest,
																								   purchaseOrder,
																								   customer);

		return paymentPayUMapper.toPaymentWithTokenResponse(paymentClient.paymentWithToken(request));
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public RefundDtoResponse applyRefund(final Payment payment, final String reason) {

		return refundPayUMapper
				.toRefundDtoResponse(paymentClient.applyRefund(refundPayUMapper.toRefundPayURequest(payment, reason, getMerchant())));
	}

	/**
	 * Get Merchant object
	 *
	 * @return {@link Merchant}
	 */
	private Merchant getMerchant() {

		return Merchant.builder().apiKey(apiKey).apiLogin(apiLogin).build();
	}
}
