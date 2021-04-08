package com.onboarding.payu.service.impl;

import java.math.BigDecimal;
import java.util.List;

import com.google.gson.Gson;
import com.onboarding.payu.client.payu.model.CurrencyType;
import com.onboarding.payu.exception.BusinessAppException;
import com.onboarding.payu.exception.ExceptionCodes;
import com.onboarding.payu.model.StatusType;
import com.onboarding.payu.model.payment.request.AdditionalValuesDto;
import com.onboarding.payu.model.payment.request.OrderDto;
import com.onboarding.payu.model.payment.request.PayerDto;
import com.onboarding.payu.model.payment.request.PaymentTransactionRequest;
import com.onboarding.payu.model.payment.request.TransactionRequest;
import com.onboarding.payu.model.payment.request.TxValueDto;
import com.onboarding.payu.model.payment.response.PaymentWithTokenResponse;
import com.onboarding.payu.provider.payments.IPaymentProvider;
import com.onboarding.payu.repository.IPaymentRepository;
import com.onboarding.payu.repository.entity.CreditCard;
import com.onboarding.payu.repository.entity.Customer;
import com.onboarding.payu.repository.entity.Payment;
import com.onboarding.payu.repository.entity.PurchaseOrder;
import com.onboarding.payu.service.ICustomerService;
import com.onboarding.payu.service.IPaymentService;
import com.onboarding.payu.service.IPurchaseOrder;
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

	private IPaymentProvider iPaymentProvider;

	private ICustomerService iCustomerService;

	private IPurchaseOrder iPurchaseOrder;

	private PaymentValidator paymentValidator;

	private IPaymentRepository iPaymentRepository;

	@Autowired
	public PaymentServiceImpl(final IPaymentProvider iPaymentProvider, final ICustomerService iCustomerService,
							  final IPurchaseOrder iPurchaseOrder,
							  final PaymentValidator paymentValidator,
							  final IPaymentRepository iPaymentRepository) {

		this.iPaymentProvider = iPaymentProvider;
		this.iCustomerService = iCustomerService;
		this.iPurchaseOrder = iPurchaseOrder;
		this.paymentValidator = paymentValidator;
		this.iPaymentRepository = iPaymentRepository;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override public Payment findById(final Integer idPayment) {

		return iPaymentRepository.findById(idPayment).orElseThrow(() ->
																		  new BusinessAppException(ExceptionCodes.PAYMENT_NOT_EXIST,
																								   idPayment.toString()));
	}

	/**
	 * {@inheritDoc}
	 */
	@Transactional
	@Override public PaymentWithTokenResponse paymentWithToken(final PaymentTransactionRequest paymentTransactionRequest) {

		log.debug("PaymentWithToken : ", paymentTransactionRequest.toString());
		final PurchaseOrder purchaseOrder = iPurchaseOrder.findById(paymentTransactionRequest.getIdPurchaseOrder());

		final PaymentWithTokenResponse paymentWithTokenResponse =
				iPaymentProvider.paymentWithToken(getTransactionRequest(paymentTransactionRequest, purchaseOrder));

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

		final Payment payment = getPayment(paymentWithTokenResponse, purchaseOrder);
		iPaymentRepository.save(payment);
		updatePurchaseOrder(payment.getStatus(), purchaseOrder);
	}

	/**
	 * @param status
	 * @param purchaseOrder
	 */
	private void updatePurchaseOrder(final String status, final PurchaseOrder purchaseOrder) {

		if (StatusType.APPROVED.name().equals(status)) {

			iPurchaseOrder.update(getPurchaseOrder(purchaseOrder, StatusType.PAID));
		}
	}

	/**
	 * @param purchaseOrder
	 * @param statusType
	 * @return
	 */
	private PurchaseOrder getPurchaseOrder(final PurchaseOrder purchaseOrder, final StatusType statusType) {

		return PurchaseOrder.builder().idPurchaseOrder(purchaseOrder.getIdPurchaseOrder())
							.customer(purchaseOrder.getCustomer())
							.status(statusType.name())
							.referenceCode(purchaseOrder.getReferenceCode())
							.date(purchaseOrder.getDate())
							.value(purchaseOrder.getValue()).build();
	}

	/**
	 * @param paymentWithTokenResponse
	 * @param purchaseOrder
	 * @return
	 */
	private Payment getPayment(final PaymentWithTokenResponse paymentWithTokenResponse,
							   final PurchaseOrder purchaseOrder) {

		final Payment.PaymentBuilder paymentBuilder = Payment.builder().idPurchaseOrder(purchaseOrder.getIdPurchaseOrder())
															 .value(purchaseOrder.getValue())
															 .currency(CurrencyType.COP.name());

		toJson(paymentWithTokenResponse, paymentBuilder);
		getStatus(paymentWithTokenResponse, paymentBuilder);
		getOrderId(paymentWithTokenResponse, paymentBuilder);
		getTransactionId(paymentWithTokenResponse, paymentBuilder);

		return paymentBuilder.build();
	}

	//Agregar en un helper
	private void getTransactionId(final PaymentWithTokenResponse paymentWithTokenResponse,
								  final Payment.PaymentBuilder paymentBuilder) {

		if (paymentWithTokenResponse != null && paymentWithTokenResponse.getTransactionResponse() != null) {

			paymentBuilder.transactionId(paymentWithTokenResponse.getTransactionResponse().getTransactionId().toString());
		}
	}

	private void getOrderId(final PaymentWithTokenResponse paymentWithTokenResponse,
							final Payment.PaymentBuilder paymentBuilder) {

		if (paymentWithTokenResponse != null && paymentWithTokenResponse.getTransactionResponse() != null) {

			paymentBuilder.orderId(paymentWithTokenResponse.getTransactionResponse().getOrderId());
		}
	}

	private void getStatus(final PaymentWithTokenResponse paymentWithTokenResponse,
						   final Payment.PaymentBuilder paymentBuilder) {

		if (paymentWithTokenResponse == null || paymentWithTokenResponse.getTransactionResponse() == null) {

			paymentBuilder.status(StatusType.ERROR.name());
		} else {

			paymentBuilder.status(paymentWithTokenResponse.getTransactionResponse().getState());
		}
	}

	private void toJson(final PaymentWithTokenResponse paymentWithTokenResponse,
						final Payment.PaymentBuilder paymentBuilder) {

		if (paymentWithTokenResponse != null) {

			paymentBuilder.response_json(new Gson().toJson(paymentWithTokenResponse));
		}
	}

	/**
	 * Get TransactionRequest object
	 *
	 * @param paymentTransactionRequest {@link PaymentTransactionRequest}
	 * @param purchaseOrder
	 * @return {@link TransactionRequest}
	 */
	private TransactionRequest getTransactionRequest(final PaymentTransactionRequest paymentTransactionRequest,
													 final PurchaseOrder purchaseOrder) {

		final Customer customer = iCustomerService.findById(paymentTransactionRequest.getIdCustomer());

		final CreditCard creditCard = getCreditCard(customer.getCreditCardList(), paymentTransactionRequest.getIdCreditCard());

		paymentValidator.runValidations(purchaseOrder);

		return TransactionRequest.builder().orderDto(buildOrderDto(purchaseOrder))
								 .payerDto(buildPayerDto(customer))
								 .creditCardTokenId(creditCard.getToken())
								 .paymentMethod(creditCard.getPaymentMethod())
								 .deviceSessionId(paymentTransactionRequest.getDeviceSessionId())
								 .ipAddress(paymentTransactionRequest.getIpAddress())
								 .cookie(paymentTransactionRequest.getCookie())
								 .userAgent(paymentTransactionRequest.getUserAgent()).build();
	}

	/**
	 * Get CreditCard object by Id
	 *
	 * @param creditCardList {@link List<CreditCard>}
	 * @param idCreditCard   {@link Integer}
	 * @return {@link CreditCard}
	 */
	private CreditCard getCreditCard(final List<CreditCard> creditCardList, final Integer idCreditCard) {

		return creditCardList.stream().filter(creditCard -> creditCard.getIdCreditCard().equals(idCreditCard)).findFirst().get();
	}

	/**
	 * Build PayerDto object
	 *
	 * @param customer {@link Customer}
	 * @return {@link PayerDto}
	 */
	private PayerDto buildPayerDto(final Customer customer) {

		return PayerDto.builder().merchantPayerId("1")
					   .fullName(customer.getFullName())
					   .emailAddress(customer.getEmail())
					   .contactPhone(customer.getPhone())
					   .dniNumber(customer.getDniNumber()).build();
	}

	/**
	 * @param purchaseOrder {@link PurchaseOrder}
	 * @return {@link OrderDto}
	 */
	private OrderDto buildOrderDto(final PurchaseOrder purchaseOrder) {

		return OrderDto.builder().referenceCode(purchaseOrder.getReferenceCode())
					   .description("Purchase Order")
					   .additionalValuesDto(buildAdditionalValuesDto(purchaseOrder.getValue())).build();
	}

	/**
	 * @param value {@link BigDecimal}
	 * @return {@link AdditionalValuesDto}
	 */
	private AdditionalValuesDto buildAdditionalValuesDto(final BigDecimal value) {

		return AdditionalValuesDto.builder().txValueDto(buildValueDto(value)).build();
	}

	/**
	 * @param value {@link BigDecimal}
	 * @return {@link TxValueDto}
	 */
	private TxValueDto buildValueDto(final BigDecimal value) {

		return TxValueDto.builder().value(value).currency(CurrencyType.COP.name()).build();
	}
}
