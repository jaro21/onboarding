package com.onboarding.payu.client.payu;

import com.onboarding.payu.client.payu.model.payment.request.PaymentWithTokenPayURequest;
import com.onboarding.payu.client.payu.model.payment.response.PaymentWithTokenPayUResponse;
import com.onboarding.payu.client.payu.model.refund.request.RefundPayURequest;
import com.onboarding.payu.client.payu.model.refund.response.RefundPayUResponse;
import com.onboarding.payu.client.payu.model.tokenization.request.TokenizationPayURequest;
import com.onboarding.payu.client.payu.model.tokenization.response.TokenizationPayUResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * Feign customer to access payu services.
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Ramirez</a>
 * @version 1.0.0
 * @since 1.0.0
 */
@FeignClient(value = "payment", url = "${onboarding.uri}")
public interface PaymentClient {

	/**
	 * Feign customer to tokenization card
	 *
	 * @param registrationCardRequest {@link TokenizationPayURequest}
	 * @return {@link TokenizationPayUResponse}
	 */
	@PostMapping(consumes = "application/json", produces = "application/json")
	TokenizationPayUResponse tokenizationCard(@RequestBody TokenizationPayURequest registrationCardRequest);

	/**
	 * Feign customer to payment with token
	 *
	 * @param paymentWithTokenRequest {@link PaymentWithTokenPayURequest}
	 * @return {@link PaymentWithTokenPayUResponse}
	 */
	@PostMapping(consumes = "application/json", produces = "application/json")
	PaymentWithTokenPayUResponse paymentWithToken(@RequestBody PaymentWithTokenPayURequest paymentWithTokenRequest);

	/**
	 * Feign customer to payment refund
	 *
	 * @param refundPayURequest {@link RefundPayURequest}
	 * @return {@link RefundPayUResponse}
	 */
	@PostMapping(consumes = "application/json", produces = "application/json")
	RefundPayUResponse applyRefund(@RequestBody RefundPayURequest refundPayURequest);
}
