package com.onboarding.payu.service.impl;

import com.onboarding.payu.exception.BusinessAppException;
import com.onboarding.payu.exception.ExceptionCodes;
import com.onboarding.payu.model.StatusType;
import com.onboarding.payu.model.payment.request.PaymentTransactionRequest;
import com.onboarding.payu.model.payment.response.PaymentWithTokenResponse;
import com.onboarding.payu.provider.payments.IPaymentProvider;
import com.onboarding.payu.repository.IPaymentRepository;
import com.onboarding.payu.repository.entity.Customer;
import com.onboarding.payu.repository.entity.Payment;
import com.onboarding.payu.repository.entity.PurchaseOrder;
import com.onboarding.payu.service.ICustomerService;
import com.onboarding.payu.service.IPaymentService;
import com.onboarding.payu.service.IPurchaseOrder;
import com.onboarding.payu.service.impl.mapper.PaymentMapper;
import com.onboarding.payu.service.validator.PaymentValidator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementation of {@link IPaymentService} interface.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Service
public class PaymentServiceImpl implements IPaymentService {

	private final IPaymentProvider iPaymentProvider;

	private final ICustomerService iCustomerService;

	private final IPurchaseOrder iPurchaseOrder;

	private final PaymentValidator paymentValidator;

	private final IPaymentRepository iPaymentRepository;

	private final PaymentMapper paymentMapper;

	@Autowired
	public PaymentServiceImpl(final IPaymentProvider iPaymentProvider, final ICustomerService iCustomerService,
							  final IPurchaseOrder iPurchaseOrder,
							  final PaymentValidator paymentValidator,
							  final IPaymentRepository iPaymentRepository,
							  final PaymentMapper paymentMapper) {

		this.iPaymentProvider = iPaymentProvider;
		this.iCustomerService = iCustomerService;
		this.iPurchaseOrder = iPurchaseOrder;
		this.paymentValidator = paymentValidator;
		this.iPaymentRepository = iPaymentRepository;
		this.paymentMapper = paymentMapper;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public Payment findById(final Integer idPayment) {

		return iPaymentRepository.findById(idPayment).orElseThrow(
				() -> new BusinessAppException(ExceptionCodes.PAYMENT_NOT_EXIST, idPayment.toString()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override public PaymentWithTokenResponse paymentWithToken(final PaymentTransactionRequest paymentTransactionRequest) {

		log.debug("PaymentWithToken : ", paymentTransactionRequest.toString());
		final PurchaseOrder purchaseOrder = iPurchaseOrder
				.findByIdPurchaseOrder(paymentTransactionRequest.getIdPurchaseOrder());
		paymentValidator.runValidations(purchaseOrder);
		final Customer customer = iCustomerService.findById(paymentTransactionRequest.getIdCustomer());

		final PaymentWithTokenResponse paymentWithTokenResponse =
				iPaymentProvider.paymentWithToken(paymentTransactionRequest, purchaseOrder, customer);

		savePayment(paymentWithTokenResponse, purchaseOrder);
		return paymentWithTokenResponse;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public void updateStatusById(final String status, final Integer id) {

		iPaymentRepository.updateStatusById(status, id);
	}

	/**
	 * @param paymentWithTokenResponse
	 * @param purchaseOrder
	 */
	private void savePayment(final PaymentWithTokenResponse paymentWithTokenResponse,
							 final PurchaseOrder purchaseOrder) {

		final Payment payment = paymentMapper.buildPayment(paymentWithTokenResponse, purchaseOrder);
		iPaymentRepository.save(payment);

		if (StatusType.APPROVED.name().equals(payment.getStatus())) {
			iPurchaseOrder.updateStatusById(StatusType.PAID.name(), purchaseOrder.getIdPurchaseOrder());
		}
	}

}
