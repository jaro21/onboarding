package com.onboarding.payu.service.impl;

import com.google.gson.Gson;
import com.onboarding.payu.model.StatusType;
import com.onboarding.payu.model.refund.request.RefundDtoRequest;
import com.onboarding.payu.model.refund.response.RefundDtoResponse;
import com.onboarding.payu.provider.payments.IPaymentProvider;
import com.onboarding.payu.repository.IRefundRepository;
import com.onboarding.payu.repository.entity.Payment;
import com.onboarding.payu.repository.entity.Refund;
import com.onboarding.payu.service.IPaymentService;
import com.onboarding.payu.service.IPurchaseOrder;
import com.onboarding.payu.service.IRefundService;
import com.onboarding.payu.service.impl.mapper.RefundMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class RefundServiceImpl implements IRefundService {

	private IPaymentService iPaymentService;

	private IRefundRepository iRefundRepository;

	private IPaymentProvider iPaymentProvider;

	private IPurchaseOrder iPurchaseOrder;

	private RefundMapper refundMapper;

	@Autowired
	public RefundServiceImpl(final IPaymentService iPaymentService,
							 final IRefundRepository iRefundRepository,
							 final IPaymentProvider iPaymentProvider, final IPurchaseOrder iPurchaseOrder,
							 final RefundMapper refundMapper) {

		this.iPaymentService = iPaymentService;
		this.iRefundRepository = iRefundRepository;
		this.iPaymentProvider = iPaymentProvider;
		this.iPurchaseOrder = iPurchaseOrder;
		this.refundMapper = refundMapper;
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override public RefundDtoResponse appyRefund(final RefundDtoRequest refundDtoRequest) {

		log.debug("appyRefund : ", refundDtoRequest.toString());
		final Payment payment = iPaymentService.findById(refundDtoRequest.getIdPayment());
		final RefundDtoResponse refundDtoResponse = iPaymentProvider.applyRefund(refundMapper.buildRefundDtoRequest(refundDtoRequest,
																													payment));
		updatePurchaseOrder(refundDtoResponse, payment.getIdPurchaseOrder());
		updatePayment(payment, refundDtoResponse.getCode());
		saveRefund(refundDtoResponse, refundDtoRequest, payment);
		return refundDtoResponse;
	}

	/**
	 * Save refund information
	 *
	 * @param refundDtoResponse {@link RefundDtoResponse}
	 * @param refundDtoRequest {@link RefundDtoRequest}
	 * @param payment {@link Payment}
	 */
	private void saveRefund(final RefundDtoResponse refundDtoResponse, final RefundDtoRequest refundDtoRequest, final Payment payment) {

		iRefundRepository.save(getRefund(refundDtoResponse, refundDtoRequest, payment));
	}

	/**
	 * Get refund information to save
	 *
	 * @param refundDtoResponse {@link RefundDtoResponse}
	 * @param refundDtoRequest {@link RefundDtoRequest}
	 * @param payment {@link Payment}
	 * @return {@link Refund}
	 */
	private Refund getRefund(final RefundDtoResponse refundDtoResponse, final RefundDtoRequest refundDtoRequest, final Payment payment) {

		final Refund.RefundBuilder refundBuilder = Refund.builder().reason(refundDtoRequest.getReason())
														 .payment(payment);

		getRefundDtoResponse(refundDtoResponse, refundBuilder);

		return refundBuilder.build();
	}

	/**
	 * Set response json to save
	 *
	 * @param refundDtoResponse {@link RefundDtoResponse}
	 * @param refundBuilder {@link Refund.RefundBuilder}
	 */
	private void getRefundDtoResponse(final RefundDtoResponse refundDtoResponse,
									  final Refund.RefundBuilder refundBuilder) {

		if (refundDtoResponse != null) {

			refundBuilder.response_json(new Gson().toJson(refundDtoResponse));
		}
	}

	/**
	 * Update payment information
	 *
	 * @param payment {@link Payment}
	 * @param code {@link String}
	 */
	private void updatePayment(final Payment payment, final String code) {

		if (StatusType.SUCCESS.name().equals(code)) {

			iPaymentService.updateStatusById(StatusType.REFUNDED.name(), payment.getIdPayment());
		}
	}

	/**
	 * Get the payment object to update
	 *
	 * @param payment {@link Payment}
	 * @param statusType {@link StatusType}
	 * @return {@link Payment}
	 */
	private Payment getPayment(final Payment payment, final StatusType statusType) {

		return Payment.builder().idPayment(payment.getIdPayment())
					  .idPurchaseOrder(payment.getIdPurchaseOrder())
					  .status(statusType.name())
					  .orderId(payment.getOrderId())
					  .transactionId(payment.getTransactionId()).build();
	}

	/**
	 * update purchase order information
	 *
	 * @param refundDtoResponse {@link RefundDtoResponse}
	 * @param idPurchaseOrder {@link Integer}
	 */
	private void updatePurchaseOrder(final RefundDtoResponse refundDtoResponse, final Integer idPurchaseOrder) {

		if (refundDtoResponse.getCode().equals(StatusType.SUCCESS.name())) {
			//final PurchaseOrder purchaseOrder = iPurchaseOrder.findById(idPurchaseOrder);
			//iPurchaseOrder.update(refundMapper.getPurchaseOrder(purchaseOrder, StatusType.REFUNDED));
			iPurchaseOrder.updateStatusById(StatusType.REFUNDED.name(), idPurchaseOrder);
		}
	}
}
