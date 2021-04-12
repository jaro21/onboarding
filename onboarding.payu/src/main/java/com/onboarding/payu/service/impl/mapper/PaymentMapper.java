package com.onboarding.payu.service.impl.mapper;

import com.onboarding.payu.client.payu.model.CurrencyType;
import com.onboarding.payu.model.StatusType;
import com.onboarding.payu.model.payment.response.PaymentWithTokenResponse;
import com.onboarding.payu.repository.entity.Payment;
import com.onboarding.payu.repository.entity.PurchaseOrder;
import org.springframework.stereotype.Component;

/**
 * Mapper for the Payment's objects
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class PaymentMapper {

	/**
	 * @param paymentWithTokenResponse
	 * @param purchaseOrder
	 * @return
	 */
	public Payment buildPayment(final PaymentWithTokenResponse paymentWithTokenResponse,
								final PurchaseOrder purchaseOrder) {

		final Payment.PaymentBuilder paymentBuilder = Payment.builder().idPurchaseOrder(purchaseOrder.getIdPurchaseOrder())
															 .value(purchaseOrder.getValue())
															 .currency(CurrencyType.COP.name())
															 .response_json(paymentWithTokenResponse.getTransactionResponse());

		buildStatus(paymentWithTokenResponse, paymentBuilder);

		return paymentBuilder.build();
	}

	/**
	 * @param paymentWithTokenResponse {@link PaymentWithTokenResponse}
	 * @param paymentBuilder           {@link Payment.PaymentBuilder}
	 */
	private void buildStatus(final PaymentWithTokenResponse paymentWithTokenResponse,
							 final Payment.PaymentBuilder paymentBuilder) {

		if (paymentWithTokenResponse == null || paymentWithTokenResponse.getTransactionResponse() == null) {
			paymentBuilder.status(StatusType.ERROR.name());
		} else {
			paymentBuilder.status(paymentWithTokenResponse.getStatus());
		}
	}
}
