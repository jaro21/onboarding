package com.onboarding.payu.provider.payments;

import com.onboarding.payu.model.payment.request.PaymentTransactionRequest;
import com.onboarding.payu.model.payment.response.PaymentWithTokenResponse;
import com.onboarding.payu.model.refund.response.RefundDtoResponse;
import com.onboarding.payu.model.tokenization.request.CreditCardRequest;
import com.onboarding.payu.model.tokenization.response.TokenResponse;
import com.onboarding.payu.repository.entity.Customer;
import com.onboarding.payu.repository.entity.Payment;
import com.onboarding.payu.repository.entity.PurchaseOrder;

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

	/**
	 *
	 * @param paymentTransactionRequest {@link PaymentTransactionRequest}
	 * @param purchaseOrder {@link PurchaseOrder}
	 * @param customer {@link Customer}
	 * @return {@link PaymentWithTokenResponse}
	 */
	PaymentWithTokenResponse paymentWithToken(PaymentTransactionRequest paymentTransactionRequest,
											  PurchaseOrder purchaseOrder,
											  Customer customer);

	/**
	 *
	 * @param payment {@link Payment}
	 * @param reason {@link String}
	 * @return {@link RefundDtoResponse}
	 */
	RefundDtoResponse applyRefund(Payment payment, String reason);

}
