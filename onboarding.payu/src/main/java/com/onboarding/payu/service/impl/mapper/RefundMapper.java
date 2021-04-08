package com.onboarding.payu.service.impl.mapper;

import com.onboarding.payu.model.StatusType;
import com.onboarding.payu.model.refund.request.RefundDtoRequest;
import com.onboarding.payu.repository.entity.Payment;
import com.onboarding.payu.repository.entity.PurchaseOrder;
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
	 * @param purchaseOrder
	 * @param statusType
	 * @return
	 */
	public PurchaseOrder getPurchaseOrder(final PurchaseOrder purchaseOrder, final StatusType statusType) {

		return PurchaseOrder.builder().idPurchaseOrder(purchaseOrder.getIdPurchaseOrder())
							.customer(purchaseOrder.getCustomer())
							.status(statusType.name())
							.referenceCode(purchaseOrder.getReferenceCode())
							.date(purchaseOrder.getDate())
							.value(purchaseOrder.getValue()).build();
	}
}
