package com.onboarding.payu.provider.payments.payu.mapper;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import com.onboarding.payu.client.payu.model.CommanType;
import com.onboarding.payu.client.payu.model.CountryType;
import com.onboarding.payu.client.payu.model.ExtraParameterType;
import com.onboarding.payu.client.payu.model.LanguageType;
import com.onboarding.payu.client.payu.model.Merchant;
import com.onboarding.payu.client.payu.model.TransactionType;
import com.onboarding.payu.client.payu.model.payment.request.AdditionalValues;
import com.onboarding.payu.client.payu.model.payment.request.Buyer;
import com.onboarding.payu.client.payu.model.payment.request.ExtraParameters;
import com.onboarding.payu.client.payu.model.payment.request.IngAddress;
import com.onboarding.payu.client.payu.model.payment.request.Order;
import com.onboarding.payu.client.payu.model.payment.request.Payer;
import com.onboarding.payu.client.payu.model.payment.request.PaymentWithTokenPayURequest;
import com.onboarding.payu.client.payu.model.payment.request.TransactionPayU;
import com.onboarding.payu.client.payu.model.payment.request.TxValue;
import com.onboarding.payu.client.payu.model.payment.response.ExtraParametersPayU;
import com.onboarding.payu.client.payu.model.payment.response.PaymentWithTokenPayUResponse;
import com.onboarding.payu.client.payu.model.payment.response.TransactionPayUResponse;
import com.onboarding.payu.model.payment.request.AdditionalValuesDto;
import com.onboarding.payu.model.payment.request.BuyerDto;
import com.onboarding.payu.model.payment.request.IngAddressDto;
import com.onboarding.payu.model.payment.request.OrderDto;
import com.onboarding.payu.model.payment.request.PayerDto;
import com.onboarding.payu.model.payment.request.TransactionRequest;
import com.onboarding.payu.model.payment.request.TxValueDto;
import com.onboarding.payu.model.payment.response.ExtraParametersResponse;
import com.onboarding.payu.model.payment.response.PaymentWithTokenResponse;
import com.onboarding.payu.model.payment.response.TransactionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * Mapper for the Payment's objects
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@Slf4j
@Component
public class PaymentPayuMapper {

	@Value("${payment-api.order.accountId}")
	private String accountId;

	public PaymentWithTokenPayURequest toPaymentWithTokenRequest(final TransactionRequest transactionRequest,
																 final Merchant merchant) {

		final String signatureMd5 = getSignature(merchant.getApiKey(), accountId, transactionRequest.getOrderDto());
		final PaymentWithTokenPayURequest.PaymentWithTokenPayURequestBuilder paymentWithTokenPayURequest =
				PaymentWithTokenPayURequest.builder()
										   .language(LanguageType.ES.getLanguage())
										   .command(CommanType.SUBMIT_TRANSACTION.toString())
										   .merchant(merchant)
										   .test(false);

		toTransaccion(transactionRequest, signatureMd5, paymentWithTokenPayURequest);
		log.error(paymentWithTokenPayURequest.toString());
		return paymentWithTokenPayURequest.build();
	}

	public void toTransaccion(final TransactionRequest transactionRequest, final String signatureMd5,
							  final PaymentWithTokenPayURequest.PaymentWithTokenPayURequestBuilder paymentWithTokenPayURequest) {

		if (transactionRequest != null) {

			final TransactionPayU.TransactionPayUBuilder transactionPayU =
					TransactionPayU.builder()
								   .creditCardTokenId(transactionRequest.getCreditCardTokenId())
								   .type(TransactionType.AUTHORIZATION_AND_CAPTURE.toString())
								   .paymentMethod(transactionRequest.getPaymentMethod())
								   .paymentCountry(CountryType.COLOMBIA.getCountry())
								   .deviceSessionId(transactionRequest.getDeviceSessionId())
								   .ipAddress(transactionRequest.getIpAddress())
								   .cookie(transactionRequest.getCookie())
								   .userAgent(transactionRequest.getUserAgent())
								   .extraParameters(getExtraParameter());

			toOrder(transactionRequest.getOrderDto(), signatureMd5, transactionPayU);
			toPayer(transactionRequest.getPayerDto(), transactionPayU);

			paymentWithTokenPayURequest.transaction(transactionPayU.build());
		}
	}

