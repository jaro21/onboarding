package com.onboarding.payu.provider.payments.payu.mapper;

import com.onboarding.payu.client.payu.model.CommanType;
import com.onboarding.payu.client.payu.model.LanguageType;
import com.onboarding.payu.client.payu.model.Merchant;
import com.onboarding.payu.client.payu.model.TransactionType;
import com.onboarding.payu.client.payu.model.refund.request.OrderPayU;
import com.onboarding.payu.client.payu.model.refund.request.RefundPayURequest;
import com.onboarding.payu.client.payu.model.refund.request.TransactionPayU;
import com.onboarding.payu.client.payu.model.refund.response.RefundPayUResponse;
import com.onboarding.payu.client.payu.model.refund.response.TransactionPayUResponse;
import com.onboarding.payu.model.refund.request.RefundDtoRequest;
import com.onboarding.payu.model.refund.response.RefundDtoResponse;
import com.onboarding.payu.model.refund.response.TransactionDtoResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class RefundPayuMapper {

	public RefundPayURequest toRefundPayURequest(final RefundDtoRequest refundDtoRequest, final Merchant merchant) {

		final RefundPayURequest.RefundPayURequestBuilder refundPayURequestBuilder =
				RefundPayURequest.builder().language(LanguageType.ES.getLanguage())
								.command(CommanType.SUBMIT_TRANSACTION.name())
								.merchant(merchant)
								.test(false);

		toTransactionPayU(refundDtoRequest, refundPayURequestBuilder);

		return refundPayURequestBuilder.build();
	}

	private void toTransactionPayU(final RefundDtoRequest refundDtoRequest,
											  final RefundPayURequest.RefundPayURequestBuilder refundPayURequestBuilder) {

		final TransactionPayU.TransactionPayUBuilder transactionPayUBuilder =
				TransactionPayU.builder().type(TransactionType.REFUND.name())
							  .reason(refundDtoRequest.getReason())
							  .parentTransactionId(refundDtoRequest.getTransactionId())
							  .order(toOrderPayU(refundDtoRequest.getOrderId()));

		refundPayURequestBuilder.transaction(transactionPayUBuilder.build());
	}

	private OrderPayU toOrderPayU(final Long orderId) {

		return OrderPayU.builder().id(orderId).build();
	}

	public RefundDtoResponse toRefundDtoResponse(final RefundPayUResponse refundPayUResponse) {

		log.error("toRefundDtoResponse : ",refundPayUResponse.toString());
		final RefundDtoResponse.RefundDtoResponseBuilder refundDtoResponseBuilder =
				RefundDtoResponse.builder().code(refundPayUResponse.getCode())
								.error(refundPayUResponse.getError());

		toTransactionDtoResponse(refundPayUResponse.getTransactionResponse(), refundDtoResponseBuilder);

		return refundDtoResponseBuilder.build();
	}

	private void toTransactionDtoResponse(final TransactionPayUResponse transactionPayUResponse,
															final RefundDtoResponse.RefundDtoResponseBuilder refundDtoResponseBuilder) {

		if (transactionPayUResponse != null) {

			final TransactionDtoResponse.TransactionDtoResponseBuilder transactionDtoResponseBuilder =
					TransactionDtoResponse.builder().orderID(transactionPayUResponse.getOrderID())
								  .transactionID(transactionPayUResponse.getTransactionID())
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

			refundDtoResponseBuilder.transactionDtoResponse(transactionDtoResponseBuilder.build());
		}
	}
}
