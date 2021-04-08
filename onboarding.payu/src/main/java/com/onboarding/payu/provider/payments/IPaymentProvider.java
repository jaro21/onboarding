package com.onboarding.payu.provider.payments;

import com.onboarding.payu.model.payment.request.TransactionRequest;
import com.onboarding.payu.model.payment.response.PaymentWithTokenResponse;
import com.onboarding.payu.model.refund.request.RefundDtoRequest;
import com.onboarding.payu.model.refund.response.RefundDtoResponse;
import com.onboarding.payu.model.tokenization.request.CreditCardRequest;
import com.onboarding.payu.model.tokenization.response.TokenResponse;

/**
 * Payment Provider interface used to define payment methods.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IPaymentProvider {

	/**
	 *
	 * @param creditCardRequest {@link CreditCardRequest}
	 * @return {@link TokenResponse}
	 */
	TokenResponse tokenizationCard(CreditCardRequest creditCardRequest);

	PaymentWithTokenResponse paymentWithToken(TransactionRequest transactionRequest);

	RefundDtoResponse applyRefund(RefundDtoRequest refundDtoRequest);

}
