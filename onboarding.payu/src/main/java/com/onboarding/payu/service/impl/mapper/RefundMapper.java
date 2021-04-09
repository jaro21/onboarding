package com.onboarding.payu.service.impl.mapper;

import com.google.gson.Gson;
import com.onboarding.payu.model.refund.request.RefundDtoRequest;
import com.onboarding.payu.model.refund.response.RefundDtoResponse;
import com.onboarding.payu.repository.entity.Payment;
import com.onboarding.payu.repository.entity.Refund;
import org.springframework.stereotype.Component;

@Component
public class RefundMapper {

	/**
	 * Build RefundDtoRequest object
	 *
	 * @param refundDtoRequest {@link RefundDtoRequest}
	 * @param payment          {@link Payment}
	 * @return {@link RefundDtoRequest}
	 */
	public RefundDtoRequest buildRefundDtoRequest(final RefundDtoRequest refundDtoRequest,
												  final Payment payment) {

		return RefundDtoRequest.builder().idPayment(refundDtoRequest.getIdPayment())
							   .orderId(payment.getOrderId())
							   .reason(refundDtoRequest.getReason())
							   .transactionId(payment.getTransactionId()).build();
	}

	/**
	 * Get refund information to save
	 *
	 * @param refundDtoResponse {@link RefundDtoResponse}
	 * @param refundDtoRequest  {@link RefundDtoRequest}
	 * @param payment           {@link Payment}
	 * @return {@link Refund}
	 */
	public Refund buildRefund(final RefundDtoResponse refundDtoResponse, final RefundDtoRequest refundDtoRequest, final Payment payment) {

		final Refund.RefundBuilder refundBuilder = Refund.builder().reason(refundDtoRequest.getReason())
														 .payment(payment);

		buildRefundDtoResponse(refundDtoResponse, refundBuilder);

		return refundBuilder.build();
	}

	/**
	 * Set response json to save
	 *
	 * @param refundDtoResponse {@link RefundDtoResponse}
	 * @param refundBuilder     {@link Refund.RefundBuilder}
	 */
	private void buildRefundDtoResponse(final RefundDtoResponse refundDtoResponse,
										final Refund.RefundBuilder refundBuilder) {

		if (refundDtoResponse != null) {

			refundBuilder.response_json(new Gson().toJson(refundDtoResponse));
		}
	}
}