	private ExtraParameters getExtraParameter() {

		return ExtraParameters.builder().installmentsNumber(ExtraParameterType.INSTALLMENTS_NUMBER.getId()).build();
	}

	private void toPayer(final PayerDto payerDto,
						 final TransactionPayU.TransactionPayUBuilder transactionPayU) {

		if (payerDto != null) {

			final Payer.PayerBuilder payerBuilder = Payer.builder().merchantPayerId(payerDto.getMerchantPayerId())
														 .fullName(payerDto.getFullName())
														 .emailAddress(payerDto.getEmailAddress())
														 .contactPhone(payerDto.getContactPhone())
														 .dniNumber(payerDto.getDniNumber());

			toIngAddressPayer(payerDto.getBillingAddressDto(), payerBuilder);

			transactionPayU.payer(payerBuilder.build());
		}
	}

	private void toIngAddressPayer(final IngAddressDto billingAddressDto, final Payer.PayerBuilder payerBuilder) {

		if (billingAddressDto != null) {

			payerBuilder.billingAddress(getIngAddress(billingAddressDto));
		}
	}

	private IngAddress getIngAddress(final IngAddressDto billingAddressDto) {

		return IngAddress.builder().street1(billingAddressDto.getStreet1())
												.street2(billingAddressDto.getStreet2())
												.city(billingAddressDto.getCity())
												.state(billingAddressDto.getState())
												.country(billingAddressDto.getCountry())
												.postalCode(billingAddressDto.getPostalCode())
												.phone(billingAddressDto.getPhone()).build();
	}

	private void toOrder(final OrderDto orderDto, final String signatureMd5,
						 final TransactionPayU.TransactionPayUBuilder transactionPayU) {

		if (orderDto != null) {

			final Order.OrderBuilder orderBuilder = Order.builder().accountId(accountId)
														 .referenceCode(orderDto.getReferenceCode())
														 .description(orderDto.getDescription())
														 .language(LanguageType.ES.getLanguage())
														 .signature(signatureMd5);

			toAdditionalValues(orderDto.getAdditionalValuesDto(), orderBuilder);
			toBuyer(orderDto.getBuyerDto(), orderBuilder);
			toIngAddressOrder(orderDto.getShippingAddressDto(), orderBuilder);

			transactionPayU.order(orderBuilder.build());
		}
	}

	private void toIngAddressOrder(final IngAddressDto shippingAddressDto,
								   final Order.OrderBuilder orderBuilder) {

		if (shippingAddressDto != null) {

			orderBuilder.shippingAddress(getIngAddress(shippingAddressDto));
		}
	}

	private void toBuyer(final BuyerDto buyerDto,
						 final Order.OrderBuilder orderBuilder) {

		if (buyerDto != null) {

			final Buyer.BuyerBuilder buyerBuilder = Buyer.builder()
														 .merchantBuyerId(buyerDto.getMerchantBuyerId())
														 .fullName(buyerDto.getFullName())
														 .emailAddress(buyerDto.getEmailAddress())
														 .contactPhone(buyerDto.getContactPhone())
														 .dniNumber(buyerDto.getDniNumber());
			toIngAddressBuyer(buyerDto.getShippingAddressDto(), buyerBuilder);
			orderBuilder.buyer(buyerBuilder.build());
		}
	}

	private void toIngAddressBuyer(final IngAddressDto shippingAddressDto, final Buyer.BuyerBuilder buyerBuilder) {

		if (shippingAddressDto != null) {

			buyerBuilder.shippingAddress(getIngAddress(shippingAddressDto));
		}
	}

	private void toAdditionalValues(final AdditionalValuesDto additionalValuesDto,
									final Order.OrderBuilder orderBuilder) {

		if (additionalValuesDto != null && additionalValuesDto.getTxValueDto() != null) {

			final AdditionalValues additionalValues =
					AdditionalValues.builder().txValue(toTxValue(additionalValuesDto.getTxValueDto())).build();
			orderBuilder.additionalValues(additionalValues);
		}
	}

