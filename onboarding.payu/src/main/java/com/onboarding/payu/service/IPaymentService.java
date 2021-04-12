package com.onboarding.payu.service;

import com.onboarding.payu.model.payment.request.PaymentTransactionRequest;
import com.onboarding.payu.model.payment.request.TransactionRequest;
import com.onboarding.payu.model.payment.response.PaymentWithTokenResponse;
import com.onboarding.payu.repository.entity.Payment;

/**
 * Interface that define of Payment's services
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IPaymentService {

	/**
	 * @param idPayment {@link Integer}
	 * @return {@link Payment}
	 * @
	 */
	Payment findById(Integer idPayment);

	/**
	 * Service to apply the payment of a purchase order
	 *
	 * @param transactionRequest {@link TransactionRequest}
	 * @return {@link PaymentWithTokenResponse}
	 */
	PaymentWithTokenResponse paymentWithToken(PaymentTransactionRequest transactionRequest);

	/**
	 * Update Payment's status by id
	 *
	 * @param status {@link String}
	 * @param id     {@link Integer}
	 * @return {@link Integer}
	 */
	void updateStatusById(String status, Integer id);
}
