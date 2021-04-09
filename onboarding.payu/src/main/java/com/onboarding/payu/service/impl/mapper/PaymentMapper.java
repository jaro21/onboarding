package com.onboarding.payu.service.impl.mapper;

import java.math.BigDecimal;
import java.util.List;

import com.google.gson.Gson;
import com.onboarding.payu.client.payu.model.CurrencyType;
import com.onboarding.payu.model.StatusType;
import com.onboarding.payu.model.payment.request.AdditionalValuesDto;
import com.onboarding.payu.model.payment.request.OrderDto;
import com.onboarding.payu.model.payment.request.PayerDto;
import com.onboarding.payu.model.payment.request.PaymentTransactionRequest;
import com.onboarding.payu.model.payment.request.TransactionRequest;
import com.onboarding.payu.model.payment.request.TxValueDto;
import com.onboarding.payu.model.payment.response.PaymentWithTokenResponse;
import com.onboarding.payu.repository.entity.CreditCard;
import com.onboarding.payu.repository.entity.Customer;
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
															 .currency(CurrencyType.COP.name());

		buildJson(paymentWithTokenResponse, paymentBuilder);
		buildStatus(paymentWithTokenResponse, paymentBuilder);
		buildOrderId(paymentWithTokenResponse, paymentBuilder);
		buildTransactionId(paymentWithTokenResponse, paymentBuilder);

		return paymentBuilder.build();
	}

	/**
	 * @param paymentWithTokenResponse {@link PaymentWithTokenResponse}
	 * @param paymentBuilder           {@link Payment.PaymentBuilder}
	 */
	private void buildTransactionId(final PaymentWithTokenResponse paymentWithTokenResponse,
									final Payment.PaymentBuilder paymentBuilder) {

		if (paymentWithTokenResponse != null && paymentWithTokenResponse.getTransactionResponse() != null) {

			paymentBuilder.transactionId(paymentWithTokenResponse.getTransactionResponse().getTransactionId().toString());
		}
	}

	/**
	 * @param paymentWithTokenResponse {@link PaymentWithTokenResponse}
	 * @param paymentBuilder           {@link Payment.PaymentBuilder}
	 */
	private void buildOrderId(final PaymentWithTokenResponse paymentWithTokenResponse,
							  final Payment.PaymentBuilder paymentBuilder) {

		if (paymentWithTokenResponse != null && paymentWithTokenResponse.getTransactionResponse() != null) {

			paymentBuilder.orderId(paymentWithTokenResponse.getTransactionResponse().getOrderId());
		}
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

			paymentBuilder.status(paymentWithTokenResponse.getTransactionResponse().getState());
		}
	}

	/**
	 * @param paymentWithTokenResponse {@link PaymentWithTokenResponse}
	 * @param paymentBuilder           {@link Payment.PaymentBuilder}
	 */
	private void buildJson(final PaymentWithTokenResponse paymentWithTokenResponse,
						   final Payment.PaymentBuilder paymentBuilder) {

		if (paymentWithTokenResponse != null) {

			paymentBuilder.response_json(new Gson().toJson(paymentWithTokenResponse));
		}
	}

	/**
	 * Get TransactionRequest object
	 *
	 * @param paymentTransactionRequest {@link PaymentTransactionRequest}
	 * @param purchaseOrder {@link PurchaseOrder}
	 * @param customer {@link Customer}
	 * @return {@link TransactionRequest}
	 */
	public TransactionRequest buildTransactionRequest(final PaymentTransactionRequest paymentTransactionRequest,
													  final PurchaseOrder purchaseOrder,
													  final Customer customer) {

		final CreditCard creditCard = buildCreditCard(customer.getCreditCardList(), paymentTransactionRequest.getIdCreditCard());

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
	 * @param creditCardList {@link List <CreditCard>}
	 * @param idCreditCard   {@link Integer}
	 * @return {@link CreditCard}
	 */
	private CreditCard buildCreditCard(final List<CreditCard> creditCardList, final Integer idCreditCard) {

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
