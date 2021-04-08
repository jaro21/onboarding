package com.onboarding.payu.service;

import com.onboarding.payu.model.refund.request.RefundDtoRequest;
import com.onboarding.payu.model.refund.response.RefundDtoResponse;

/**
 * Interface that define of Refund's services
 *
 * @author <a href='julian.ramirez@payu.com'>Julian Alberto Ramirez Osorio</a>
 * @version 1.0.0
 * @since 1.0.0
 */
public interface IRefundService {

	/**
	 * Service to apply the refund payment
	 *
	 * @param refundDtoRequest {@link RefundDtoRequest}
	 * @return {@link RefundDtoResponse}
	 */
	RefundDtoResponse appyRefund(RefundDtoRequest refundDtoRequest);
}