	private TxValue toTxValue(final TxValueDto txValueDto) {

		return TxValue.builder().value(txValueDto.getValue()).currency(txValueDto.getCurrency()).build();
	}

	public PaymentWithTokenResponse toPaymentWithTokenResponse(final PaymentWithTokenPayUResponse paymentWithToken) {

		PaymentWithTokenResponse.PaymentWithTokenResponseBuilder paymentWithTokenResponseBuilder =
				PaymentWithTokenResponse.builder().code(paymentWithToken.getCode())
										.error(paymentWithToken.getError());

		toTransactionResponse(paymentWithToken.getTransactionResponse(), paymentWithTokenResponseBuilder);

		return paymentWithTokenResponseBuilder.build();

	}

	public void toTransactionResponse(final TransactionPayUResponse transactionPayUResponse,
									  final PaymentWithTokenResponse.PaymentWithTokenResponseBuilder paymentWithTokenResponseBuilder) {

		if (transactionPayUResponse != null) {

			final TransactionResponse.TransactionResponseBuilder transactionResponseBuilder =
					TransactionResponse.builder().orderId(transactionPayUResponse.getOrderId())
									   .transactionId(transactionPayUResponse.getTransactionId())
									   .state(transactionPayUResponse.getState())
									   .paymentNetworkResponseCode(transactionPayUResponse.getPaymentNetworkResponseCode())
									   .paymentNetworkResponseErrorMessage(
											   transactionPayUResponse.getPaymentNetworkResponseErrorMessage())
									   .trazabilityCode(transactionPayUResponse.getTrazabilityCode())
									   .authorizationCode(transactionPayUResponse.getAuthorizationCode())
									   .pendingReason(transactionPayUResponse.getPendingReason())
									   .responseCode(transactionPayUResponse.getResponseCode())
									   .errorCode(transactionPayUResponse.getErrorCode())
									   .responseMessage(transactionPayUResponse.getResponseMessage())
									   .transactionDate(transactionPayUResponse.getTransactionDate())
									   .transactionTime(transactionPayUResponse.getTransactionTime())
									   .operationDate(transactionPayUResponse.getOperationDate());

			toExtraParameters(transactionPayUResponse.getExtraParameters(), transactionResponseBuilder);

			paymentWithTokenResponseBuilder.transactionResponse(transactionResponseBuilder.build());
		}
	}

	private void toExtraParameters(final ExtraParametersPayU extraParameters,
								   final TransactionResponse.TransactionResponseBuilder transactionResponseBuilder) {

		if (extraParameters != null) {

			final ExtraParametersResponse extraParametersResponse =
					ExtraParametersResponse.builder().bankReferencedCode(extraParameters.getBankReferencedCode()).build();
			transactionResponseBuilder.extraParameters(extraParametersResponse);
		}
	}

	/**
	 * Generate MD5 hash value of "ApiKey~merchantId~referenceCode~tx_value~currency"
	 *
	 * @param apiKey    {@link String}
	 * @param accountId {@link String}
	 * @param orderDto  {@link OrderDto}
	 * @return {@link String}
	 */
	public static String getSignature(final String apiKey, final String accountId, final OrderDto orderDto) {

		final String signatureString = apiKey + "~" + accountId + "~" + orderDto.getReferenceCode()
				+ "~" + orderDto.getAdditionalValuesDto().getTxValueDto().getValue()
				+ "~" + orderDto.getAdditionalValuesDto().getTxValueDto().getCurrency();

		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] messageDigest = md.digest(signatureString.getBytes());
			BigInteger no = new BigInteger(1, messageDigest);
			String hashtext = no.toString(16);
			while (hashtext.length() < 32) {
				hashtext = "0" + hashtext;
			}
			return hashtext;
		} catch (NoSuchAlgorithmException e) {
			log.error("Error generating signature for [{}] ", signatureString, e);
			throw new RuntimeException(e);
		}
	}
}
